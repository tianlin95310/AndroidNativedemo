package com.tl.androidnativedemo.navigation01.behavior.doublemove;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2017/10/13.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class BehaviorMoveActivity extends BaseActivity
{

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_move_recycle);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    public void initView()
    {
        button.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                button.setX(event.getRawX() - button.getWidth() / 2);
                button.setY(event.getRawY() - button.getHeight() * 3 / 2);
                return false;
            }
        });
    }
}
