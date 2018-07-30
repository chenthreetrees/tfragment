package com.threetree.ttfragment.refreshview;

import android.content.Context;

import com.threetree.ttrefreshrecyclerview.base.HTViewHolderTracker;
import com.threetree.ttrefreshrecyclerview.viewimpl.HTDefaultVerticalRefreshViewHolder;


/**
 * Created by Administrator on 2018/5/8.
 */

public class HTRefreshHolder extends HTDefaultVerticalRefreshViewHolder {

    IOnRefreshChangeListener mListener;
    /**
     * 刷新控件改变时候调用
     */
    public interface IOnRefreshChangeListener
    {
        void onRefreshChange(float moveYDistance);
    }

    public void setOnRefreshChangeListener(IOnRefreshChangeListener listener)
    {
        mListener = listener;
    }
    public HTRefreshHolder(Context context)
    {
        super(context);
    }

    @Override
    public void onRefreshPositionChange(float scale, float moveDistance, int refreshStatus, HTViewHolderTracker viewHolderTracker)
    {
        if(mListener!=null)
        {
            mListener.onRefreshChange(moveDistance);
        }
        super.onRefreshPositionChange(scale, moveDistance, refreshStatus, viewHolderTracker);
    }
}
