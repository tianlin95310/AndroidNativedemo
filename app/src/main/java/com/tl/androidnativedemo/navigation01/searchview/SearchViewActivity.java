package com.tl.androidnativedemo.navigation01.searchview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;
import com.tl.androidnativedemo.navigation01.searchview.nestedview.NestedActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/7/20.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class SearchViewActivity extends BaseActivity
{

    @BindView(R.id.sv_search_view)
    SearchView svSearchView;
    @BindView(R.id.bt_search_view)
    Button btSearchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        ButterKnife.bind(this);
    }

    @Override
    public void initView()
    {
        svSearchView.setOnSearchClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(SearchViewActivity.this, "key = " + svSearchView.getQuery(), Toast.LENGTH_SHORT).show();
            }
        });
        svSearchView.setOnCloseListener(new SearchView.OnCloseListener()
        {
            @Override
            public boolean onClose()
            {
                Toast.makeText(SearchViewActivity.this, "onClose", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @OnClick(R.id.bt_search_view)
    public void onViewClicked()
    {
        Toast.makeText(this, "key = " + svSearchView.getQuery(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, NestedActivity.class);
        intent.putExtra("key", svSearchView.getQuery().toString());
        startActivity(intent);
    }
}
