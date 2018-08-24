package com.threetree.ttfragment.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/8/8.
 */

public class PaddingDecoration extends RecyclerView.ItemDecoration {

    private int left;
    private int right;
    private int top;
    private int bottom;
    private HashMap<Integer,Rect> rectMap;

    private PaddingDecoration(Builder builder)
    {
        this.left = builder.left;
        this.right = builder.right;
        this.top = builder.top;
        this.bottom = builder.bottom;
        this.rectMap = builder.rectMap;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        int position = parent.getChildLayoutPosition(view);
        if(rectMap!=null && rectMap.size() > 0 && rectMap.containsKey(position))
        {
            outRect = rectMap.get(position);
        }else
        {
            outRect.left = left;
            outRect.right = right;
            outRect.top = top;
            outRect.bottom = bottom;
        }
    }

    public static final class Builder {
        private int left;
        private int right;
        private int top;
        private int bottom;
        private HashMap<Integer,Rect> rectMap;

        public Builder addRect(int position,Rect outRect)
        {
            if(rectMap == null)
            {
                rectMap = new HashMap<>();
            }
            rectMap.put(position,outRect);
            return this;
        }

        public Builder left(int left)
        {
            this.left = left;
            return this;
        }

        public Builder right(int right)
        {
            this.right = right;
            return this;
        }

        public Builder top(int top)
        {
            this.top = top;
            return this;
        }

        public Builder bottom(int bottom)
        {
            this.bottom = bottom;
            return this;
        }

        public PaddingDecoration build()
        {
            return new PaddingDecoration(this);
        }
    }

}
