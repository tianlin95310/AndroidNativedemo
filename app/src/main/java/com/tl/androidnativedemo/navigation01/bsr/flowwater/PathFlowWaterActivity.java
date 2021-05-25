package com.tl.androidnativedemo.navigation01.bsr.flowwater;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2018/3/26.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class PathFlowWaterActivity extends BaseActivity {
    @BindView(R.id.pfv)
    PathFlowView pfv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_water);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    public void initView() {
    }
}
