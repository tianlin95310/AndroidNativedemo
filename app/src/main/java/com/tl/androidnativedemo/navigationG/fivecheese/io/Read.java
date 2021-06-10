package com.tl.androidnativedemo.navigationG.fivecheese.io;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tl.androidnativedemo.navigationG.fivecheese.model.BaseResponse;
import com.tl.androidnativedemo.navigationG.fivecheese.model.Constant;
import com.tl.androidnativedemo.navigationG.fivecheese.model.DownInfoVo;
import com.tl.androidnativedemo.navigationG.fivecheese.model.UserResponse;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by tianlin on 2017/10/24.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class Read implements Runnable {
    Socket socket;

    Handler handler;

    public Read(Socket socket, Handler handler) {
        this.socket = socket;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            while (true && !socket.isClosed() && !socket.isInputShutdown()) {
                SystemClock.sleep(500);

                String json = null;

                while ((json = bufferedReader.readLine()) != null) {
                    Log.d("my", "json = " + json);
                    if (!TextUtils.isEmpty(json)) {
                        BaseResponse response = JSON.parseObject(json,
                                new TypeReference<BaseResponse>() {
                                }.getType());

                        if (response.what == Constant.DOWN) {
                            BaseResponse<DownInfoVo> baseResponse = JSON.parseObject(json, new TypeReference<BaseResponse<DownInfoVo>>() {
                            }.getType());
                            Message message = Message.obtain();
                            message.what = 2;
                            message.obj = baseResponse.content;
                            handler.sendMessage(message);
                        } else if (response.what == Constant.LOGIN) {
                            BaseResponse<UserResponse> baseResponse = JSON.parseObject(json, new TypeReference<BaseResponse<UserResponse>>() {
                            }.getType());

                            Message message = Message.obtain();
                            message.what = 1;
                            message.obj = baseResponse.content;
                            handler.sendMessage(message);

                        }

                    }

                }

            }

        } catch (IOException e) {
            Log.d("my", "socket 被关闭");
            e.printStackTrace();
        }
    }

}
