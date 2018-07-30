package com.threetree.tfragment;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 重写了viewpager，主要增加了viewpager是否可以左右滑动的开关
 * 修复viewPager快速缩放时程序异常
 * 
 * @author 陈文森
 *
 */
public class APagingViewPager extends ViewPager
{
	 private boolean enabled;

	public APagingViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.enabled = false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		try {
			if(enabled){
				return super.onInterceptTouchEvent(ev);
			}
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		try {
			if(enabled){
				return super.onTouchEvent(ev);
			}
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public void setPagingEnable(boolean enable)
	{
		this.enabled = enable;
	}

}
