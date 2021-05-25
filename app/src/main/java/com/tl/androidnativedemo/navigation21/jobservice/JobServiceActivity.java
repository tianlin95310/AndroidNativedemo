package com.tl.androidnativedemo.navigation21.jobservice;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;
import com.tl.androidnativedemo.service.MyJobService;
import com.tl.androidnativedemo.service.StayAlwaysService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/7/20.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class JobServiceActivity extends BaseActivity
{

    public static int jobId = 1;
    
    @BindView(R.id.bt_start)
    Button btStart;
    @BindView(R.id.bt1_start_job)
    Button bt1StartJob;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_service);
        ButterKnife.bind(this);
    }

    @Override
    public void initView()
    {

    }

    @OnClick({R.id.bt_start, R.id.bt1_start_job})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.bt_start:

                // 不关闭服务时，Activity退出，服务不会关闭，但进程结束后，服务就关闭了
                Intent intent = new Intent(this, StayAlwaysService.class);
                this.startService(intent);
                
                break;
            case R.id.bt1_start_job:

                // 不关闭服务时，Activity退出，服务不会关闭，但进程结束后，服务就关闭了
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    scheduler();
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scheduler()
    {
        JobScheduler scheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(this, MyJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(++jobId, componentName);

        // 设置JobService执行的最小延时时间
        builder.setMinimumLatency(1000);

        // 设置JobService执行的最晚时间
        builder.setOverrideDeadline(1000);

        // 设置执行的网络条件
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        scheduler.schedule(builder.build());
    }
}
