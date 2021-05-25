package com.tl.androidnativedemo.navigation01.gaussblur;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2018/5/29.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class GaussianBlurActivity extends BaseActivity
{
    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.bt2)
    Button bt2;

    @Override
    public void initView()
    {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gauss_blur);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt1)
    public void onBt1Clicked()
    {
        Intent intent = new Intent(this, GaussianBlurImageActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.bt2)
    public void onBt2Clicked()
    {
        Intent intent = new Intent(this, GaussianBlurNoImageActivity.class);
        startActivity(intent);
    }
}
