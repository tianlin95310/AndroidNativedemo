package com.tl.androidnativedemo.navigation31.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;
import com.tl.androidnativedemo.utils.display.DensityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2018/6/26.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class ActivityViewTransition extends BaseActivity {

    @BindView(R.id.ll_content)
    RelativeLayout llContent;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transition);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupEnterAnimation();
            setupExitAnimation();
        }

        initView();
    }

    @Override
    public void initView() {

    }

    // 退出动画
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupExitAnimation() {

        Slide slide = new Slide();
        slide.setDuration(300);
        getWindow().setReturnTransition(slide);

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterAnimation() {
        Transition transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.arc_motion);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

                llContent.setVisibility(View.INVISIBLE);
                Log.d("my", "onTransitionStart");
            }

            @Override
            public void onTransitionEnd(Transition transition) {

                Log.d("my", "onTransitionEnd");
                transition.removeListener(this);
                llContent.setVisibility(View.VISIBLE);
                anim();
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        });
        getWindow().setSharedElementEnterTransition(transition);

        Slide slide = new Slide();
        slide.setDuration(300);
        slide.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                fab.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        getWindow().setSharedElementReturnTransition(slide);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void anim() {
        int cx = (fab.getLeft() + fab.getRight()) / 2;
        int cy = (fab.getTop() + fab.getBottom()) / 2;

        Animator anim = ViewAnimationUtils.createCircularReveal(llContent,
                cx,
                cy,
                0,
                DensityUtils.getScreenHeight(this));
        anim.setDuration(500);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });

        anim.start();
    }

}
