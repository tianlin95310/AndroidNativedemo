package com.tl.androidnativedemo.navigation01.behavior.pullrefresh;

import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;
import com.tl.androidnativedemo.base.OnlyTextAdapter;
import com.tl.androidnativedemo.utils.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2017/10/18.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class PullRefreshActivity extends BaseActivity implements TLPullRefreshRecycleView.OnRefreshListener {
    @BindView(R.id.ll_refresh)
    LinearLayout llRefresh;
    @BindView(R.id.rv)
    TLPullRefreshRecycleView rv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_refresh)
    TextView tvRefresh;

    @Override
    public void initView() {

        toolbar.setTitle("半透明状态栏");
        setSupportActionBar(toolbar);

        rv.setLayoutManager(new LinearLayoutManager(this));
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list1.add("string " + (i + 1));
        }
        OnlyTextAdapter adapter1 = new OnlyTextAdapter(this, list1);
        rv.setAdapter(adapter1);

        rv.setOnRefreshListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_refresh);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 主线程
                ToastUtils.show(PullRefreshActivity.this, "onRefresh");
                rv.close();
            }
        }, 2000);

    }

}
