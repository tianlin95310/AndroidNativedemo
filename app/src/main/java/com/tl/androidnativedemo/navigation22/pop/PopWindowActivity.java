package com.tl.androidnativedemo.navigation22.pop;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.PopupWindowCompat;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2018/8/10.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class PopWindowActivity extends BaseActivity {

    @BindView(R.id.ll_index)
    LinearLayout llIndex;
    private PopupWindow popWindow;
    private TextView tv_pop_content;
    private View popView;
    private long lastTime;

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
                        break;
                    case MotionEvent.ACTION_MOVE:

                        if(System.currentTimeMillis() - lastTime > 200) {
                            int position = getPosition(event);
                            showPop(position, event);
                            lastTime = System.currentTimeMillis();
                        }
                        else {
//                            hidePop();
                        }
                        
                        break;
                    case MotionEvent.ACTION_UP:
                        hidePop();
                        break;

                }

                return true;
            }
        });
    }

    private void hidePop() {
        if(popWindow != null && popWindow.isShowing()) {
            popWindow.dismiss();
        }
    }

    private void showPop(int position, MotionEvent event) {
        if(position >= 0 && position <= llIndex.getChildCount() - 1) {

            if(popWindow == null) {
                popView = this.getLayoutInflater().inflate(R.layout.pop_window, null, false);
                tv_pop_content = popView.findViewById(R.id.tv_pop_content);
                tv_pop_content.setText(String.valueOf(position));
                // 用LayoutParams可以匹配屏幕大小
                popWindow = new PopupWindow(popView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            }
            else {
                tv_pop_content.setText(String.valueOf(position));
            }

            Log.d("my", "llIndex.getChildAt(position) = " + llIndex.getChildAt(position));
            PopupWindowCompat.showAsDropDown(popWindow, llIndex.getChildAt(position), 0, -llIndex.getMeasuredHeight() - popView.getHeight(), Gravity.BOTTOM);
        }
    }

    private int getPosition(MotionEvent event) {
        int itemWidth = llIndex.getMeasuredWidth() / llIndex.getChildCount();
        int position = (int) (event.getX() / itemWidth);

        return position;
    }
}
