package com.threetree.ttfragment;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/7.
 */

public class TypePool<T> {
    ArrayList<Class<?>> mItemClasses = new ArrayList<>(5);
    ArrayList<ViewBinder> mViewbinders = new ArrayList<>(5);

    /**
     * 将数据类型和页面通过Index关联在一起
     * @param dataItemCls
     * @param viewBinder
     */
    public void register(Class<?> dataItemCls,ViewBinder viewBinder)
    {
        mViewbinders.add(viewBinder);
        mItemClasses.add(dataItemCls);
    }

    public boolean unregister(@NonNull Class<?> clazz) {
        boolean removed = false;
        while (true) {
            int index = mItemClasses.indexOf(clazz);
            if (index != -1) {
                mItemClasses.remove(index);
                mViewbinders.remove(index);
                removed = true;
            } else {
                break;
            }
        }
        return removed;
    }

    /**
     *
     * @param index
     * @return
     */
    public ViewBinder getViewBinder(int index)
    {
        return mViewbinders.get(index);
    }

    /**
     * 获取数据类型的index
     * @param clazz
     * @return
     */
    public int firstIndexOf(@NonNull final Class<?> clazz) {
        int index = mItemClasses.indexOf(clazz);
        if (index != -1) {
            return index;
        }
        for (int i = 0; i < mItemClasses.size(); i++) {
            if (mItemClasses.get(i).isAssignableFrom(clazz)) {
                return i;
            }
        }
        return -1;
    }
}
