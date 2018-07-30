package com.threetree.tfragment;

import android.view.View;
import android.widget.TextView;

import com.threetree.ttfragment.fragment.ImmersionFragment;


/**
 * Created by Administrator on 2018/7/27.
 */

public class MainAFragment extends ImmersionFragment {

    private TextView mContentTv;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_test;
    }

    @Override
    protected boolean initView(View view)
    {
        mContentTv = (TextView) view.findViewById(R.id.content_tv);
        mContentTv.setText("Fragment A");
        return false;
    }

    @Override
    protected boolean immersionBarEnabled()
    {
        return true;
    }
}
