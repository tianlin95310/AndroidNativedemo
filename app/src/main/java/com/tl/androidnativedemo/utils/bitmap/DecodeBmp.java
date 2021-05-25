package com.tl.androidnativedemo.utils.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by tianlin on 2017/4/20.
 * Tel : 15071485692
 * QQ : 953108373
 * Function : 已制定的DPI来decode图片，貌似只能对应用内部的图片有效
 *          sdcard上的图片无效
 */

public class DecodeBmp
{
    /**
     * 以指定的密度加载应用内部图片
     * @param context
     * @param imgID
     * @param densityDPI    目标的dpi
     * @return
     */
    public static Bitmap decodeBmpWithDPI(Context context, int imgID, int densityDPI)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inTargetDensity = densityDPI;
        return BitmapFactory.decodeResource(context.getResources(), imgID, options);
    }

    /**
     *
     * @param context
     * @param x     你想要的图片大小，是一个大致的，以其中一个为准
     * @param y
     * @param resId apk资源文件
     * @return
     */
    public static Bitmap decodeInSample(Context context, int x, int y, int resId)
    {
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, ops);

        int srcX = ops.outWidth;
        int srcY = ops.outHeight;
        System.out.println("srcX = " + srcX + ", srcY = " + srcY);

        int rateX = srcX / x;
        int rateY = srcY / y;

        int rate = 0;

        if(rateX > 1 && rateY > 1)
        {
            if(rateX >= rateY)
                rate = rateX;
            else if(rateY > rateX)
                rate = rateY;
        }

        ops.inSampleSize = rate;
        ops.inJustDecodeBounds = false;

        Bitmap obj = BitmapFactory.decodeResource(context.getResources(), resId, ops);

        return obj;
    }


    /**
     *
     * @param x
     * @param y
     * @param path sdCard的图片路径
     * @return
     */
    public static Bitmap decodeInSample(int x, int y, String path)
    {
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, ops);

        int srcX = ops.outWidth;
        int srcY = ops.outHeight;
        System.out.println("srcX = " + srcX + ", srcY = " + srcY);

        int rateX = srcX / x;
        int rateY = srcY / y;

        int rate = 0;

        if(rateX > 1 && rateY > 1)
        {
            if(rateX >= rateY)
                rate = rateX;
            else if(rateY > rateX)
                rate = rateY;
        }

        ops.inSampleSize = rate;
        ops.inJustDecodeBounds = false;

        Bitmap obj = BitmapFactory.decodeFile(path, ops);

        return obj;
    }

}
