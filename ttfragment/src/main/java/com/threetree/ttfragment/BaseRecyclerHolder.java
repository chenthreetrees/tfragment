package com.threetree.ttfragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by Administrator on 2018/3/26.
 * Recycler三剑客之缓存
 */

public class BaseRecyclerHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private Context mContext;
    private BaseRecyclerHolder(Context context, View itemView)
    {
        super(itemView);
        this.mContext = context;
        mViews = new SparseArray<>(5);
    }
    public static BaseRecyclerHolder getRecyclerHolder(Context context,View itemView)
    {
        return  new BaseRecyclerHolder(context,itemView);
    }

    public SparseArray<View> getViews()
    {
        return mViews;
    }

    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if(view == null)
        {
            view = itemView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

}
