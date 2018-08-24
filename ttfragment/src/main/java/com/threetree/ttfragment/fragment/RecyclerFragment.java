package com.threetree.ttfragment.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.threetree.ttfragment.BaseRecyclerAdapter;
import com.threetree.ttfragment.R;
import com.threetree.ttfragment.refreshview.HTRefreshHolder;
import com.threetree.ttrefreshrecyclerview.HTLoadMoreListener;
import com.threetree.ttrefreshrecyclerview.HTRefreshListener;
import com.threetree.ttrefreshrecyclerview.HTRefreshRecyclerView;

/**
 * 将整个页面封装成列表页面，配合BaseRecyclerAdapter使用，
 * 实现多类型子项的适配，使用简单，提供可定制的titlebar和无数据时显示页面
 * 页面布局采用FrameLayout
 *
 * Created by Administrator on 2018/7/29.
 */
public abstract class RecyclerFragment extends ImmersionFragment {
    private HTRefreshRecyclerView mRecyclerView;
    protected FrameLayout mLayout;
    View mTitleBar;

    private HTRefreshHolder mRefreshHolder;
    /**
     * 空白数据时候显示的页面
     */
    protected View mEmptyView;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_recycler;
    }

    @Override
    protected boolean initView(View view)
    {
        mLayout = (FrameLayout) view.findViewById(R.id.recycler_fl);
        mRecyclerView = (HTRefreshRecyclerView)view.findViewById(R.id.recycler);
        setTitleBar();
        setRecyclerView();
        setEmptyView();
        return true;
    }

    private void setTitleBar()
    {
        mTitleBar = getTitleBar();
        if(mTitleBar != null)
        {
            mLayout.addView(mTitleBar,1,
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                           getTitleBarHeight()));
        }
    }

    private void setRecyclerView()
    {
        if(isRefresh() || isLoadmore())
        {
            mRefreshHolder = new HTRefreshHolder(mActivity);
            mRecyclerView.setRefreshViewHolder(mRefreshHolder);
        }
        mRecyclerView.setLayoutManager(getLayoutManager());
        BaseRecyclerAdapter adapter = getAdapter();
        if(adapter == null)
            throw new NullPointerException("adapter must not be null");
        mRecyclerView.setAdapter(adapter);

    }


    private void setEmptyView()
    {
        mEmptyView = getEmptyView();
        if(mEmptyView != null)
        {
            mLayout.addView(mEmptyView);
        }
    }


    @Override
    protected void initImmersionBar()
    {
        if(mTitleBar != null)
        {
            mImmersionBar = ImmersionBar.with(mActivity).titleBar(mTitleBar);
            mImmersionBar.init();
        }else
        {
            super.initImmersionBar();
        }
        int topPadding = getTopPadding();
        if(mImmersionBar != null)
        {
            topPadding = topPadding + mImmersionBar.getBarParams().titleBarHeight;
        }
        mRecyclerView.setPadding(0,topPadding,0,getBottomPadding());
    }

    /**
     * 设置recyclerview是否需要下拉刷新
     * @return
     */
    protected boolean isRefresh()
    {
        return false;
    }

    /**
     *  设置recyclerview是否需要上拉加载更多
     * @return
     */
    protected boolean isLoadmore()
    {
        return false;
    }

    /**
     * 设置recyclerview的偏移
     * 布局采用framelayout,titlebar叠在recyclerview上面，不同页面根据需求去做偏移
     * @return
     */
    protected int getTopPadding()
    {
        return 0;
    }

    /**
     * 设置recyclerview的偏移
     */
    protected int getBottomPadding()
    {
        return 0;
    }

    /**
     * 设置布局模式
     * @return
     */
    protected RecyclerView.LayoutManager getLayoutManager()
    {
        return new LinearLayoutManager(mActivity);
    }

    /**
     * 设置空白数据页面
     * @return
     */
    protected View getEmptyView()
    {
        return null;
    }


    /**
     * 从子类传入Titlebar
     * @return
     */
    protected View getTitleBar()
    {
        return null;
    }

    /**
     * 设置titlebar的高度
     * @return
     */
    protected int getTitleBarHeight()
    {
        return 0;
    }

    /**
     * 设置适配器
     * @return
     */
    protected abstract BaseRecyclerAdapter getAdapter();

    /**
     * 添加ItemDecoration
     * @return
     */
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration)
    {
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    /**
     * 设置上拉加载事件
     * @param listener
     */
    public void setOnLoadMoreListener(HTLoadMoreListener listener)
    {
        if(isLoadmore())
        {
            mRecyclerView.setOnLoadMoreListener(listener);
        }
    }

    /**
     * 该方法在setRefreshCompleted（false）时候起作用，
     * true-显示无更多加载
     * false-隐藏没有更多，每次上拉有隐藏的动画效果
     * @param isShow
     */
    public void setLoadMoreViewShow(boolean isShow)
    {
        if(isLoadmore())
        {
            mRecyclerView.setLoadMoreViewShow(isShow);
        }
    }

    /**
     * 设置下拉刷新事件
     * @param listener
     */
    public void setOnRefreshListener(HTRefreshListener listener)
    {
        if(isRefresh())
        {
            mRecyclerView.setOnRefreshListener(listener);
        }
    }

    /**
     * 结束刷新
     * @param hasMore false-没有更多数据可加载，下次将不会回调onloadmore
     */
    public void setRefreshCompleted(boolean hasMore)
    {
        mRecyclerView.setRefreshCompleted(hasMore);
    }

    /**
     * 显示空白数据页面
     */
    public void showEmptyView()
    {
        if(mEmptyView != null)
        {
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏空白数据页面
     */
    public void hideEmptyView()
    {
        if(mEmptyView != null)
        {
            mEmptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
