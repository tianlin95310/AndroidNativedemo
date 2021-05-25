package com.tl.androidnativedemo.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.tl.androidnativedemo.navigation21.jobservice.JobServiceActivity;


/**
 * Created by tianlin on 2017/6/23.
 * Tel : 15071485690
 * QQ : 953108373
 * Function : 测试jobService能否实现永驻，发现并不能
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService
{
    Handler handler = new Handler()
    {
    };

    Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {
            Log.d("my", "MyJobService run");
            handler.postDelayed(runnable, 2000);
        }
    };

    @Override
    public boolean onStartJob(JobParameters params)
    {
        Log.i("my", "MyJobService onStartJob = " + params.getJobId());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params)
    {
        scheduler();
        Log.i("my", "MyJobService onStopJob = " + params.getJobId());
        return true;
    }

    @Override
    public void onCreate()
    {
        Log.d("my", "MyJobService onCreate");
        super.onCreate();
        handler.post(runnable);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d("my", "MyJobService onStartCommand");
        return START_NOT_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scheduler()
    {
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(this, MyJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(++JobServiceActivity.jobId, componentName);

        // 设置JobService执行的最小延时时间
        builder.setMinimumLatency(1000);

        // 设置JobService执行的最晚时间
        builder.setOverrideDeadline(1000);

        // 设置执行的网络条件
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        scheduler.schedule(builder.build());
    }
}
