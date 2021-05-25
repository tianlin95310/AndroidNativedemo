package com.tl.androidnativedemo.navigation71.check;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;
import com.tl.androidnativedemo.navigation71.shanxing.TLCycleRatioView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2017/7/7.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class OnKeyCheckActivity extends BaseActivity implements ICheckView
{

    @BindView(R.id.tl_index_view)
    TLCycleRatioView tlIndexView;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.rv_checklist)
    RecyclerView rvChecklist;

    CheckControl checkControl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check);
        ButterKnife.bind(this);

        initView();

        checkControl = new CheckControl();
        checkControl.attachView(this);
        checkControl.initData();
    }

    @Override
    public void addTask(CheckItemTaskVo checkItemTaskVo)
    {

        CheckAdapter checkAdapter = (CheckAdapter) rvChecklist.getAdapter();
        if(checkAdapter == null)
            return;

        List<CheckItemTaskVo> list = checkAdapter.getTaskVoList();
        if(list == null)
            return;
    }

    @Override
    public void initView()
    {
        rvChecklist.setLayoutManager(new LinearLayoutManager(this));
        List<CheckItemTaskVo> taskVos = new ArrayList<>();
        CheckAdapter checkAdapter = new CheckAdapter(this, taskVos);
        rvChecklist.setAdapter(checkAdapter);


//        new Thread()
//        {
//            @Override
//            public void run()
//            {
//                super.run();
//
//                int i = 0;
//
////                tlIndexView.setMode(TLCycleRatioView.MODE_SCAN);
////                for (; i <= 100; i++)
////                {
////                    tlIndexView.setPercentSync(i);
////                    SystemClock.sleep(50);
////                }
//
//                tlIndexView.setMode(TLCycleRatioView.MODE_RATIO);
//                tlIndexView.setUnit("分");
//                for (; i <= 100; i++)
//                {
//                    tlIndexView.setScoreSync(i);
//                    SystemClock.sleep(50);
//                }
//
//            }
//        }.start();
    }
}
