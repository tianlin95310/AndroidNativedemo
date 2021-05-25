package com.tl.androidnativedemo.navigation71.shanxingpro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2018/6/21.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class ShanXingProgressActivity extends BaseActivity {

    @BindView(R.id.tl_shanxing)
    TLShanXingRatioView2 tlShanxing;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shanxing_progress);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initView() {

        tlShanxing.setOnAnimOverListener(new TLShanXingRatioView2.OnAnimOverListener() {
            @Override
            public void onAnimOver() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String transitionName = ViewCompat.getTransitionName(tlShanxing) == null ? "" : ViewCompat.getTransitionName(tlShanxing);
                        Log.d("my", "transitionName = " + transitionName);

                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                ShanXingProgressActivity.this, tlShanxing, transitionName);

                        Intent intent = new Intent(getBaseContext(), NewActivity.class);
                        startActivity(intent, options.toBundle());

                    }
                });

            }
        });
    }

    @OnClick(R.id.tl_shanxing)
    public void onViewClicked() {
        response(this, "tl_shanxing");
        tlShanxing.start();
    }
}
