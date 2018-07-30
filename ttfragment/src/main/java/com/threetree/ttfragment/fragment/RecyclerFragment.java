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
 * Created by Administrator on 2018/7/29.
 */

public abstract class RecyclerFragment extends ImmersionFragment {
    protected HTRefreshRecyclerView mRecyclerView;
    protected FrameLayout mLayout;
    View mTitleBar;
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
            HTRefreshHolder viewHolder = new HTRefreshHolder(mActivity);
            mRecyclerView.setRefreshViewHolder(viewHolder);
        }
        mRecyclerView.setLayoutManager(getLayoutManager());
        RecyclerView.ItemDecoration decoration = getItemDecoration();
        if(decoration !=null)
        {
            mRecyclerView.addItemDecoration(decoration);
        }
        BaseRecyclerAdapter adapter = getAdapter();
        if(adapter == null)
            throw new NullPointerException("adapter is null");
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLoadMoreViewShow(false);
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
        mRecyclerView.setPadding(0,getTopPadding(),0,getBottomPadding());
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
     * 设置下拉刷新事件
     * @param listener
     */
    protected void setOnRefreshListener(HTRefreshListener listener)
    {
        if(isRefresh())
        {
            mRecyclerView.setOnRefreshListener(listener);
        }
    }

    /**
     * 设置下拉加载事件
     * @param listener
     */
    protected void setOnLoadMoreListener(HTLoadMoreListener listener)
    {
        if(isLoadmore())
        {
            mRecyclerView.setOnLoadMoreListener(listener);
        }
    }

    protected void setLoadMoreViewShow(boolean isShow)
    {
        if(isLoadmore())
        {
            mRecyclerView.setLoadMoreViewShow(isShow);
        }
    }

    /**
     * 设置recyclerview的偏移
     * 布局采用framelayout,titlebar叠在recyclerview上面，不同页面根据需求去做偏移
     * @return
     */
    protected int getTopPadding()
    {
        if(mImmersionBar != null)
        {
            return mImmersionBar.getBarParams().titleBarHeight;
        }
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

    protected RecyclerView.ItemDecoration getItemDecoration()
    {
        return null;
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
    protected abstract View getTitleBar();

    /**
     * 设置titlebar的高度
     * @return
     */
    protected abstract int getTitleBarHeight();

    protected abstract BaseRecyclerAdapter getAdapter();

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
