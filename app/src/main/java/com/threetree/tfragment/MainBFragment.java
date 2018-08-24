package com.threetree.tfragment;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.threetree.ttfragment.BaseRecyclerAdapter;
import com.threetree.ttfragment.BaseRecyclerHolder;
import com.threetree.ttfragment.fragment.RecyclerFragment;
import com.threetree.ttrefreshrecyclerview.HTLoadMoreListener;
import com.threetree.ttrefreshrecyclerview.HTRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static android.os.Looper.getMainLooper;

/**
 * Created by Administrator on 2018/7/27.
 */

public class MainBFragment extends RecyclerFragment {

    @Override
    protected View getTitleBar()
    {
        View view =View.inflate(mActivity,R.layout.titlebar,null);
        return view;
    }

    @Override
    protected int getTitleBarHeight()
    {
        return dip2px(mActivity,48);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected boolean isRefresh()
    {
        return true;
    }

    @Override
    protected boolean isLoadmore()
    {
        return true;
    }



    @Override
    protected BaseRecyclerAdapter getAdapter()
    {
        List<String> list = new ArrayList<String>();
        for (int i=0;i<30;i++)
        {
            list.add("item"+i);
        }
        setOnRefreshListener(new HTRefreshListener() {
            @Override
            public void onRefresh()
            {
                new Handler(getMainLooper()).postDelayed(new Runnable() {
                    public void run()
                    {
                        setRefreshCompleted(true);
                    }
                }, 500);
            }
        });
        setOnLoadMoreListener(new HTLoadMoreListener() {
            @Override
            public void onLoadMore()
            {
                new Handler(getMainLooper()).postDelayed(new Runnable() {
                    public void run()
                    {
                        setRefreshCompleted(true);
                    }
                }, 500);
            }
        });
        MainAdapter adapter = new MainAdapter(mActivity,list);
        return adapter;
    }

    class MainAdapter extends BaseRecyclerAdapter
    {
        public MainAdapter(Context context, List list)
        {
            super(context, list);
        }

        @Override
        public int getLayoutId(int type)
        {
            return R.layout.item_text;
        }

        @Override
        public void convert(BaseRecyclerHolder holder, Object item, int position)
        {
            String text = (String)item;
            TextView textView = holder.getView(R.id.content_tv);
            textView.setText(text);
        }
    }

}
