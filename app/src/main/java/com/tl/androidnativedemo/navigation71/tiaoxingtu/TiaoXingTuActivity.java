package com.tl.androidnativedemo.navigation71.tiaoxingtu;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;
import com.tl.androidnativedemo.utils.ui.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2018/4/8.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class TiaoXingTuActivity extends BaseActivity
{

    @BindView(R.id.tl_tiaoxing1)
    TLTiaoXingTuView tlTiaoxing;
    @BindView(R.id.tl_tiaoxing2)
    TLTiaoXingTuView tlTiaoxing2;
    @BindView(R.id.bt_1)
    Button bt1;
    @BindView(R.id.bt_2)
    Button bt2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiaoxingtu);
        ButterKnife.bind(this);
    }

    @Override
    public void initView()
    {

    }

    @OnClick(R.id.bt_1)
    public void onBt1Clicked()
    {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 8; i++)
        {
            list.add("" + (i + 1));
        }
        tlTiaoxing.setHengZuoBiaos(list);

        List<String> left = new ArrayList<>();
        for (int i = 0; i < 6; i++)
        {
            left.add(String.valueOf((5 - i) * 1000));
        }
        tlTiaoxing.setZongZuoBiaosLeft(left);

        List<String> right = new ArrayList<>();
        for (int i = 0; i < 6; i++)
        {
            right.add(String.valueOf((5 - i) * 0.5));
        }
        tlTiaoxing.setZongZuoBiaosRight(right);

        Random random = new Random();
        List<DataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < 8; i++)
        {
            DataSet dataSet = new DataSet();

            int value1 = random.nextInt(5000);
            int value2 = random.nextInt(5000);
            double rate = random.nextDouble() * 2.5;

            dataSet.data1 = 1.0 * (5000 - value1) / 1000;
            dataSet.data2 = 1.0 * (5000 - value2) / 1000;
            dataSet.rate = (2.5 - rate) / 0.5;
            dataSets.add(dataSet);

            Log.d("my", "value1 = " + value1 + ", value2 = " + value2 + ", rate = " + rate);
        }

        // 出发刷新
        tlTiaoxing.setDataSets(dataSets);
    }

    @OnClick(R.id.bt_2)
    public void onBt2Clicked()
    {

        tlTiaoxing2.setDrawDoubleBar(false);
        tlTiaoxing2.setDrawZhexian(false);

        // 同真同假
        tlTiaoxing2.setDrawAnotherLine(false);
        tlTiaoxing2.setDrawZuoBiaoTextLeftY(false);

        tlTiaoxing2.setDrawZuoBiaoTextX(false);
        tlTiaoxing2.setDrawZuoBiaoTextRightY(false);
        tlTiaoxing2.setDrawValueOnBar(true);

        Random random = new Random();
        List<DataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < 2; i++)
        {
            DataSet dataSet = new DataSet();

            int value1 = random.nextInt(5000);
            dataSet.value = String.valueOf(value1);
            dataSet.data1 = 1.0 * (5000 - value1) / 1000;

            if(i == 0) {
                dataSet.color = "#5DB1CD";
            }
            else if(i == 1) {
                dataSet.color = "#FF9156";
            }
            dataSets.add(dataSet);

            Log.d("my", "value1 = " + value1);
        }
        tlTiaoxing2.setDataSets(dataSets);
    }
}
