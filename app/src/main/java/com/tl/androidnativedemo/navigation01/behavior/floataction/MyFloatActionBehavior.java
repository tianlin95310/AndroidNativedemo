package com.tl.androidnativedemo.navigation01.behavior.floataction;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tl.androidnativedemo.utils.anim.AnimUtils;

/**
 * Created by tianlin on 2017/6/29.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class MyFloatActionBehavior extends FloatingActionButton.Behavior
{

    public MyFloatActionBehavior(Context context, AttributeSet attrs)
    {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes)
    {
        Log.d("my", "onStartNestedScroll");
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed)
    {
        if (dyConsumed > 0 && dyUnconsumed == 0)
        {
            Log.d("my", "上滑中");
        }
        if (dyConsumed == 0 && dyUnconsumed > 0)
        {
            Log.d("my", "到边界了还在上滑");
        }
        if (dyConsumed < 0 && dyUnconsumed == 0)
        {
            Log.d("my", "下滑中");
        }
        if (dyConsumed == 0 && dyUnconsumed < 0)
        {
            Log.d("my", "到边界了，还在下滑");
        }

        // 上滑
        if (((dyConsumed > 0 && dyUnconsumed == 0) || (dyConsumed == 0
                && dyUnconsumed > 0)))
        {
            AnimUtils.translateX(child, child.getWidth(), null, 800);

        }
        // 下滑
        else if (((dyConsumed < 0 && dyUnconsumed == 0) || (dyConsumed == 0
                && dyUnconsumed < 0)))
        {
            AnimUtils.translateX(child, 0, null, 800);
        }
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target)
    {
        Log.d("my", "onStopNestedScroll");
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }
}
