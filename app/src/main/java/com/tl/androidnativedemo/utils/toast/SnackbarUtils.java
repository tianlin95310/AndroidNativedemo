package com.tl.androidnativedemo.utils.toast;

import android.app.Activity;

import com.google.android.material.snackbar.Snackbar;

/**
 * Created by tianlin on 2017/3/6.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class SnackbarUtils
{
    /**
     *
     * @param context 显示Snackbar的Activity
     * @param msg 显示的消息
     */
    public static void show(Activity context, String msg)
    {
        Snackbar.make(context.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show();
    }

    /**
     *
     * @param context 显示Snackbar的Activity
     * @param msgId 消息的ID
     */
    public static void show(Activity context, int msgId)
    {
        Snackbar.make(context.findViewById(android.R.id.content), context.getString(msgId), Snackbar.LENGTH_LONG).show();
    }
}
