package com.tl.androidnativedemo.navigation71.shanxing;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;
import com.tl.androidnativedemo.navigation71.shanxingpro.TLShanXingRatioView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2018/4/13.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class ShanXingRatioActivity extends BaseActivity
{
    @BindView(R.id.tl_shanxing)
    TLShanXingRatioView tlShanxing;

    @Override
    public void initView()
    {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shanxing);
        ButterKnife.bind(this);
    }
}
