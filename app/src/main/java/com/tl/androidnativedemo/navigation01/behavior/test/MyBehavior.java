package com.tl.androidnativedemo.navigation01.behavior.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

/**
 * Created by tianlin on 2017/10/13.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class MyBehavior extends CoordinatorLayout.Behavior<View>
{

    public MyBehavior(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        Log.d("my", "MyBehavior");
    }

    /**
     * MyCoordinatorLayout拦截事件时，先将事件给MyBehavior，将权利给他,
     * onInterceptTouchEvent优先子控件调用
     * @param parent
     * @param child
     * @param ev
     * @return 如果拦截了,事件就不会往下传了,MyBehavior以及CoordinatorLayout的onTouchEvent会处理事件
     */
    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev)
    {
        Log.d("my", "MyBehavior onInterceptTouchEvent");
        return super.onInterceptTouchEvent(parent, child, ev);
//        return true;
    }

    /**
     * 如果事件是让onTouchEvent处理，即事件是让CoordinatorLayout处理了，事件也是不往下分发的，所以
     * MyBehavior只是CoordinatorLayout一层代理
     * @param parent
     * @param child
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev)
    {
        Log.d("my", "MyBehavior onTouchEvent");
        return super.onTouchEvent(parent, child, ev);
//        return true;
    }

    /**
     *  layoutDependsOn会先于onMeasureChild调用，在测量的时候会调用多次
     * @param parent
     * @param child 是赋予behavior的控件
     * @param dependency 其他的CoordinatorLayout的没有赋予behavior的一级孩子，都是dependency,有多少个dependency
     *            layoutDependsOn就会调用几次，我们可以通过layoutDependsOn选择处理那个dependency的变化
     *            dependency的onMeasure会先于MyBehavior的layoutDependsOn调用
     *            child的onMeasure后于MyBehavior的onMeasureChild调用
     *
     * @return 返回true才能正常建立依赖关系，以后的每次滑动都会调用layoutDependsOn和onDependentViewChanged
     *         返回false的话，滑动时只会调用layoutDependsOn，不会调用onDependentViewChanged，即我们只针对部分
     *         dependency进行处理，并不是所有的dependency我们都要去处理
     *
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency)
    {
        Log.d("my", "MyBehavior layoutDependsOn --- child = " + child.getId() + ", dependency = " + dependency.getId() + ", " + dependency.getClass().getSimpleName() + "" +
                ", child.getMeasuredWidth() = " + child.getMeasuredWidth() + ", child.getMeasuredHeight() = " + child.getMeasuredHeight() + "----dependency.getMeasuredWidth() = " + dependency.getMeasuredWidth() + ", dependency.getMeasuredHeight = " + dependency.getMeasuredHeight());
//        return child instanceof RecyclerView;
//        return super.layoutDependsOn(parent, child, dependency);
        return dependency instanceof LinearLayout;
//        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed)
    {
        Log.d("my", "MyBehavior onMeasureChild ");
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }

    /**
     *
     * @param parent
     * @param child
     * @param layoutDirection
     * @return 返回true使用必须自己去布局child，返回false使用CoordinatorLayout默认的布局去布局child
     * onLayoutChild的调用会晚于layoutDependsOn中返回true的dependency的onLayout，这样我们才能在返回true的
     * 情况下去自定义dependency的布局，否则我们在onLayoutChild写的dependency的布局会被dependency自己的默认布局覆盖
     *
     */
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection)
    {
        Log.d("my", "MyBehavior onLayoutChild ");
//        return super.onLayoutChild(parent, child, layoutDirection);

        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency)
    {
        Log.d("my", "MyBehavior onDependentViewChanged");
        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, View child, View dependency)
    {
        Log.d("my", "MyBehavior onDependentViewRemoved");
        super.onDependentViewRemoved(parent, child, dependency);
    }

    /**
     *
     * @param coordinatorLayout
     * @param child             带有behavior的view
     * @param directTargetChild 当前滑动的view，coordinatorLayout其他可以滑动的的孩子滑动也会触发该方法，如果是child滑动的
     *                          那么child和target就是一样的，如果不是child是其他的滑动，那么child和target就不一样，
     * @param target            当前滑动的view
     * @param nestedScrollAxes
     * @return 返回true才会调用接下来的一系列的滑动方法
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes)
    {
        Log.d("my", "MyBehavior onStartNestedScroll" + ", child = " + child.getId() + "， directTargetChild = " + directTargetChild.getId() + ", target = " + target.getId());
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes)
    {
        Log.d("my", "MyBehavior onNestedScrollAccepted");
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    /**
     * 当滚动的视图是child时，child和target是相同的
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy
     * @param consumed  给consumed[1]赋值后，dyUnconsumed in onNestedScroll = dy in onNestedPreScroll - consumed[1] in onNestedPreScroll
     *       一旦消费了dy以后，子View就没有对应的dy偏移量了，导致子View没有偏移量，无法滑动， consumed是返回参数,size = 2，他会返回给子View消费了多少,消费了
     *        之后他就不能在消费了
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed)
    {
        Log.d("my", "MyBehavior onNestedPreScroll child = " + child.getId() + ", target = " + target.getId() + ", dy = " + dy);
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    /**
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed    孩子已经消费的
     * @param dyConsumed
     * @param dxUnconsumed  孩子没有消费的
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed)
    {
        Log.d("my", "MyBehavior onNestedScroll child = " + child.getId() + ", target = " + target.getId() + ", dyConsumed = " + dyConsumed + ", dyUnconsumed = " + dyUnconsumed);
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target)
    {
        Log.d("my", "MyBehavior onStopNestedScroll");
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY)
    {
        Log.d("my", "MyBehavior onNestedPreFling");
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }
    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed)
    {
        Log.d("my", "MyBehavior onNestedFling");
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }




}
