package com.tl.androidnativedemo.navigation71.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.utils.anim.AnimUtils;

/**
 * Created by tianlin on 2017/10/19.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class TLRefreshBehavior extends CoordinatorLayout.Behavior<View> implements TLRefresh
{
    /**
     * 最开始的状态，以及下拉距离还没有到可以出发下拉刷新临界值的状态
     */
    public final int MODE_INIT = 0;

    /**
     * 下拉距离已经大于下拉临界值，此时松开可以刷新
     */
    public final int MODE_BEGIN = 1;

    /**
     * 正在刷新中
     */
    public final int MODE_READY = 2;

    /**
     * 刷新结束
     */
    public final int MODE_OVER = 3;

    // 控件能下滑的最大位移量,即刷新块的高度
    int MAX_REFRESH_LAYOUT_HEIGHT;

    // 控件开始出现松开刷新的高度，达到该高度时，控件应该能继续下滑一段时间，这是一个出发刷新的临界值
    int BEGIN_REFRESH_HEIGHT;
    // 视图包装
    RefreshViewHolder refreshViewHolder;
    // 列表视图
    TLRefreshRecyclerView recyclerView;
    // 关闭动画是否正在进行
    boolean isAnimRunning = false;
    // 当前是刷新还是加载更多
    int mode = 0;

    public TLRefreshBehavior() {
    }

    public TLRefreshBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 该方法返回true，才会刷新dependency的布局，特别是在这样onLayoutChild返回true的情况下，onLayoutChild中的refreshViewHolder.ll_refresh才能正常的布局
     *
     * @param parent
     * @param child
     * @param dependency
     * @return 通常返回true,
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
//        return child instanceof TLRefreshRecyclerView;
        return dependency instanceof LinearLayout;
    }

    /**
     * @param parent
     * @param child
     * @param layoutDirection
     * @return 在返回true的情况下，对于那些使用behavior的child，我们需要自己去布局他，否则控件无法显示，没有加behavior的dependency能正常显示
     * <p>
     * 有时候，有些dependency会比onLayoutChild执行要晚，导致onLayoutChild效果被覆盖，导致对dependency的布局无效
     * 我们可以让layoutDependsOn返回true，或者依赖于我们需要再onLayoutChild中需要自己实现布局,能保证LinearLayout的
     * onLayout先于this.onLayoutChild调用
     */
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        // 初始化列表和监听器
        initRecyclerView(child);
        // 初始化View
        initViewHolder(parent);
        // 对孩子进行布局
        initAndLayoutChild(parent);

        return super.onLayoutChild(parent, child, layoutDirection);
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {

        if (mode == MODE_READY) {
            return;
        }
        if (isAnimRunning)
            return;

        // recyclerView有平移时不让recyclerView进行内部滑动
        if(recyclerView.getTranslationY() != 0) {
            float transition = recyclerView.getTranslationY() - dy;
            if (transition >= 0 && transition <= MAX_REFRESH_LAYOUT_HEIGHT) {
                refreshViewHolder.ll_refresh.setTranslationY(transition);
                recyclerView.setTranslationY(transition);

                if (transition >= BEGIN_REFRESH_HEIGHT) {
                    setMode(MODE_BEGIN);
                } else if (transition > 0 && transition < BEGIN_REFRESH_HEIGHT && this.mode == MODE_BEGIN) {
                    setMode(MODE_INIT);
                }
            }
            else if (transition >= -MAX_REFRESH_LAYOUT_HEIGHT && transition <= 0) {
                refreshViewHolder.ll_load.setTranslationY(transition);
                recyclerView.setTranslationY(transition);

                if (transition <= -BEGIN_REFRESH_HEIGHT) {
                    setMode(MODE_BEGIN);
                } else if (transition > -BEGIN_REFRESH_HEIGHT && transition < 0 && this.mode == MODE_BEGIN) {
                    setMode(MODE_INIT);
                }
            }

            // 禁止让recyclerView内部滑动
            consumed[1] = dy;
        }


    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

        Log.d("my", "dyConsumed = " + dyConsumed + ", dyUnconsumed = " + dyUnconsumed + ", recyclerView.getTranslationY() = " + recyclerView.getTranslationY());
        if (mode == MODE_READY) {
            return;
        }
        if (isAnimRunning)
            return;

        // 滑动到顶端后的滑动，触发recyclerView的移动
        if (dyConsumed == 0) {
            float transition = recyclerView.getTranslationY() - dyUnconsumed;
            if (transition >= 0 && transition <= MAX_REFRESH_LAYOUT_HEIGHT) {
                refreshViewHolder.ll_refresh.setTranslationY(transition);
                recyclerView.setTranslationY(transition);
                if (transition >= BEGIN_REFRESH_HEIGHT) {
                    setMode(MODE_BEGIN);
                } else if (transition > 0 && transition < BEGIN_REFRESH_HEIGHT && this.mode == MODE_BEGIN) {
                    setMode(MODE_INIT);
                }
            }
            else if (transition >= -MAX_REFRESH_LAYOUT_HEIGHT && transition <= 0) {
                refreshViewHolder.ll_load.setTranslationY(transition);
                recyclerView.setTranslationY(transition);
                if (transition <= -BEGIN_REFRESH_HEIGHT) {
                    setMode(MODE_BEGIN);
                } else if (transition > -BEGIN_REFRESH_HEIGHT && transition < 0 && this.mode == MODE_BEGIN) {
                    setMode(MODE_INIT);
                }
            }
        }
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {

        if (mode == MODE_READY) {
            return;
        }

        if (isAnimRunning)
            return;

        float transition = recyclerView.getTranslationY();
        if (transition >= BEGIN_REFRESH_HEIGHT) {
            setMode(MODE_READY);
            if (recyclerView.getTlOnRefreshListener() != null) {
                recyclerView.getTlOnRefreshListener().onRefresh();
            }

        } else if (transition <= -BEGIN_REFRESH_HEIGHT) {
            setMode(MODE_READY);
            if (recyclerView.getTlOnRefreshListener() != null) {
                recyclerView.getTlOnRefreshListener().onLoad();
            }
        } else {
            finish();
        }
    }

    private void setMode(int MODE) {

        if (MODE == MODE_BEGIN) {
            refreshViewHolder.tv_refresh.setText("松开刷新");
            refreshViewHolder.tv_load.setText("松开加载");
        }

        if (MODE == MODE_READY) {
            refreshViewHolder.tv_refresh.setText("正在刷新");
            refreshViewHolder.tv_load.setText("正在加载");
            refreshViewHolder.pb_load.setVisibility(View.VISIBLE);
            refreshViewHolder.pb_refresh.setVisibility(View.VISIBLE);
        }
        if (MODE == MODE_INIT) {
            refreshViewHolder.tv_refresh.setText("下拉刷新");
            refreshViewHolder.tv_load.setText("上拉加载");
        }

        if (MODE == MODE_OVER) {
            if (this.mode == MODE_READY) {
                refreshViewHolder.tv_refresh.setText("刷新完成");
                refreshViewHolder.tv_load.setText("加载完成");
            }
            refreshViewHolder.pb_refresh.setVisibility(View.INVISIBLE);
            refreshViewHolder.pb_load.setVisibility(View.INVISIBLE);
        }

        this.mode = MODE;
    }

    @Override
    public void finish() {
        float translationY = recyclerView.getTranslationY();
        if (translationY == 0)
            return;

        if (!isAnimRunning) {
            long duration = (long) Math.abs(translationY * 2);

            AnimUtils.translateY(recyclerView, 0, new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {
                    setMode(MODE_OVER);
                    isAnimRunning = true;
                }

                @Override
                public void onAnimationEnd(View view) {
                    setMode(MODE_INIT);
                    isAnimRunning = false;
                }

                @Override
                public void onAnimationCancel(View view) {

                }
            }, duration);

            AnimUtils.translateY(refreshViewHolder.ll_refresh, 0, null, duration);
            AnimUtils.translateY(refreshViewHolder.ll_load, 0, null, duration);
        }

    }

    private void initRecyclerView(View child) {
        recyclerView = (TLRefreshRecyclerView) child;
        recyclerView.setTlRefresh(this);
    }

    private void initViewHolder(CoordinatorLayout parent) {
        if (refreshViewHolder == null) {
            refreshViewHolder = new RefreshViewHolder();
            refreshViewHolder.ll_refresh = (LinearLayout) parent.findViewById(R.id.ll_refresh);
            refreshViewHolder.pb_refresh = (ProgressBar) parent.findViewById(R.id.pb_refresh);
            refreshViewHolder.tv_refresh = (TextView) parent.findViewById(R.id.tv_refresh);

            refreshViewHolder.ll_load = (LinearLayout) parent.findViewById(R.id.ll_load);
            refreshViewHolder.pb_load = (ProgressBar) parent.findViewById(R.id.pb_load);
            refreshViewHolder.tv_load = (TextView) parent.findViewById(R.id.tv_load);
        }
    }

    private void initAndLayoutChild(CoordinatorLayout parent) {
        // 在进行布局时，此时肯定都是测量过年的，所以getMeasuredHeight是有值的
        MAX_REFRESH_LAYOUT_HEIGHT = refreshViewHolder.ll_refresh.getMeasuredHeight();
        BEGIN_REFRESH_HEIGHT = MAX_REFRESH_LAYOUT_HEIGHT * 3 / 4;
        refreshViewHolder.ll_refresh.layout(0, -MAX_REFRESH_LAYOUT_HEIGHT, refreshViewHolder.ll_refresh.getMeasuredWidth(), 0);
        refreshViewHolder.ll_load.layout(0, parent.getMeasuredHeight(), refreshViewHolder.ll_load.getMeasuredWidth(), parent.getMeasuredHeight() + MAX_REFRESH_LAYOUT_HEIGHT);
        recyclerView.layout(0, 0, recyclerView.getMeasuredWidth(), recyclerView.getMeasuredHeight());

//        showSize(parent);
//        showSize(refreshViewHolder.ll_refresh);
//        showSize(refreshViewHolder.ll_load);
//        showSize(recyclerView);
    }

    private void showSize(View view) {
        Log.d("my", "view.getMeasuredWidth() = " + view.getMeasuredWidth() + ", view.getMeasuredHeight" + view.getMeasuredHeight());
    }

    class RefreshViewHolder {
        LinearLayout ll_refresh;
        ProgressBar pb_refresh;
        TextView tv_refresh;

        LinearLayout ll_load;
        ProgressBar pb_load;
        TextView tv_load;
    }
}
