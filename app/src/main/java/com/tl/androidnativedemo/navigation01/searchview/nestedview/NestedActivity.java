package com.tl.androidnativedemo.navigation01.searchview.nestedview;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tl.androidnativedemo.R;

/**
 * Created by tianlin on 2017/3/15.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class NestedActivity extends AppCompatActivity
{
    Toolbar toolbar;

    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested);

        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        textView = (TextView) findViewById(R.id.nested_tv);

        initView();
    }

    private void initView()
    {
        String key = getIntent().getStringExtra("key");
        textView.setText(key);

        toolbar.setTitle("nestedview");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
