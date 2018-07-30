package com.threetree.ttfragment.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 懒加载，意味着只有在页面可见的时候才加载数据，
 * 这种情况在使用viewpager的时候经常遇到，
 * 因为viewpager会缓存下个页面，即会调用下个页面的setUserVisibleHint，onCreateView和onViewCreated，
 * 所以我们可以根据页面的setUserVisibleHint是否可见来处理是否加载数据
 *
 * Created by Administrator on 2018/7/27.
 */
public abstract class BaseFragment extends Fragment {
    private final String TAG = BaseFragment.class.getSimpleName();
    protected FragmentActivity mActivity;

    /**
     * 将整个页面缓存，fragment在切换的时候，不需要重新创建页面
     */
    protected View mRootView;

    /**
     *页面切换时候，是否重新加载数据
     */
    private boolean isReLoadData = true;

    /**
     *页面切换时候，是否重新设置页面
     */
    private boolean isResetView = true;

    /**
     * 是否对用户可见
     */
    private boolean mIsVisible;

    /**
     * 是否完成创建
     * 当执行完onViewCreated方法后即为true
     */
    private boolean mIsPrepare;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Log.e(TAG,"fragment=" + this.getClass().getSimpleName() + " onAttach is running");
        mActivity = (FragmentActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Log.e(TAG,"fragment=" + this.getClass().getSimpleName() +" onCreateView is running");
        if(!isHoldView() || mRootView == null)
        {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG,"fragment=" + this.getClass().getSimpleName() +" onViewCreated is running");
        if(isResetView)
        {
            isResetView = initView(view);
        }
        onViewInitFinished();
        if (isLazyLoad()) {
            mIsPrepare = true;
            onLazyLoad();
        } else {
            canLoadData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG,"fragment=" + this.getClass().getSimpleName() +" setUserVisibleHint is running" + " isVisibleToUser=" + isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        Log.e(TAG,"fragment=" + this.getClass().getSimpleName() +" onDestroyView is running");
        mIsPrepare = false;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.e(TAG,"fragment=" + this.getClass().getSimpleName() +" onDestroy is running");
        if(mRootView!=null)
            mRootView=null;
    }

    /**
     * 页面layout
     *
     * @return
     */
    protected abstract int getLayoutId();

    private void onLazyLoad() {
        if (mIsVisible && mIsPrepare) {
            //是否需要重新加载数据
            canLoadData();
        }
    }

    private void canLoadData()
    {
        //是否需要重新加载数据
        if(isReLoadData)
        {
            isReLoadData = loadData();
        }
    }

    /**
     * 是否缓存整个页面
     *
     * @return
     */
    protected boolean isHoldView()
    {
        return false;
    }

    /**
     * 是否懒加载
     *
     * @return the boolean
     */
    protected boolean isLazyLoad()
    {
        return false;
    }

    /**
     * 用户可见时执行的操作
     */
    protected void onVisible() {
        onLazyLoad();
    }

    /**
     * 用户不可见执行
     */
    protected void onInvisible() {

    }

    /**
     * 页面初始化完成
     */
    protected void onViewInitFinished()
    {

    }

    /**
     *
     * @param view
     * @return true-页面可见时候每次都会设置页面，false-页面可见时，只设置一次数据
     */
    protected boolean initView(View view)
    {
        Log.e(TAG,"fragment=" + this.getClass().getSimpleName() +" initView is running");
        return true;
    }

    /**
     * 页面切换时候，是否重新加载数据
     * @return true-页面可见时候每次都会加载数据，false-页面可见时，只加载一次数据
     */
    protected boolean loadData() {
        Log.e(TAG,"fragment=" + this.getClass().getSimpleName() +" initData is running");
        return true;
    }
}
