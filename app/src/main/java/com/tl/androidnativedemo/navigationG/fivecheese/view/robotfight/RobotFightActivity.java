package com.tl.androidnativedemo.navigationG.fivecheese.view.robotfight;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.navigationG.fivecheese.model.DownInfoVo;
import com.tl.androidnativedemo.navigationG.fivecheese.model.UserVo;
import com.tl.androidnativedemo.navigationG.fivecheese.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianlin on 2017/11/5.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class RobotFightActivity extends AppCompatActivity implements TLLocalChessView.OnWhoChangeListener
{

    class ViewHolder
    {
        TextView tv_current_who;

        TextView tv_user1;
        TextView tv_user2;

        TLLocalChessView tl_chess_view;
    }

    ViewHolder viewHolder;

    AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_fight);

        initViewHolder();

    }

    private void initViewHolder()
    {
        viewHolder = new ViewHolder();

        viewHolder.tv_current_who = (TextView) findViewById(R.id.tv_current_who);
        viewHolder.tv_user1 = (TextView) findViewById(R.id.tv_user1);
        viewHolder.tv_user2 = (TextView) findViewById(R.id.tv_user2);
        viewHolder.tl_chess_view = (TLLocalChessView) findViewById(R.id.tl_chess_view);

        viewHolder.tl_chess_view.setOnWhoChangeListener(this);

        List<UserVo> userVos = new ArrayList<>();
        UserVo userVo = Utils.makeAUser();
        userVos.add(userVo);
        UserVo robot = Utils.makeAUser();
        userVos.add(robot);

        viewHolder.tv_user1.setText("我   " + userVo.username);
        viewHolder.tv_user2.setText("电脑  " + robot.username);

        viewHolder.tl_chess_view.setUserGroup(userVos);
        viewHolder.tl_chess_view.setCurrentUserPosition(0);
        viewHolder.tl_chess_view.setOtherUserPosition(1);
        viewHolder.tl_chess_view.setWho(0);
        viewHolder.tl_chess_view.updateWho();
    }

    @Override
    public void onWin(String who)
    {
        createDialog("恭喜" + who + "获取胜利", new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(alertDialog != null)
                    alertDialog.dismiss();
            }
        }, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(alertDialog != null)
                    alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onMenChessDown(int [][] chess)
    {
        // 当前是电脑下子
        DownInfoVo down = RobotDownUtil.whereToDown(chess, viewHolder.tl_chess_view.getOtherUserPosition());

        viewHolder.tl_chess_view.onRobotChessDown(down);
    }

    @Override
    public void onWho(String who)
    {
        viewHolder.tv_current_who.setText("当前谁下   " + who);
    }

    private void createDialog(String msg, View.OnClickListener onOk, View.OnClickListener onCancel)
    {
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
}
