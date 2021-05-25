package com.tl.androidnativedemo.navigation41.imgdivide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.utils.display.DensityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/4/17.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class ImageDivideActivity extends AppCompatActivity
{
    float rateX = 1.0f;
    float rateY = 1.0f;

    Matrix matrix;
    @BindView(R.id.f8_scale_in)
    Button f8ScaleIn;
    @BindView(R.id.f8_scale_out)
    Button f8ScaleOut;

    int man_w;
    int man_h;

    int bust_w;
    int bust_h;
    @BindView(R.id.iv_man_head)
    ImageView ivManHead;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.iv_man_waist_leg)
    ImageView ivManWaistLeg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_matrix_operate);
        ButterKnife.bind(this);
        initView();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);

        // 通过这里得到的图片是600 * 1260的，但图片的实际大小是300 * 630的，所以图片是以
        // 2倍的密度放大，但手机的密度是3，图片确实2倍的密度放大
        // 我们用man_h / 630能得到实际的加载密度
        Bitmap man = BitmapFactory.decodeResource(getResources(), R.drawable.man);

        man_w = man.getWidth();
        man_h = man.getHeight();

        Log.d("my", "getHeight" + man.getHeight() + ", getWidth = " + man.getWidth());
        Log.d("my", "density = " + DensityUtils.getScreenDensity(this));

        float density = man_h / 630;
        Bitmap head = Bitmap.createBitmap(man, 0, 0, man_w, (int) (100 * density));

        Bitmap top = Bitmap.createBitmap(man, 0, (int)(100 * density), man_w, (int)(280 * density));

        Bitmap leg = Bitmap.createBitmap(man, 0, (int)(380 * density), man_w, (int)(250 * density));

        ivManHead.setImageBitmap(head);
        ivTop.setImageBitmap(top);
        ivManWaistLeg.setImageBitmap(leg);

        matrix = ivTop.getMatrix();
        bust_w = top.getWidth();
        bust_h = top.getHeight();

    }

    private void initView()
    {
    }

    @OnClick({R.id.f8_scale_in, R.id.f8_scale_out})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.f8_scale_in:
                rateX = rateX - 0.01f;
                matrix.setScale(rateX, 1, bust_w / 2, bust_h / 2);
                break;
            case R.id.f8_scale_out:
                rateX = rateX + 0.01f;
                matrix.setScale(rateX, 1, bust_w / 2, bust_h / 2);
                break;
        }

        ivTop.setImageMatrix(matrix);
    }
}
