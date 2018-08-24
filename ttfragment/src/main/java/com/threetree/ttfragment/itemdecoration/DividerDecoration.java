package com.threetree.ttfragment.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2018/8/8.
 */

public class DividerDecoration extends RecyclerView.ItemDecoration {

    private int dividerHeight;
    private Paint dividerPaint;
    private int left;
    private int right;

    private DividerDecoration(Builder builder)
    {
        dividerPaint = new Paint();
        dividerPaint.setColor(builder.color);
        this.dividerHeight = builder.height;
        this.left = builder.left;
        this.right = builder.right;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        int childCount = parent.getChildCount();
        int leftPadding = parent.getPaddingLeft() + left;
        int rightpadding = parent.getWidth() - parent.getPaddingRight() - right;

        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerHeight;
            c.drawRect(leftPadding, top, rightpadding, bottom, dividerPaint);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        outRect.bottom = dividerHeight;
    }

    public static final class Builder {
        private int left;
        private int right;
        private int height;
        private int color;

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

        public Builder color(int color)
        {
            this.color = color;
            return this;
        }

        public Builder height(int height)
        {
            this.height = height;
            return this;
        }

        public DividerDecoration build()
        {
            return new DividerDecoration(this);
        }
    }
}
