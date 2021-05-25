package com.tl.androidnativedemo.navigation11.gridrecycler;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2017/12/23.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class GridRecyclerActivity extends BaseActivity
{
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    public void initView()
    {

        // 这种布局导致Item（具体的宽度和高度，如200dp等）的宽度和高度没有意义，不能限死，会有bug，用match_parent或者wrap_content
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);

        List<GridVo> gridVos = new ArrayList<>();
        for(int i = 0; i <= 50; i++) {
            GridVo gridVo = new GridVo();
            gridVo.path = R.drawable.man_under;
            gridVo.no = "no---" + i;
            gridVos.add(gridVo);
        }

        GridAdapter gridAdapter = new GridAdapter(gridVos, this);
        recyclerView.setAdapter(gridAdapter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_recycler);
        ButterKnife.bind(this);
        initView();
    }
}
