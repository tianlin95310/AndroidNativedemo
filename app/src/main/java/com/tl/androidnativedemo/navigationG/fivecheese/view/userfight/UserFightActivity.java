package com.tl.androidnativedemo.navigationG.fivecheese.view.userfight;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.navigationG.fivecheese.io.Read;
import com.tl.androidnativedemo.navigationG.fivecheese.io.Write;
import com.tl.androidnativedemo.navigationG.fivecheese.model.BaseRequest;
import com.tl.androidnativedemo.navigationG.fivecheese.model.Constant;
import com.tl.androidnativedemo.navigationG.fivecheese.model.DownInfoVo;
import com.tl.androidnativedemo.navigationG.fivecheese.model.UserResponse;
import com.tl.androidnativedemo.navigationG.fivecheese.model.UserVo;
import com.tl.androidnativedemo.navigationG.fivecheese.utils.ThreadManager;
import com.tl.androidnativedemo.navigationG.fivecheese.utils.Utils;
import com.tl.androidnativedemo.utils.toast.ToastUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;

public class UserFightActivity extends AppCompatActivity implements TLNetChessView.OnWhoChangeListener, View.OnClickListener {
    MyViewHolder myViewHolder;

    // 套接字
    Socket socket;

    // 对话框
    AlertDialog alertDialog;

    // 当前用户
    UserVo currentUser;

    // 当前房间内的所有用户
    List<UserVo> userGroup;

    // 处理服务器返回的信息
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Constant.DOWN) {
                DownInfoVo downInfoVo = (DownInfoVo) msg.obj;
                myViewHolder.tl_chess_view.onRemoteChessDown(downInfoVo);
            } else if (msg.what == Constant.LOGIN) {
                UserResponse userResponse = (UserResponse) msg.obj;

                initUserAndWhoFirst(userResponse);
            }

        }
    };

    private void initUserAndWhoFirst(UserResponse userResponse) {
        if (userResponse.whoFirst == -1) {
            ToastUtils.show(this, "当前只有一个用户，请等待其他用户接入");
        }

        List<UserVo> userVoList = userResponse.list;

        if (userVoList.size() == 1) {
            myViewHolder.tv_user1.setText(userVoList.get(0).username);
        } else if (userVoList.size() == 2) {
            ToastUtils.show(this, "房间里已经有两个用户了，可以开始游戏了");
            myViewHolder.tv_user1.setText(userVoList.get(0).username);
            myViewHolder.tv_user2.setText(userVoList.get(1).username);

            // 当前的所有用户
            myViewHolder.tl_chess_view.setUserGroup(userResponse.list);

            // 谁先下，当前是谁下
            myViewHolder.tl_chess_view.setWho(userResponse.whoFirst);

            myViewHolder.tl_chess_view.updateWho();

            // 当前用户在集合中的位置
            int position = userResponse.list.indexOf(currentUser);
            myViewHolder.tl_chess_view.setCurrentUserPosition(position);

            if (position == 0) {
                myViewHolder.tl_chess_view.setOtherUserPosition(1);
            } else if (position == 1) {
                myViewHolder.tl_chess_view.setOtherUserPosition(0);
            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_fight);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // 初始化控件
        initMyViewHolder();

        // begin
        ready();

    }

    private void ready() {
        createDialog("立即进入房间？", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null)
                    alertDialog.dismiss();
                // 开始联机
                beginNet();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null)
                    alertDialog.dismiss();
            }
        });
    }

    private void beginNet() {
        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket();
                    SocketAddress address = new InetSocketAddress(Constant.SERVICE_IP, Constant.PORT);
                    socket.connect(address, 5 * 1000);
                    if (socket != null) {
                        Log.d("my", "成功连接到服务器");
                        currentUser = Utils.makeAUser();
                        BaseRequest<UserVo> request = new BaseRequest<>();
                        request.what = Constant.LOGIN;
                        request.content = currentUser;
                        Write.write(socket, request);

                        ThreadManager.execute(new Read(socket, handler));
                    } else {
                        Log.d("my", "服务器无响应");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socket != null)
            try {
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    class MyViewHolder {
        public LinearLayout ll_reset;
        public LinearLayout ll_back;
        public TLNetChessView tl_chess_view;
        TextView tv_user1;
        TextView tv_user2;
    }

    private void initMyViewHolder() {
        myViewHolder = new MyViewHolder();
        myViewHolder.ll_reset = (LinearLayout) findViewById(R.id.ll_reset);
        myViewHolder.ll_back = (LinearLayout) findViewById(R.id.ll_back);
        myViewHolder.tv_user1 = (TextView) findViewById(R.id.tv_user1);
        myViewHolder.tv_user2 = (TextView) findViewById(R.id.tv_user2);
        myViewHolder.tl_chess_view = (TLNetChessView) findViewById(R.id.tl_chess_view);

        myViewHolder.ll_reset.setOnClickListener(this);
        myViewHolder.ll_back.setOnClickListener(this);
        myViewHolder.tl_chess_view.setOnWhoChangeListener(this);
    }

    @Override
    public void onLocalChessDown(final DownInfoVo down) {
        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    BaseRequest<DownInfoVo> request = new BaseRequest<>();
                    request.what = Constant.DOWN;
                    request.content = down;
                    Write.write(socket, request);
                } catch (IOException e) {
                    Log.d("my", "流读写异常");
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onWho(String who) {
        Log.d("my", who);
    }

    @Override
    public void onWin(String who) {
        createDialog("恭喜" + who + "获取胜利", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null)
                    alertDialog.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null)
                    alertDialog.dismiss();
            }
        });
    }

    private void createDialog(String msg, View.OnClickListener onOk, View.OnClickListener onCancel) {
        View view = getLayoutInflater().inflate(R.layout.pop_win, null);
        TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_msg.setText(msg);

        tv_confirm.setOnClickListener(onOk);
        tv_cancel.setOnClickListener(onCancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();

        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.width = (int) this.getResources().getDimension(R.dimen.pop_win_w);
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        alertDialog.getWindow().setAttributes(layoutParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_reset:
                break;
            case R.id.ll_back:
                break;
        }
    }
}
