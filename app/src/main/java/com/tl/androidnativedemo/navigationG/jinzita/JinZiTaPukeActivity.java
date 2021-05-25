package com.tl.androidnativedemo.navigationG.jinzita;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;
import com.tl.androidnativedemo.utils.display.DensityUtils;
import com.tl.androidnativedemo.utils.thread.ThreadManager;
import com.tl.androidnativedemo.utils.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2018/11/26.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class JinZiTaPukeActivity extends BaseActivity {

    public int sw ;
    public int sh ;

    float pukeW;
    float pukeH;

    float dividerW;
    float dividerH;
    @BindView(R.id.ll_content)
    FrameLayout llContent;

    List<PukeInfoImg> centerPukes = new ArrayList<>();
    List<PukeInfoImg> leftPukes = new ArrayList<>();
    List<PukeInfoImg> rightPukes = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jin_zi_ta);
        ButterKnife.bind(this);

        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {
                GetImage.loadAssets(getBaseContext());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.show(getBaseContext(), "资源加载完成");
                    }
                });
            }
        });
        initView();
    }

    @Override
    public void initView() {

        sw = DensityUtils.getScreenWidth(this);
        sh = DensityUtils.getScreenHeight(this);

        pukeW = sw / (7 + (7 + 1) * 0.2f);
        pukeH = pukeW * 1.63f;

        dividerW = pukeW * 0.2f;
        dividerH = (sh - pukeH) / 9;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            llContent.removeAllViews();
            centerPukes = new ArrayList<>();
            leftPukes = new ArrayList<>();
            rightPukes = new ArrayList<>();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dividerPukes();
                    animOneByOne();
                }
            }, 500);

        }
        return super.onTouchEvent(event);
    }

    private void animOneByOne() {

        for (int k = 0; k < centerPukes.size(); k++) {
            PukeInfoImg pukeInfo = centerPukes.get(k);

            int i = pukeInfo.i;
            int j = pukeInfo.j;
            float center = i / 2.0f;

            float centerX = sw / 2 - pukeW / 2;
            float y = (i + 1) * dividerH;
            if (j < center) {
                centerX = centerX + (dividerW + pukeW) * (j - center);
            } else if (j > center) {
                centerX = centerX + (dividerW + pukeW) * (j - center);
            }
            pukeInfo.go_to(centerX, y, (k + 1) * 50);
        }

        Log.d("my", "---centerPukes.size()---" + centerPukes.size());

    }

    public class MyClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            PukeInfoImg infoImg = (PukeInfoImg) v;
            int number = infoImg.value;

            infoImg.isChoose = !infoImg.isChoose;
            if (infoImg.isChoose) {
                infoImg.choose();
            }
            else {
                infoImg.unChoose();
            }
        }
    }
    private void dividerPukes() {
        List<PukeInfo> pukes = getPukers();

        int index = 0;

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < i + 1; j++) {

                PukeInfo pukeInfo = pukes.get(index++);

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)pukeW, (int)pukeH);
                PukeInfoImg imageView = new PukeInfoImg(this);
                imageView.setBackgroundResource(R.drawable.puke_bg);
                imageView.pid = pukeInfo.id;
                imageView.i = i;
                imageView.j = j;
                imageView.setOnClickListener(new MyClick());
                Bitmap bitmap = GetImage.getBitmap(pukeInfo);
                imageView.setImageBitmap(bitmap);
                llContent.addView(imageView, layoutParams);
                imageView.go_to(0 ,0 , 0);
                centerPukes.add(imageView);
            }
        }

        for (int i = 28; i < pukes.size(); i++) {

            PukeInfo pukeInfo = pukes.get(i);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)pukeW, (int)pukeH);
            PukeInfoImg imageView = new PukeInfoImg(this);
            imageView.setBackgroundResource(R.drawable.puke_bg);
            imageView.pid = pukeInfo.id;
            imageView.setOnClickListener(new MyClick());
            Bitmap bitmap = GetImage.getBitmap(pukeInfo);
            imageView.setImageBitmap(bitmap);
            llContent.addView(imageView, layoutParams);
            imageView.go_to(0 ,0 , 0);
            leftPukes.add(imageView);
        }
    }

    private List<PukeInfo> getPukers() {
        List<PukeInfo> list = new ArrayList<>(52);
        Random random = new Random(System.currentTimeMillis());

        while (list.size() != 52) {
            int value = random.nextInt(13) + 1;
            int color = random.nextInt(4) + 1;
            PukeInfo pukeInfo = new PukeInfo();
            pukeInfo.value = value;
            pukeInfo.color = color;
            pukeInfo.id = String.valueOf(value + "," + color);
            if (!list.contains(pukeInfo)) {
                list.add(pukeInfo);
            }
        }
        return list;
    }
}
