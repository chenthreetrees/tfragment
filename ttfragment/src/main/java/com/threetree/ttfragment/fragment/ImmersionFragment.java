package com.threetree.ttfragment.fragment;

import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by Administrator on 2018/7/27.
 */
public abstract class ImmersionFragment extends BaseFragment {

    /**
     * 沉浸式状态栏
     */
    protected ImmersionBar mImmersionBar;

    @Override
    protected void onViewInitFinished()
    {
        super.onViewInitFinished();
        if(immersionBarEnabled())
        {
            initImmersionBar();
        }
        onImmersionBarInitFinish();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean immersionBarEnabled() {
        return false;
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(mActivity);
        mImmersionBar.init();
    }

    /**
     * 沉浸式状态栏初始化完成
     * 在这个方法里，可以对状态栏进行操作，例如获取状态栏高度等
     * 防止页面创建时候获取状态栏为空
     */
    protected void onImmersionBarInitFinish()
    {

    }
}
