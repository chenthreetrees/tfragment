package com.threetree.tfragment;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.threetree.ttfragment.BaseRecyclerAdapter;
import com.threetree.ttfragment.BaseRecyclerHolder;
import com.threetree.ttfragment.IOnItemLongClickListener;
import com.threetree.ttfragment.fragment.RecyclerFragment;
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
        final List list = getList();
        final MainCAdapter adapter = new MainCAdapter(mActivity,list);
        setOnRefreshListener(new HTRefreshListener() {
            @Override
            public void onRefresh()
            {
                adapter.clear();
                adapter.insertAll(getList(),0);
                new Handler(getMainLooper()).postDelayed(new Runnable() {
                    public void run()
                    {
                        mRecyclerView.setRefreshCompleted(true);
                    }
                }, 500);
            }
        });
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
                        mRecyclerView.setRefreshCompleted(true);
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

    private List<MainItem> getList()
    {
        List<MainItem> list = new ArrayList<MainItem>();
        for (int i=0;i<30;i++)
        {
            int num = i%3;
            MainItem mainItem = new MainItem();
            if(num == 0)
            {
                mainItem.type = 0;
            }else if(num == 1)
            {
                mainItem.type = 1;
            }else if(num == 2)
            {
                mainItem.type = 2;
            }
            mainItem.text = "item" + i;
            list.add(mainItem);
        }
        return list;
    }


    class MainCAdapter extends BaseRecyclerAdapter
    {
        public MainCAdapter(Context context, List list)
        {
            super(context, list);
        }

        @Override
        public int getItemViewType(int position)
        {
            MainItem item = (MainItem)mList.get(position);
            return item.type;
        }

        @Override
        public int getLayoutId(int type)
        {
            if(type == 0)
            {
                return R.layout.item_text;
            }else if(type == 1)
            {
                return R.layout.item_button;
            }else if(type == 2)
            {
                return R.layout.item_image;
            }
            return R.layout.item_text;
        }

        @Override
        public void convert(BaseRecyclerHolder holder, Object item, int position, int type)
        {
            MainItem mainItem = (MainItem)item;
            if(type == 0)
            {
                TextView textView = holder.getView(R.id.content_tv);
                textView.setText(mainItem.text);
            }else if(type == 1)
            {
                Button button = holder.getView(R.id.button);
                button.setText(mainItem.text);
            }
        }
    }

}
