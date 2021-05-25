package com.tl.androidnativedemo.navigation22.move;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2018/8/10.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class PopMoveActivity extends BaseActivity {

    @BindView(R.id.ll_index)
    LinearLayout llIndex;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pop_window);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        llIndex.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        int position = getPosition(event);
                        tvContent.setText(String.valueOf(position));
                        tvContent.setX(event.getX());
                        break;
                    case MotionEvent.ACTION_UP:
                        break;

                }

                return true;
            }
        });
    }


    private int getPosition(MotionEvent event) {
        int itemWidth = llIndex.getMeasuredWidth() / llIndex.getChildCount();
        int position = (int) (event.getX() / itemWidth);

        return position;
    }
}
