package com.tl.androidnativedemo.navigation01.behavior.test;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Created by tianlin on 2017/12/21.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class MyLinear extends LinearLayout
{
    public MyLinear(Context context)
    {
        super(context);
    }

    public MyLinear(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MyLinear(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyLinear(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        Log.e("my", "MyLinear dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        Log.e("my", "MyLinear onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Log.e("my", "MyLinear onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        Log.e("my", "MyLinear onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        Log.e("my", "MyLinear onLayout");
        super.onLayout(changed, l, t, r, b);
    }
}
