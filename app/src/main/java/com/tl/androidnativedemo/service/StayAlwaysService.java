package com.tl.androidnativedemo.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Created by tianlin on 2017/6/16.
 * Tel : 15071485690
 * QQ : 953108373
 * Function : 永驻服务测试，发现并不能
 */

public class StayAlwaysService extends Service
{

    private static final int SERVICE_ID = 0x1234;
    Handler handler = new Handler()
    {
    };

    Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {
            Log.d("my", "StayAlwaysService run");
            handler.postDelayed(runnable, 2000);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        // 让服务成为前台服务，会在状态栏显示
        startForeground(SERVICE_ID, new Notification());

        // START_STICKY如果主进程没有被干掉，在他被干掉时会被系统重新启动
        // START_NOT_STICKY与上面相反
        return START_STICKY;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        handler.post(runnable);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        // 在触发了onDestroy的情况下重启服务
        Intent intent = new Intent(this, StayAlwaysService.class);
        startService(intent);

    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        return super.onUnbind(intent);
    }

    /**
     * 判断服务是否在运行
     * @return
     */
    public boolean isServiceRunning()
    {
        boolean isServiceRunning = false;
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if ("tl.com.testmaterialdesign.service.StayAlwaysService".equals(service.service.getClassName()))
            {
                isServiceRunning = true;
            }
        }

        return isServiceRunning;
    }
}
