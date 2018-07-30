package com.threetree.ttfragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2018/7/26.
 */

public interface IOnItemClickListener {
    void onItemClick(RecyclerView parent, View view, int position);
}
