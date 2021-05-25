package com.tl.androidnativedemo.navigation61.menulistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.tl.androidnativedemo.base.BaseActivity;


// 经测试无效
public class MenuListenerActivity extends BaseActivity {

    public void initView() {

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注意！！！在setContentView之前添加,未添加的话home键监听无效，设置窗体属性
        this.getWindow().setFlags(0x80000000, 0x80000000);
        //创建广播
        InnerRecevier innerReceiver = new InnerRecevier();
        //动态注册广播
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        //启动广播
        registerReceiver(innerReceiver, intentFilter);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;//拦截事件传递,从而屏蔽back键。
        }
        if (KeyEvent.KEYCODE_HOME == keyCode) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class InnerRecevier extends BroadcastReceiver {

        final String SYSTEM_DIALOG_REASON_KEY = "reason";

        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {

                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);

                if (reason != null) {

                    if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                        Log.d("my", "onReceive = " + reason);
                    } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                        Log.d("my", "onReceive = " + reason);
                    }
                }
            }
        }
    }
}
