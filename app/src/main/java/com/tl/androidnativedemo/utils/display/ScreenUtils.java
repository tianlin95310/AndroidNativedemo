package com.tl.androidnativedemo.utils.display;

import android.app.Activity;
import android.graphics.Rect;
import android.view.Window;

/**
 * Created by tianlin on 2018/2/9.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class ScreenUtils
{
    /**
     * 获取标题栏的高度
     *
     * @param activity
     * @return
     */
    public static int getTitleHeight(Activity activity) {
        Rect rect = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight = contentViewTop - statusBarHeight;
        return titleBarHeight;
    }

    /**
     *
     * 获取状态栏高度
     *
     * @param activity
     * @return
     */
    public static int getStateHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }
}
