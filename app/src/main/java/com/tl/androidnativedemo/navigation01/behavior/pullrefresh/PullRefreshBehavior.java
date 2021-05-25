package com.tl.androidnativedemo.navigation01.behavior.pullrefresh;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tl.androidnativedemo.R;


/**
 * Created by tianlin on 2017/10/18.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class PullRefreshBehavior extends CoordinatorLayout.Behavior<View>
{

    Context context;

    View dep;
    int dep_h;

    int max_dep;

    TextView textView;

    TLPullRefreshRecycleView recycler;

    public PullRefreshBehavior()
    {
    }

    Handler handler = new Handler();

    public PullRefreshBehavior(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        max_dep = (int) context.getResources().getDimension(R.dimen.ll_refresh_h);
        dep_h = max_dep - 32;
    }

    /**
     * 先调用了CoordinatorLayout的onLayout后，才会调用该方法
     *
     * @param parent
     * @param child
     * @param layoutDirection
     * @return
     */
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection)
    {
        Log.d("my", "onLayoutChild parent.getMeasuredWidth() = " + parent.getMeasuredWidth() +
                ", parent.getMeasuredHeight()" + parent.getMeasuredHeight() +
                ", child.getMeasuredWidth() = " + child.getMeasuredWidth() + ", child.getMeasuredHeight()" + child.getMeasuredHeight());

        dep = parent.findViewById(R.id.ll_refresh);
        textView = (TextView) parent.findViewById(R.id.tv_refresh);
        // 将按上面的一块放到屏幕上方
        dep.layout(0, -dep_h, parent.getWidth(), 0);
        // 将列表占满屏幕
        child.layout(0, 0, parent.getWidth(), parent.getHeight());

        // 为TLPullRefreshRecycleView设置behavior
        recycler = (TLPullRefreshRecycleView) child;
        recycler.setPullRefreshBehavior(this);
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency)
    {
        Log.d("my", "layoutDependsOn");
        return true;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes)
    {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

//    {
//        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//        int first1 = linearLayoutManager.findFirstVisibleItemPosition();
//        int first2 = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
//        int last1 = linearLayoutManager.findLastVisibleItemPosition();
//        int last2 = linearLayoutManager.findLastCompletelyVisibleItemPosition();
//
    // 当某一个item的高度太高时findFirstCompletelyVisibleItemPosition可能为-1，
    // findLastVisibleItemPosition根findFirstVisibleItemPosition的值一样，这种情况不能
    // 用这些方法判断第一和最后一行
//        Log.d("my", "dy = " + dy + ", first1 = " + first1 + ", first2 = " + first2 + ", last1 = " + last1 + ", last2 = " + last2);
//    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed)
    {

        // 滑动方式1
        RecyclerView recyclerView = (RecyclerView) child;
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int first = linearLayoutManager.findFirstCompletelyVisibleItemPosition();

        if (recycler.getTranslationY() >= dep_h)
        {
            textView.setText("松开刷新");
        }
        else
        {
            textView.setText("下拉刷新");
        }
        // 如果没有到顶端，不准滑动
        if (first != 0)
            return;
        // 下滑
        if (dy < 0)
        {
            // 平移刷新view
            float dyDep = dep.getTranslationY() - dy;
            if (dyDep >= 0 && dyDep <= max_dep)
            {
                dep.setTranslationY(dyDep);
            }

            // 平移列表
            float dyRecycle = child.getTranslationY() - dy;
            if (dyRecycle >= 0 && dyRecycle <= max_dep)
            {
                child.setTranslationY(dyRecycle);
            }
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed)
    {
//        if(recycler.getTranslationY() >= dep_h)
//        {
//            textView.setText("松开刷新");
//        }
//        // 滑动方式2
//        if (dyConsumed == 0 && dyUnconsumed < 0)
//        {
//            Log.d("my", "到边界了，还在下滑");
////             平移刷新view
//            float dyDep = dep.getTranslationY() - dyUnconsumed;
//            if (dyDep >= 0 && dyDep <= max_dep)
//            {
//                dep.setTranslationY(dyDep);
//            }
////             平移列表
//            float dyRecycle = child.getTranslationY() - dyUnconsumed;
//            if (dyRecycle >= 0 && dyRecycle <= max_dep)
//            {
//                child.setTranslationY(dyRecycle);
//            }
//        }
    }

    boolean isAnim = false;

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, final View child, View target)
    {

        if (child.getTranslationY() >= dep_h)
        {
            textView.setText("正在刷新");
            refresh();
        } else
            close();
    }

    private void refresh()
    {
        if (recycler.getOnRefreshListener() != null)
            recycler.getOnRefreshListener().onRefresh();
    }

    public void close()
    {
        textView.setText("刷新完成");

        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                recycler.setTranslationY(0);
                dep.setTranslationY(0);
            }
        }, 500);
    }
}
