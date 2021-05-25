package com.tl.androidnativedemo.utils.anim;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorUpdateListener;

/**
 * Created by tianlin on 2017/6/30.
 * Tel : 15071485690
 * QQ : 953108373
 * Function : 属性动画工具
 */

public class AnimUtils
{

    /**
     * 放大到显示
     * @param view
     * @param viewPropertyAnimatorListener 动画监听器
     */
    public static void scaleShow(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(0);
        ViewCompat.animate(view)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .alpha(1.0f)
                .setDuration(800)
                .setListener(viewPropertyAnimatorListener)
                .setInterpolator(new AccelerateInterpolator())
                .start();
    }

    /**
     * 缩小到消失
     * @param view
     * @param viewPropertyAnimatorListener
     */
    public static void scaleHide(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(0);
        ViewCompat.animate(view)
                .scaleX(1.0f)
                .scaleY(0.0f)
                .alpha(0.0f)
                .setDuration(800)
                .setListener(viewPropertyAnimatorListener)
                .setInterpolator(new AccelerateInterpolator())
                .start();
    }

    /**
     * 水平平移
     * @param dx 平移的距离
     * @param viewPropertyAnimatorListener
     * @param duration
     */
    public static void translateX(View view, int dx, ViewPropertyAnimatorListener viewPropertyAnimatorListener, long duration)
    {
        ViewCompat.animate(view)
                .translationX(dx)
                .setDuration(duration)
                .setListener(viewPropertyAnimatorListener)
                .start();

    }

    /**
     * 垂直平移
     * @param dy
     * @param viewPropertyAnimatorListener
     * @param duration
     */
    public static void translateY(View view, int dy, ViewPropertyAnimatorListener viewPropertyAnimatorListener, long duration)
    {
        ViewCompat.animate(view)
                .translationY(dy)
                .setDuration(duration)
                .setListener(viewPropertyAnimatorListener)
                .start();
    }

    /**
     * 透明度动画
     * @param view
     * @param alpha
     * @param viewPropertyAnimatorListener
     * @param duration
     */
    public static void alpha(View view, float alpha, long duration,
                             ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener,     // 位置更新监听
                             ViewPropertyAnimatorListener viewPropertyAnimatorListener)
    {
        ViewCompat.animate(view)
                .alpha(alpha)
                .setDuration(duration)
                .setUpdateListener(viewPropertyAnimatorUpdateListener)
                .setListener(viewPropertyAnimatorListener)
                .start();
    }

    /**
     * 缩放动画
     * @param view
     * @param scaleX
     * @param scaleY
     * @param viewPropertyAnimatorListener
     * @param duration
     */
    public static void scale(View view, float scaleX, float scaleY, ViewPropertyAnimatorListener viewPropertyAnimatorListener, long duration)
    {
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight() / 2);

        ViewCompat.animate(view)
                .scaleX(scaleX)
                .scaleY(scaleY)
                .setDuration(duration)
                .setListener(viewPropertyAnimatorListener)
                .start();
    }

    /**
     * 缩放
     * @param view
     * @param scaleX
     * @param scaleY
     * @param viewPropertyAnimatorListener
     * @param delay     全局延时时间，设置了之后会影响其他的动画
     * @param duration
     */
    public static void scale(View view,
                             float scaleX,
                             float scaleY,
                             ViewPropertyAnimatorListener viewPropertyAnimatorListener,
                             long delay,
                             long duration)
    {
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight() / 2);

        ViewCompat.animate(view)
                .scaleX(scaleX)
                .scaleY(scaleY)
                .setDuration(duration)
                .setStartDelay(delay)
                .setListener(viewPropertyAnimatorListener)
                .start();
    }

    /**
     * 平移到某处
     * @param view
     * @param x     目标位置的坐标
     * @param y
     * @param delay
     * @param duration
     */
    public static void go_to(View view, float x, float y, long delay, long duration) {
        ViewCompat.animate(view).
                x(x).
                y(y).
                setStartDelay(delay)
                .setDuration(duration)
                .setInterpolator(new LinearInterpolator())
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {

                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        view.setX(x);
                        view.setY(y);
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                })
                .start();
    }


}
