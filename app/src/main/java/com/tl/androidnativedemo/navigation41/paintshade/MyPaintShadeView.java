package com.tl.androidnativedemo.navigation41.paintshade;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.tl.androidnativedemo.R;

/**
 * Created by tianlin on 2017/11/25.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class MyPaintShadeView extends View
{

    Paint paint1;

    Paint paint2;

    Bitmap bitmap;

    Shader shader;
    private void initView(Context context)
    {
        paint1 = new Paint();
        paint2 = new Paint();

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.android);

        Log.d("my", "MyPaintShadeView getWidth = " + bitmap.getWidth() + ", getHeight = " + bitmap.getHeight());

        // 整个单元重复
        BitmapShader bitmapShader1 = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        paint1.setShader(bitmapShader1);

        // 对边缘进行扩散，貌似只对边缘的一个像素
        BitmapShader bitmapShader2 = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint2.setShader(bitmapShader2);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        int radius = 100;

        // 用填充笔画圆
        canvas.drawCircle(radius, radius ,radius, paint1);

        // 画bitmap
        Rect rect = new Rect();
        rect.left = 0;
        rect.top = radius * 3;
        rect.right = 2 * radius;
        rect.bottom = 5 * radius;
        canvas.drawBitmap(
                bitmap,
                null,   // 显示图片的哪一个部分，为null显示真个图片
                rect,       // 图片最终显示到哪里去，如果bitmap大小不够，会进行放缩
                paint1);
    }

    public MyPaintShadeView(Context context)
    {
        super(context);
    }

    public MyPaintShadeView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        initView(context);
    }



    public MyPaintShadeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyPaintShadeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


}
