package com.tl.androidnativedemo.navigation01.behavior.doublemove;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * Created by tianlin on 2017/10/17.
 * Tel : 15071485690
 * QQ : 953108373
 * 未指定的View添加Behavior,所有的child部分都是指定Behavior的TextView
 */


public class MyDoubleMoveBehavior extends CoordinatorLayout.Behavior<TextView>
{
    public MyDoubleMoveBehavior()
    {
    }

    public MyDoubleMoveBehavior(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency)
    {
        Log.d("my", "layoutDependsOn");
        //
        return dependency instanceof Button;
//        return true;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency)
    {
        Log.d("my", "onDependentViewChanged");

        child.setX(dependency.getX());
        child.setY(dependency.getY() + dependency.getHeight());
//        return super.onDependentViewChanged(parent, child, dependency);
        return true;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, TextView child, int layoutDirection)
    {
        Log.d("my", "onLayoutChild");
        return super.onLayoutChild(parent, child, layoutDirection);
//        return true;
    }
}
