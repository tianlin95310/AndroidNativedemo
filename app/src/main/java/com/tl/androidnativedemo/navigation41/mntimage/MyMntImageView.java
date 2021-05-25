package com.tl.androidnativedemo.navigation41.mntimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.tl.androidnativedemo.utils.bitmap.DecodeBmp;
import com.tl.androidnativedemo.utils.display.DensityUtils;
import com.tl.androidnativedemo.utils.toast.ToastUtils;

import java.io.File;

/**
 * Created by tianlin on 2017/4/19.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class MyMntImageView extends AppCompatImageView
{
    // 原始图片
    Bitmap raw_bmp;
    // 改变密度的图片
    Bitmap scaleBmp;
    // 改变采样率的图片
    Bitmap scaleSampleBmp;

    int w;
    int h;

    Paint paint;

    public MyMntImageView(Context context)
    {
        super(context);
    }
    public MyMntImageView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        paint = new Paint();
        paint.setAlpha(66);

        File file = new File(Environment.getExternalStorageDirectory(), "1.png");

        if(!file.exists())
        {
            ToastUtils.show(context, file.getName() + "不存在或者没有内存卡权限");
            return;
        }

        // 1，加载原始图片大小,图片是多大就是多大，300 * 630
        raw_bmp = BitmapFactory.decodeFile(file.getAbsolutePath());

        if(raw_bmp == null)
        {
            ToastUtils.show(context, "文件不存在或者没有内存卡权限");
            return;
        }
        // 2，通过目标密度来改变图片，对于内存卡上的图片，inTargetDensity无法改变大小
        BitmapFactory.Options option = new BitmapFactory.Options();
        // 加载的图片的实际密度，只能对应用内部/data/data的图片有效，内存卡内的图片无效
        option.inTargetDensity = DensityUtils.getScreenDensityDPI(context) / 2;
        scaleBmp = BitmapFactory.decodeFile(file.getAbsolutePath(), option);

        // 3，通过改变采样率来改变图片，对于内存卡上的图片也可以通过采样率改变大小
        // 缩小只能是2,4,8倍的比率，并不能保存完全是指定的大小
        scaleSampleBmp = DecodeBmp.decodeInSample(raw_bmp.getWidth() / 4, raw_bmp.getHeight() / 4, file.getAbsolutePath());

        Log.d("my", "------------raw_bmp-----------\n");
        getBmpInfo(raw_bmp);
        Log.d("my", "-------------scaleBmp------------\n");
        getBmpInfo(scaleBmp);
        Log.d("my", "-------------scaleSampleBmp------------\n");
        getBmpInfo(scaleSampleBmp);

    }

    private void getBmpInfo(Bitmap bmp)
    {
        // 得到图片的长宽
        Log.d("my", "getWidth = " + bmp.getWidth());
        Log.d("my", "getHeight = " + bmp.getHeight());
        // 得到图片的位信息
        Log.d("my", "getConfig = " + bmp.getConfig());
        // 获取当前设备的密度
        Log.d("my", "getDensity = " + bmp.getDensity());
        // 得到某个位置的像素
        Log.d("my", "getPixel = " + String.format("%x", bmp.getPixel(w / 2, h/  2)));
        // 是否具有灰度
        Log.d("my", "hasAlpha = " + bmp.hasAlpha());
        // 是否开启MipMap技术加速
        Log.d("my", "hasMipMap = " + bmp.hasMipMap());
        // 图像是否可以修改
        Log.d("my", "isMutable = " + bmp.isMutable());
        // 是否已经回收
        Log.d("my", "isRecycled = " + bmp.isRecycled());
        // 字节数
        Log.d("my", "getByteCount = " + bmp.getByteCount());
        // 得到宽字节数，一个像素有4个字节，分别为ARGB
        Log.d("my", "getRowBytes = " + bmp.getRowBytes());
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawBitmap(raw_bmp, 0, 0, paint);

        canvas.drawBitmap(scaleBmp, raw_bmp.getWidth(), 0, paint);

        canvas.drawBitmap(scaleSampleBmp, raw_bmp.getWidth() + scaleBmp.getWidth(), 0, paint);
    }
}
