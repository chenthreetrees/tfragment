package com.threetree.ttfragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2018/7/26.
 */

public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerHolder> {

    protected Context mContext;//上下文
    protected List mList;//数据源
    protected RecyclerView mRecyclerView;
    private LayoutInflater mInflater;//布局器

    private IOnItemClickListener mClickListener;
    private IOnItemLongClickListener mLongClickListener;

    public BaseRecyclerAdapter(Context context, List list) {
        mContext = context;
        if(list != null)
        {
            mList = list;
        }
        mInflater = LayoutInflater.from(context);
    }

    //在RecyclerView提供数据的时候调用
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView)
    {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView = null;
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        int layoutId = getLayoutId(viewType);
        View view = mInflater.inflate(layoutId, parent, false);
        return BaseRecyclerHolder.getRecyclerHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position)
    {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (mClickListener != null && view != null && mRecyclerView != null) {
                    int position = mRecyclerView.getChildAdapterPosition(view);
                    mClickListener.onItemClick(mRecyclerView,view,position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view)
            {
                if (mLongClickListener != null && view != null && mRecyclerView != null) {
                    int position = mRecyclerView.getChildAdapterPosition(view);
                    mLongClickListener.onItemLongClick(mRecyclerView,view,position);
                    return true;
                }
                return false;
            }
        });
        convert(holder, mList.get(position), position,getItemViewType(position));
    }

    @Override
    public int getItemCount()
    {
        return mList==null?0:mList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return super.getItemViewType(position);
    }

    /**
     * 多类型item时，可以根据type来设置layout
     * @param type
     * @return
     */
    public abstract int getLayoutId(int type);

    /**
     * 填充recyclerview适配器的方法
     *
     * @param holder
     * @param item
     * @param position
     * @param type
     */
    public abstract void convert(BaseRecyclerHolder holder,Object item,int position,int type);


    public void setIOnItemClickListener(IOnItemClickListener clickListener)
    {
        this.mClickListener = clickListener;
    }

    public void setIOnItemLongClickListener(IOnItemLongClickListener longClickListener)
    {
        this.mLongClickListener = longClickListener;
    }

    public BaseRecyclerHolder getHolder(int position)
    {

        View view = mRecyclerView.getChildAt(position);
        if(view==null)
            return null;
        return (BaseRecyclerHolder)mRecyclerView.getChildViewHolder(view);
    }

    /**
     * 插入一项
     * @param item
     */
    public void insert(Object item)
    {
        if(mList == null)
            return;
        insert(item,0);
    }

    /**
     * 插入一项
     * @param item
     * @param position
     */
    public void insert(Object item, int position)
    {
        if(item == null)
            return;
        if(mList != null)
        {
            mList.add(position,item);
            notifyItemInserted(position);
        }
    }

    /**
     * 插入多项
     * @param items
     * @param position
     */
    public void insertAll(List items,int position)
    {
        if(items == null)
            return;
        if(mList != null)
        {
            mList.addAll(position,items);
            notifyItemRangeInserted(position,items.size());
        }
    }

    /**
     * 清除数据
     */
    public void clear()
    {
        if(mList != null)
        {
            int size = mList.size();
            mList.clear();
            notifyItemRangeRemoved(0,size);
        }
    }

    /**
     * 从start开始删除数据
     * @param start
     */
    public void clear(int start)
    {
        if(mList != null)
        {
            int size = mList.size();
            if(start >= size)
                return;
            for(int i=start;i<mList.size();i++){
                mList.remove(i);
                i--;
            }
            notifyItemRangeRemoved(start,size-start);
        }
    }

    /**
     * 删除一项
     * @param position
     */
    public void remove(int position)
    {
        if(mList != null)
        {
            mList.remove(position);
            notifyItemRemoved(position);
        }
    }
}
