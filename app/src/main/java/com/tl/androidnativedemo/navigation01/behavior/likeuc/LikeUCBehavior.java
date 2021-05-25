package com.tl.androidnativedemo.navigation01.behavior.likeuc;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;

import com.tl.androidnativedemo.R;


/**
 * Created by tianlin on 2017/10/16.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class LikeUCBehavior extends CoordinatorLayout.Behavior<View>
{

    Context context;

    int head_h;

    public LikeUCBehavior(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        head_h = (int) context.getResources().getDimension(R.dimen.head_h);
    }

    /**
     * @param parent
     * @param child
     * @param dependency 作为CoordinatorLayout孩子的其他没有behavior的View
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency)
    {
        Log.d("my", "LikeUCBehavior layoutDependsOn");
        return child instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency)
    {
        Log.d("my", "LikeUCBehavior onDependentViewChanged getTranslationY = " + child.getTranslationY());

        dependency.setAlpha(child.getTranslationY() * 1.0f / head_h);
        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection)
    {
        Log.d("my", "LikeUCBehavior onLayoutChild");

        // 占满全屏
        child.layout(0, 0, parent.getWidth(), parent.getHeight());

        // 向下平移setTranslationY是一个正值
        child.setTranslationY(head_h);

        return true;
    }

    /**
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes
     * @return 返回true时，才有后面的Nested的方法
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes)
    {
        Log.d("my", "LikeUCBehavior onStartNestedScroll = " + nestedScrollAxes);
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    /**
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy                dy < 0表示下滑
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed)
    {

        float transY = child.getTranslationY() - dy;

        if (transY >= 0 && transY <= head_h)
        {
            child.setTranslationY(transY);
            consumed[1] = dy;
        }

        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed)
    {
        Log.d("my", "LikeUCBehavior onNestedScroll dyConsumed = " + dyConsumed + ", dyUnconsumed = " + dyUnconsumed);

        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }
}
