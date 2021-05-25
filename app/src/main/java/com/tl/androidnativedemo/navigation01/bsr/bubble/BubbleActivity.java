package com.tl.androidnativedemo.navigation01.bsr.bubble;

import android.animation.Animator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2018/7/3.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class BubbleActivity extends BaseActivity implements Animator.AnimatorListener{

    @BindView(R.id.BubbleView)
    BubbleView bubbleView;

    @Override
    public void initView() {
        List<Drawable> drawables = new ArrayList<>();
        drawables.add(ContextCompat.getDrawable(this, R.drawable.android));
        bubbleView.setDrawableList(drawables);
        bubbleView.setAnimatorListener(this);

        bubbleView.startAnimation(bubbleView.getWidth(), bubbleView.getHeight());
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        bubbleView.startAnimation(bubbleView.getWidth(), bubbleView.getHeight());
//    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        bubbleView.startAnimation(bubbleView.getWidth(), bubbleView.getHeight());
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble);
        ButterKnife.bind(this);

        initView();
    }
}
