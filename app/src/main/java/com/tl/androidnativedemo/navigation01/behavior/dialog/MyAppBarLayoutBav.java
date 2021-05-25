package com.tl.androidnativedemo.navigation01.behavior.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewPropertyAnimatorUpdateListener;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.utils.anim.AnimUtils;

/**
 * Created by tianlin on 2017/12/14.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class MyAppBarLayoutBav extends AppBarLayout.Behavior implements AppBarLayout.OnOffsetChangedListener
{
    // 下滑过程中的最大距离
    private static float MAX_SLIDE;

    // APP bar的最小宽度
    private static float MIN_WIDTH = 0;

    // 横纵变化的比率
    private static float SCALE = 0;

    CoordinatorLayout parent;
    AppBarLayout appBarLayout;
    RecyclerView recyclerView;

    // 平移量
    float offset_h;
    int verticalOffset;

    Context context;

    public void setDragable(final boolean canDrag)
    {
        setDragCallback(new DragCallback()
        {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout)
            {
                return canDrag;
            }
        });
    }

    public MyAppBarLayoutBav(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDragable(true);
        this.context = context;
    }

    /**
     * 在偏移量发生变化时才会调用，verticalOffset，初始值为0
     *
     * @param appBarLayout
     * @param verticalOffset
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset)
    {
        Log.d("my", "onOffsetChanged verticalOffset = " + verticalOffset + ", offset_h = " + offset_h);
        if (verticalOffset < 0)
            appBarLayout.setBackgroundColor(Color.TRANSPARENT);
        this.verticalOffset = verticalOffset;
    }

    float startY;

    /**
     * 点击AppBarLayout会走这里，因为AppBarLayout默认不消费，
     * 而点击RecyclerView不会走，因为RecyclerView已经消费了,除非拦截了事件
     *
     * @param parent
     * @param child
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev)
    {
        Log.d("my", "onTouchEvent getAction = " + ev.getAction());

        // 有一个向下的平移量，并且AppBarLayout没有折叠
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getRawY();

                boolean inApp = checkIn(ev);
                if (!inApp) {
                    DialogActivity activity = (DialogActivity) context;
                    activity.onKeyUp(KeyEvent.KEYCODE_BACK, null);
                    return true;
                }

                break;

            case MotionEvent.ACTION_MOVE:

                float dy = ev.getRawY() - startY;
                startY = ev.getRawY();

                // 滑动AppBarLayout时，我们改变AppBarLayout的行为，不调用super.onTouchEvent
                if(offset_h >= 0 && verticalOffset == 0)
                    return setAppBarLayoutParam(dy);
                else
                    return super.onTouchEvent(parent, child, ev);

            case MotionEvent.ACTION_UP:

                dy = ev.getRawY() - startY;
                startY = ev.getRawY();

                // 临界控制
                if(offset_h + dy < MAX_SLIDE / 2)
                    offset_h = 0;
                else
                    offset_h = MAX_SLIDE;

                // 滑动AppBarLayout时，我们改变AppBarLayout的行为，不调用super.onTouchEvent
                if(offset_h >= 0 && verticalOffset == 0)
                    return auToSlide(offset_h);
                else
                    return super.onTouchEvent(parent, child, ev);

        }
        return super.onTouchEvent(parent, child, ev);

    }

    private boolean checkIn(MotionEvent ev) {
        Rect rect = new Rect();
        rect.left = (int) appBarLayout.getX();
        rect.top = (int) appBarLayout.getY();
        rect.right = rect.left + appBarLayout.getMeasuredWidth();
        rect.bottom = rect.top + appBarLayout.getMeasuredHeight();

        int x = (int) ev.getX();
        int y = (int) ev.getY();
        boolean result = rect.contains(x, y);
        Log.d("my", "---checkIn---" + rect.toString() + ", result = " + result + ", x = " + x + ", y = " + y);
        return result;
    }

    public boolean setAppBarLayoutParam(float dy)
    {
        offset_h = offset_h + dy;

        if (offset_h >= MAX_SLIDE)
            offset_h = MAX_SLIDE;

        if (offset_h <= 0)
            offset_h = 0;

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        layoutParams.width = parent.getWidth() - (int) (offset_h / SCALE);

        if (layoutParams.width <= MIN_WIDTH)
            layoutParams.width = (int) MIN_WIDTH;
        if (layoutParams.width >= parent.getWidth())
            layoutParams.width = parent.getWidth();

        layoutParams.leftMargin = layoutParams.rightMargin = (parent.getWidth() - layoutParams.width) / 2;

        appBarLayout.setLayoutParams(layoutParams);

        Log.d("my", "dy = " + dy + ", offset_h = " + offset_h + ", layoutParams.width = " + layoutParams.width +
                ", appBarLayout.getTotalScrollRange() = " + appBarLayout.getTotalScrollRange());

        appBarLayout.setTranslationY(offset_h);
        recyclerView.setTranslationY(offset_h);

        recyclerView.setAlpha(1 - offset_h / MAX_SLIDE);

        if (recyclerView.getAlpha() < 0.1) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }

        return false;
    }

    public boolean auToSlide(final float offset_h)
    {
        long duration = 200;

        AnimUtils.translateY(appBarLayout, (int) offset_h, null, duration);
        AnimUtils.translateY(recyclerView, (int) offset_h, null, duration);
        AnimUtils.alpha(recyclerView, 1 - offset_h / MAX_SLIDE, duration, new ViewPropertyAnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(View view)
            {
                Log.d("my", "view.getY() = " + view.getY() + ", view.getAlpha() = " + view.getAlpha());

//                alpha = 1 - offset_h / MAX_SLIDE;
//                offset_h = (1 - alpha) * MAX_SLIDE;
//                width = parent.getWidth() - offset_h / SCALE

                // 得到appBarLayout应有的宽度
                float width = parent.getWidth() - (1 - view.getAlpha()) * MAX_SLIDE / SCALE;

                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
                layoutParams.width = (int) width;
                if (layoutParams.width <= MIN_WIDTH)
                    layoutParams.width = (int) MIN_WIDTH;
                if (layoutParams.width >= parent.getWidth())
                    layoutParams.width = parent.getWidth();
                layoutParams.leftMargin = layoutParams.rightMargin = (parent.getWidth() - layoutParams.width) / 2;
                appBarLayout.setLayoutParams(layoutParams);

            }
        }, null);

        return false;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout abl, int layoutDirection)
    {
        Log.d("my", "onLayoutChild");

        boolean result = super.onLayoutChild(parent, abl, layoutDirection);
        if (appBarLayout == null)
        {
            appBarLayout = abl;
            this.parent = parent;
            recyclerView = (RecyclerView) parent.findViewById(R.id.recycler_view);
            appBarLayout.addOnOffsetChangedListener(this);

            // 初始化布局参数
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
            layoutParams.width = parent.getWidth();
            abl.setLayoutParams(layoutParams);

            // 假设最大的滑动距离为appBarLayout高度的一半
            MAX_SLIDE = appBarLayout.getHeight() / 2;

            // 假设窗口的最小宽度
            MIN_WIDTH = layoutParams.width * 0.8f;

            // 窗口减到最小宽度移动的距离
            float dy = parent.getWidth() - MIN_WIDTH;

            // 计算得到横纵长度变化比率
            SCALE = MAX_SLIDE / dy;

            Log.d("my", "parent.getWidth() = " + parent.getWidth());
            Log.d("my", "MIN_WIDTH = " + MIN_WIDTH);
            Log.d("my", "SCALE = " + SCALE);

            // 初始化为对话框
            offset_h = MAX_SLIDE;
            setAppBarLayoutParam(offset_h);
        }

        return result;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed)
    {
        Log.d("my", "onNestedPreScroll dy = " + dy + ", consumed[1] = " + consumed[1]);
        if (offset_h > 0)
        {
            // 禁止RecyclerView的内部滑动
            consumed[1] = dy;
        } else
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY)
    {
        Log.d("my", "onNestedPreFling");
        // 如果有平移量，禁止惯性滑动
        if (offset_h > 0)
        {
            return true;
        } else
            return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

}
