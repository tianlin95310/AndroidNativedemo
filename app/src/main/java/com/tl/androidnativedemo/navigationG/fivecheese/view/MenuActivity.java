package com.tl.androidnativedemo.navigationG.fivecheese.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.navigationG.fivecheese.view.robotfight.RobotFightActivity;
import com.tl.androidnativedemo.navigationG.fivecheese.view.userfight.UserFightActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener
{

    LinearLayout ll1;
    LinearLayout ll2;
    LinearLayout ll3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initView();
    }

    private void initView()
    {
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll1.setOnClickListener(this);
        ll2.setOnClickListener(this);
        ll3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.ll1:
            {
                Intent intent = new Intent(this, RobotFightActivity.class);
                startActivity(intent);
            }
                break;

            case R.id.ll2:
            {
                Intent intent = new Intent(this, UserFightActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.ll3:
                finish();
                break;


        }

    }
}
