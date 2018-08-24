package com.threetree.ttfragment;

/**
 * Created by Administrator on 2018/8/7.
 */

public interface ViewBinder {

    /**
     * 页面的xml文件ID
     * @return
     */
    int getLayoutId();

    /**
     * 绑定数据
     * @param holder
     * @param item
     * @param position
     */
    void bindData(BaseRecyclerHolder holder, Object item, int position);
}
