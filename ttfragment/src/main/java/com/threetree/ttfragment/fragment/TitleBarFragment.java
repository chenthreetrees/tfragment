package com.threetree.ttfragment.fragment;

import android.view.View;
import android.widget.FrameLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.threetree.ttfragment.R;

/**
 * Created by Administrator on 2018/7/27.
 */

public abstract class TitleBarFragment extends ImmersionFragment {

    private FrameLayout mFlyt;
    private View mTitleBar;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_titlebar;
    }

    @Override
    protected boolean initView(View v)
    {
        mFlyt = (FrameLayout)v.findViewById(R.id.titlebar_ll);
        mTitleBar = getTitleBar();
        int height = getTitleBarHeight();
        if(mTitleBar != null)
        {
            mFlyt.addView(mTitleBar,1,
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                            height));
        }

        View view = getContentView();
        if(view != null)
        {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            params.topMargin = height;
            mFlyt.addView(view,params);
        }
        return true;
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
     * 设置背景
     * @param res
     */
    public void setBackground(int res)
    {
        mFlyt.setBackgroundResource(res);
    }

    /**
     * 设置背景颜色
     * @param color
     */
    public void setBackgroundColor(int color)
    {
        mFlyt.setBackgroundColor(color);
    }

    /**
     * 从子类传入Titlebar
     * @return
     */
    protected abstract View getTitleBar();

    /**
     * 从子类传入页面内容
     * @return
     */
    protected abstract View getContentView();
}
