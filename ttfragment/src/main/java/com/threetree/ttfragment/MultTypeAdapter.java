package com.threetree.ttfragment;

import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2018/8/6.
 */

public class MultTypeAdapter extends BaseRecyclerAdapter {

    private TypePool mTypePool;

    public MultTypeAdapter(Context context, List list)
    {
        super(context, list);
        mTypePool = new TypePool();
    }

    /**
     * 匹配数据类型和页面类型
     * @param dataItemCls
     * @param viewBinder
     */
    public void registerLayout(Class<?> dataItemCls,ViewBinder viewBinder)
    {
        mTypePool.register(dataItemCls,viewBinder);
    }


    @Override
    public int getLayoutId(int viewType)
    {
       return mTypePool.getViewBinder(viewType).getLayoutId();
    }

    @Override
    public int getItemViewType(int position)
    {
        //这个方法的底层逻辑其实就是数据源的index
        Object item = mList.get(position);
        return mTypePool.firstIndexOf(item.getClass());
    }

    @Override
    public void convert(BaseRecyclerHolder holder, Object item, int position)
    {
        int index = getItemViewType(position);
        ViewBinder viewBinder = mTypePool.getViewBinder(index);
        viewBinder.bindData(holder,item,position);
    }
}
