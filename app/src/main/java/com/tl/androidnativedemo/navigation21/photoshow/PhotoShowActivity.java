package com.tl.androidnativedemo.navigation21.photoshow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2017/7/20.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class PhotoShowActivity extends BaseActivity
{

    @BindView(R.id.iv_photo_show)
    ImageView ivPhotoShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_show);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView()
    {
        Intent intent = getIntent();
        String file = intent.getStringExtra("file");

        Bitmap bitmap = BitmapFactory.decodeFile(file);
        if (bitmap != null)
            ivPhotoShow.setImageBitmap(bitmap);
    }
}
