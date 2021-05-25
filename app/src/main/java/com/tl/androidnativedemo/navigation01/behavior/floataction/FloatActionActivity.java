package com.tl.androidnativedemo.navigation01.behavior.floataction;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.OnlyTextAdapter;
import com.tl.androidnativedemo.utils.anim.AnimUtils;
import com.tl.androidnativedemo.utils.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/6/29.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class FloatActionActivity extends AppCompatActivity
{
    @BindView(R.id.rv_floataction)
    RecyclerView rvFloataction;
    @BindView(R.id.fab_to_top)
    FloatingActionButton fabToTop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_floataction);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        AnimUtils.translateX(fabToTop, fabToTop.getWidth(), null, 800);
    }

    private void initView()
    {
        rvFloataction.setLayoutManager(new LinearLayoutManager(this));

        List<String> list = new ArrayList<>();

        for (int i = 0; i < 30; i++)
        {
            list.add("string " + (i + 1));
        }

        OnlyTextAdapter adapter = new OnlyTextAdapter(this, list);
        rvFloataction.setAdapter(adapter);
    }

    @OnClick(R.id.fab_to_top)
    public void onViewClicked()
    {
        ToastUtils.show(this, "fab_to_top click");
        rvFloataction.smoothScrollToPosition(1);
    }
}
