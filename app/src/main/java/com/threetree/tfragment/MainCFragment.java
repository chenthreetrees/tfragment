package com.threetree.tfragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.threetree.ttfragment.BaseRecyclerAdapter;
import com.threetree.ttfragment.BaseRecyclerHolder;
import com.threetree.ttfragment.IOnItemLongClickListener;
import com.threetree.ttfragment.MultTypeAdapter;
import com.threetree.ttfragment.ViewBinder;
import com.threetree.ttfragment.fragment.RecyclerFragment;
import com.threetree.ttfragment.itemdecoration.DividerDecoration;
import com.threetree.ttfragment.itemdecoration.PaddingDecoration;
import com.threetree.ttrefreshrecyclerview.HTLoadMoreListener;
import com.threetree.ttrefreshrecyclerview.HTRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static android.os.Looper.getMainLooper;

/**
 * Created by Administrator on 2018/7/27.
 */

public class MainCFragment extends RecyclerFragment {

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
    protected boolean immersionBarEnabled()
    {
        return true;
    }

    @Override
    protected BaseRecyclerAdapter getAdapter()
    {
        DividerDecoration dividerDecoration = new DividerDecoration.Builder()
                .left(12)
                .right(12)
                .height(2)
                .color(Color.RED)
                .build();
        PaddingDecoration paddingDecoration = new PaddingDecoration.Builder()
                .left(12)
                .right(12)
                .build();
        addItemDecoration(paddingDecoration);
        addItemDecoration(dividerDecoration);

        final List list = getList();
        final MultTypeAdapter adapter = new MultTypeAdapter(mActivity,list);
        adapter.registerLayout(MainItem.class, new ViewBinder() {
            @Override
            public int getLayoutId()
            {
                return R.layout.item_text;
            }

            @Override
            public void bindData(BaseRecyclerHolder holder, Object item, int position)
            {
                MainItem mainItem = (MainItem)item;
                TextView textView = holder.getView(R.id.content_tv);
                textView.setText(mainItem.text);
            }
        });

        adapter.registerLayout(ImageItem.class, new ViewBinder() {
            @Override
            public int getLayoutId()
            {
                return R.layout.item_button;
            }

            @Override
            public void bindData(BaseRecyclerHolder holder, Object item, int position)
            {
                ImageItem mainItem = (ImageItem)item;
                Button button = holder.getView(R.id.button);
                button.setText(mainItem.text);
            }
        });

        setOnRefreshListener(new HTRefreshListener() {
            @Override
            public void onRefresh()
            {
                adapter.clear();
                adapter.insertAll(getList(),0);
                new Handler(getMainLooper()).postDelayed(new Runnable() {
                    public void run()
                    {
                        setRefreshCompleted(true);
                    }
                }, 500);
            }
        });
        setLoadMoreViewShow(false);
        setOnLoadMoreListener(new HTLoadMoreListener() {
            @Override
            public void onLoadMore()
            {
                MainItem mainItem = new MainItem();
                mainItem.type = 0;
                mainItem.text = "add item";
                adapter.insert(mainItem,list.size());
                new Handler(getMainLooper()).postDelayed(new Runnable() {
                    public void run()
                    {
                        setRefreshCompleted(true);
                    }
                }, 500);
            }
        });
        adapter.setIOnItemLongClickListener(new IOnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView parent, View view, int position)
            {
                adapter.remove(position);
                return true;
            }
        });
        return adapter;
    }

    private List getList()
    {
        List list = new ArrayList();
        for (int i=0;i<10;i++)
        {
            int num = i%2;

            if(num == 0)
            {
                MainItem mainItem = new MainItem();
                mainItem.text = "item" + i;
                list.add(mainItem);
            }else if(num == 1)
            {
                ImageItem imageItem = new ImageItem();
                imageItem.text = "item" + i;
                list.add(imageItem);
            }
        }
        return list;
    }

}
