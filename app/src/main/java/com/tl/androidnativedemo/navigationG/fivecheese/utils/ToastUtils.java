package com.tl.androidnativedemo.navigationG.fivecheese.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by tianlin on 2017/3/17.
 * Tel : 15071485690
 * QQ : 953108373
 * Function : Toast提示工具
 */

public class ToastUtils
{
    /**
     * 提示工具
     * @param context
     * @param msg 消息字符换
     */
    public static void show(Context context,String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * @param context
     * @param msgId 消息ID
     */
    public static void show(Context context,int msgId)
    {
        Toast.makeText(context, context.getString(msgId), Toast.LENGTH_SHORT).show();
    }
}
