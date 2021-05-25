package com.tl.androidnativedemo.service.bindservice;

import android.os.Binder;
import android.util.Log;

public class MyBinder extends Binder
{
    /**
     * 调用方式1，通过方法
     * @param msg
     */
    public void sendDataToService(String msg)
    {
        Log.d("my", msg);
    }

    public String returnData()
    {
        return "我是Service返回的数据";
    }
}