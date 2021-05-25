package com.tl.androidnativedemo.utils.display;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by tianlin on 2017/4/19.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class DensityUtils
{
    /**
     * 获取屏幕的每英寸的像素数量,如160， 240， 360等等
     *
     * @param context
     * @return
     */
    public static int getScreenDensityDPI(Context context)
    {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return displayMetrics.densityDpi;
    }

    /**
     * 获取屏幕的密度，如1.5,2.0,2.5
     *
     * @param context
     * @return
     */
    public static float getScreenDensity(Context context)
    {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return displayMetrics.density;
    }

    /**
     * 获取屏幕的像素宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context)
    {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕的像素高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context)
    {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

}
