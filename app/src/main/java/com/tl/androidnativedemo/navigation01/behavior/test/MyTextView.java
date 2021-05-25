package com.tl.androidnativedemo.navigation01.behavior.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by tianlin on 2017/12/21.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class MyTextView extends AppCompatTextView
{
    public MyTextView(Context context)
    {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        Log.e("my", "MyTextView onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        Log.e("my", "MyTextView onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        Log.e("my", "MyTextView dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Log.e("my", "MyTextView onTouchEvent");
        return super.onTouchEvent(event);
    }



}
