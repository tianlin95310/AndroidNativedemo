package com.tl.androidnativedemo.navigation01.floatintop;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;
import com.tl.androidnativedemo.base.IBaseView;
import com.tl.androidnativedemo.base.OnlyTextAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2017/9/25.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class FloatInTopActivity extends BaseActivity implements IBaseView
{
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_in_top);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    public void initView()
    {
        recycler.setLayoutManager(new LinearLayoutManager(this));

        List<String> list = new ArrayList<>();

        for (int i = 0; i < 30; i++)
        {
            list.add("string " + (i + 1));
        }

        OnlyTextAdapter adapter = new OnlyTextAdapter(this, list);
        recycler.setAdapter(adapter);
    }
}
