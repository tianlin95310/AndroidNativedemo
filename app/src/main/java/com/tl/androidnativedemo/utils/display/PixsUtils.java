package com.tl.androidnativedemo.utils.display;

import android.content.Context;

/**
 * Created by tianlin on 2017/4/7.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class PixsUtils
{
    /**
     * 将px值转换为dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @param context （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dp(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param context  （DisplayMetrics类中属性density）
     * @return
     */
    public static int dp2px(Context context, float dipValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
