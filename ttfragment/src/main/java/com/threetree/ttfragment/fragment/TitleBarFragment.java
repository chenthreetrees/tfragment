package com.threetree.ttfragment.fragment;

import android.view.View;
import android.widget.LinearLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.threetree.ttfragment.R;

/**
 * Created by Administrator on 2018/7/27.
 */

public abstract class TitleBarFragment extends ImmersionFragment {

    private LinearLayout mLlyt;
    private View mTitleBar;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_titlebar;
    }

    @Override
    protected boolean initView(View v)
    {
        mLlyt = (LinearLayout)v.findViewById(R.id.titlebar_ll);
        mTitleBar = getTitleBar();
        if(mTitleBar != null)
        {
            mLlyt.addView(mTitleBar);
        }
        View view = getContentView();
        if(view != null)
        {
            mLlyt.addView(view,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
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
     * 设置背景
     * @param res
     */
    public void setBackground(int res)
    {
        mLlyt.setBackgroundResource(res);
    }

    /**
     * 设置背景颜色
     * @param color
     */
    public void setBackgroundColor(int color)
    {
        mLlyt.setBackgroundColor(color);
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
