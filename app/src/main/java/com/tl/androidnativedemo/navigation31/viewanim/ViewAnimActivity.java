package com.tl.androidnativedemo.navigation31.viewanim;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.ViewPropertyAnimatorListener;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;
import com.tl.androidnativedemo.utils.anim.AnimUtils;
import com.tl.androidnativedemo.utils.toast.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/10/12.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class ViewAnimActivity extends BaseActivity
{

    @BindView(R.id.bt_big1)
    Button btBig1;
    @BindView(R.id.bt_small1)
    Button btSmall1;
    @BindView(R.id.tv_text1)
    TextView tvText1;
    @BindView(R.id.bt_big2)
    Button btBig2;
    @BindView(R.id.bt_small2)
    Button btSmall2;
    @BindView(R.id.tv_text2)
    TextView tvText2;
    @BindView(R.id.tv_text3)
    TextView tvText3;
    @BindView(R.id.tv_text4)
    TextView tvText4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_anim);
        ButterKnife.bind(this);
    }

    @Override
    public void initView()
    {

    }

    @OnClick({R.id.bt_big1, R.id.bt_small1, R.id.bt_big2, R.id.bt_small2, R.id.bt_big3, R.id.bt_small3, R.id.bt_big4, R.id.bt_small4})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.bt_big1:
            {
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_scale_in);
                animation.setAnimationListener(new Animation.AnimationListener()
                {
                    @Override
                    public void onAnimationStart(Animation animation)
                    {
                        tvText1.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onAnimationEnd(Animation animation)
                    {
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation)
                    {
                    }
                });

                tvText1.startAnimation(animation);
            }
                break;
            case R.id.bt_small1:
            {
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_scale_out);

                // 为补间动画设置监听器
                animation.setAnimationListener(new Animation.AnimationListener()
                {
                    @Override
                    public void onAnimationStart(Animation animation)
                    {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation)
                    {
                        tvText1.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation)
                    {

                    }
                });
                tvText1.startAnimation(animation);
                break;
            }
            case R.id.bt_big2:
            {
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_scale_in);
                animation.setFillAfter(true);
                tvText2.startAnimation(animation);
            }
                break;
            case R.id.bt_small2:
            {
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_scale_out);
                animation.setFillAfter(true);
                tvText2.startAnimation(animation);
                break;
            }
            case R.id.bt_big3:
            {
                AnimUtils.translateX(tvText3, 200, null, 800);
            }
            break;
            case R.id.bt_small3:
            {
                AnimUtils.translateX(tvText3, 0, null, 800);

                break;
            }
            case R.id.bt_big4:
                AnimUtils.scaleShow(tvText4, new ViewPropertyAnimatorListener()
                {
                    @Override
                    public void onAnimationStart(View view)
                    {

                    }

                    @Override
                    public void onAnimationEnd(View view)
                    {

                    }

                    @Override
                    public void onAnimationCancel(View view)
                    {

                    }
                });
                break;
            case R.id.bt_small4:
                AnimUtils.scaleHide(tvText4, new ViewPropertyAnimatorListener()
                {
                    @Override
                    public void onAnimationStart(View view)
                    {

                    }

                    @Override
                    public void onAnimationEnd(View view)
                    {

                    }

                    @Override
                    public void onAnimationCancel(View view)
                    {

                    }
                });
                break;
        }
    }

    @OnClick({R.id.tv_text1, R.id.tv_text2, R.id.tv_text3, R.id.tv_text4})
    public void onTextClick(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_text1:
                ToastUtils.show(this, "tv_text1");
                break;
            case R.id.tv_text2:
                ToastUtils.show(this, "tv_text2");
                break;
            case R.id.tv_text3:
                ToastUtils.show(this, "tv_text3");
                break;
            case R.id.tv_text4:
                ToastUtils.show(this, "tv_text4");
                break;
        }
    }
}
