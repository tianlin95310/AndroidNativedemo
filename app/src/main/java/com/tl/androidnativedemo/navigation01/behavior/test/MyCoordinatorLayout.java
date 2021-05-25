package com.tl.androidnativedemo.navigation01.behavior.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * Created by tianlin on 2017/10/17.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class MyCoordinatorLayout extends CoordinatorLayout
{
    public MyCoordinatorLayout(Context context)
    {
        super(context);
    }

    public MyCoordinatorLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MyCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        Log.w("my", "MyCoordinatorLayout onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        Log.w("my", "MyCoordinatorLayout onLayout");
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        Log.w("my", "MyCoordinatorLayout dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        Log.w("my", "MyCoordinatorLayout onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        Log.w("my", "MyCoordinatorLayout onTouchEvent");
        return super.onTouchEvent(ev);
    }

}
