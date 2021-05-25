package com.tl.androidnativedemo.navigation01.behavior.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by tianlin on 2017/10/16.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class MyRecyclerView extends RecyclerView
{
    public MyRecyclerView(Context context)
    {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec)
    {
        super.onMeasure(widthSpec, heightSpec);
        Log.e("my", "MyRecyclerView onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
        Log.e("my", "MyRecyclerView onLayout");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        Log.e("my", "MyRecyclerView dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e)
    {
        Log.e("my", "MyRecyclerView onInterceptTouchEvent");
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        Log.e("my", "MyRecyclerView onTouchEvent");
        return super.onTouchEvent(e);
    }

    @Override
    public boolean startNestedScroll(int axes)
    {
        Log.e("my", "MyRecyclerView startNestedScroll");
        return super.startNestedScroll(axes);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow)
    {
        Log.e("my", "MyRecyclerView dispatchNestedPreScroll");
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow)
    {
        Log.e("my", "MyRecyclerView dispatchNestedScroll");
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public void stopNestedScroll()
    {
        Log.e("my", "MyRecyclerView stopNestedScroll");
        super.stopNestedScroll();
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY)
    {
        Log.e("my", "MyRecyclerView dispatchNestedPreFling");
        return super.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed)
    {
        Log.e("my", "MyRecyclerView dispatchNestedFling");
        return super.dispatchNestedFling(velocityX, velocityY, consumed);
    }


}
