package com.tl.androidnativedemo.navigationG.fivecheese.io;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tl.androidnativedemo.navigationG.fivecheese.model.BaseRequest;
import com.tl.androidnativedemo.navigationG.fivecheese.model.UserVo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by tianlin on 2017/10/24.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class Write
{
    public static void write(Socket socket, BaseRequest request) throws IOException
    {
        if(socket == null)
            return;
        String json = JSON.toJSONString(request);

        BaseRequest<UserVo> baseRequest = JSON.parseObject(json.replace("\n", ""), new TypeReference<BaseRequest<UserVo>>(){}.getType());


        OutputStream outputStream = socket.getOutputStream();
        // 添加结束标记
        outputStream.write((json + "\n").getBytes());
        outputStream.flush();

    }
}
