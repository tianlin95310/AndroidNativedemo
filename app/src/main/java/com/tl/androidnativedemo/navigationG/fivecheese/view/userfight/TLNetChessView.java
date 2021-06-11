package com.tl.androidnativedemo.navigationG.fivecheese.view.userfight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.tl.androidnativedemo.navigationG.fivecheese.model.DownInfoVo;
import com.tl.androidnativedemo.navigationG.fivecheese.model.UserVo;
import com.tl.androidnativedemo.navigationG.fivecheese.utils.CheckWinnerUtils;
import com.tl.androidnativedemo.utils.toast.ToastUtils;

import java.util.List;

/**
 * Created by tianlin on 2017/9/28.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class TLNetChessView extends View {

    // 棋盘上的辅助点大小，值越大点越小
    private static final int DOT_SIZE = 8;
    private static final int CHESS_SIZE = 3;
    // 棋盘的高宽
    int cheeseW;
    int cheeseH;
    // 线的条数
    private static final int LINE_COUNT = 15;
    // 每两条线之间的距离
    int itemLength = 0;
    // 线的边长
    int recLength = 0;
    // 画笔
    Paint paint;
    // 表示棋盘的数组
    int chess[][] = new int[LINE_COUNT][LINE_COUNT];
    // 下棋方
    int who = -1;
    UserVo my;

    // 当前用户的位置，只有who等于当前用户的位置，他才可以下棋
    int currentUserPosition;

    // 其他用户的位置
    int otherUserPosition;

    // 当前房间内的所有用户
    List<UserVo> userGroup;

    private OnWhoChangeListener onWhoChangeListener;

    Context context;

    Paint tipPaint;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        cheeseW = getMeasuredWidth();
        cheeseH = getMeasuredHeight();

        recLength = Math.min(cheeseW, cheeseH);

        // 去掉两端的空隙
        itemLength = recLength / (LINE_COUNT + 1);

        setMeasuredDimension(recLength, recLength);
    }

    private void initView(Context context) {

        this.context = context;
        paint = new Paint();
        tipPaint = new Paint();
        paint.setAntiAlias(true);
        tipPaint.setAntiAlias(true);

        // 初始化棋盘
        for (int i = 0; i < chess.length; i++) {
            for (int j = 0; j < chess[i].length; j++) {
                chess[i][j] = -1;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画格子
        paint.setColor(Color.BLACK);
        for (int i = 1; i <= LINE_COUNT; i++) {
            canvas.drawLine(itemLength, itemLength * i, LINE_COUNT * itemLength, i * itemLength, paint);
        }
        for (int i = 1; i <= LINE_COUNT; i++) {
            canvas.drawLine(itemLength * i, itemLength, i * itemLength, LINE_COUNT * itemLength, paint);
        }

        // 画辅助中心点
        canvas.drawCircle((LINE_COUNT + 1) / 2 * itemLength, (LINE_COUNT + 1) / 2 * itemLength, itemLength / DOT_SIZE, paint);

        // 画棋子
        for (int i = 0; i < chess.length; i++) {
            for (int j = 0; j < chess[i].length; j++) {
                // 每个人都是黑子
//                if (chess[i][j] == currentUserPosition)
//                {
//                    paint.setColor(Color.BLACK);
//                    canvas.drawCircle((i + 1) * itemLength, (j + 1) * itemLength, itemLength / CHESS_SIZE, paint);
//
//                } else if (chess[i][j] == otherUserPosition)
//                {
//                    paint.setColor(Color.WHITE);
//                    canvas.drawCircle((i + 1) * itemLength, (j + 1) * itemLength, itemLength / CHESS_SIZE, paint);
//                }
                if (chess[i][j] == 0) {
                    paint.setColor(Color.BLACK);
                    canvas.drawCircle((i + 1) * itemLength, (j + 1) * itemLength, itemLength / CHESS_SIZE, paint);

                } else if (chess[i][j] == 1) {
                    paint.setColor(Color.WHITE);
                    canvas.drawCircle((i + 1) * itemLength, (j + 1) * itemLength, itemLength / CHESS_SIZE, paint);
                }
            }
        }
        // 绘制提示
        updateInfo(canvas);
    }

    void updateInfo(Canvas canvas) {
        String str = "连接服务器中。。。";
        String room = "当前房间内，【】";
        if (userGroup == null) {
            str = "连接服务器中...";
            room = "当前房间内，【】";
        } else if (userGroup.size() == 1) {
            String names = userGroup.get(0).username;
            str = "我是" + userGroup.get(currentUserPosition).username + "，我已经登录";
            room = "当前房间内:【 " + names + " 】";
        } else if (userGroup.size() == 2){
            String names = userGroup.get(0).username + "， " + userGroup.get(1).username;;
            str = "我是" + userGroup.get(currentUserPosition).username + "，" +
                    "我是" + (currentUserPosition == 0 ? "黑棋" : "白旗");
            room = "当前房间内:【 " + names + " 】" +
                    "，现在轮到" + (userGroup.get(who).username.equals(my.username) ? "我" : userGroup.get(who).username) + "下子";
            if (currentUserPosition == 0) {
                tipPaint.setColor(Color.BLACK);
            } else if (currentUserPosition == 1) {
                tipPaint.setColor(Color.WHITE);
            }
            canvas.drawCircle(itemLength / 2, itemLength / 2, itemLength / 4, tipPaint);
        }
        Rect textBound = new Rect();
        tipPaint.setTextSize(itemLength * 0.4f);
        tipPaint.getTextBounds(str, 0, str.length(), textBound);
        tipPaint.setColor(Color.WHITE);
        canvas.drawText(str, itemLength, itemLength - textBound.height(), tipPaint);
        canvas.drawText(room, itemLength + textBound.width() + itemLength, itemLength - textBound.height(), tipPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                // 当前用户如果没有初始化，或者当前用户没有两个人，不开始下子
                if (userGroup == null || userGroup.size() != 2) {
                    ToastUtils.show(context, "请等待另一个用户入场");
                    return true;
                }
                // 对于网络版，当前下子方如果不是自己，不允许下子
                if (who != currentUserPosition) {
                    ToastUtils.show(context, "还没有轮到我下子");
                    return true;
                }

                // 实际点坐标
                float x = event.getX();
                float y = event.getY();

                // 近似数组下标
                float pointX = x * 1.0f / itemLength - 1;
                float pointY = y * 1.0f / itemLength - 1;

                // 排除无效点
                if (pointX + 0.5 < 0 || pointY + 0.5 < 0)
                    return true;

                // 实际最近数组下标
                int retX = (int) (pointX + 0.5);
                int retY = (int) (pointY + 0.5);

                Log.d("my", "retX = " + retX + ", retY = " + retY);

                // 排除无效点
                if (!(retX <= chess.length - 1 && retX >= 0 && retY <= chess.length - 1 && retY >= 0))
                    return true;

                // 排除无效点
                if (chess[retX][retY] != -1) {
                    ToastUtils.show(context, "该处已经落子，请不要重复");
                    return true;
                }

                // 给当前用户下子
                chess[retX][retY] = who;

                // 先将下子信息传递给另外一个用户，服务器收到请求信息向每个用户重新发送信息
                // 包括当前下子方自己，所以当前房间内的所有用户都会调用onRemoteChessDown方法
                DownInfoVo down = new DownInfoVo(retX, retY, who);
                if (onWhoChangeListener != null) {
                    onWhoChangeListener.onLocalChessDown(down);
                }

                // 如下的方法在onRemoteChessDown会继续调用一次，为了防止网络因素，我们先刷新本地数据
                // 在onRemoteChessDown我们控制一下让当前下子方不刷新，但下子方要刷新
                invalidate();
                // 判断输赢
                int result = CheckWinnerUtils.checkWinner(chess, who, retX, retY);
                if (result == 6) {
                    if (onWhoChangeListener != null)
                        onWhoChangeListener.onWin(userGroup.get(who).username);
                }
                break;
        }
        return true;
    }

    public void changeWho() {
        who = (who + 1) % 2;
        Log.d("my", "轮到" + userGroup.get(who).username + "下子");
        invalidate();
    }

    public void finish() {

        // 赢了之后就不能在下了
        for (int i = 0; i < chess.length; i++) {
            for (int j = 0; j < chess[i].length; j++) {
                if (chess[i][j] == 0)
                    chess[i][j] = -1;
            }
        }
    }

    // 另一方下子,已通过网络返回，消息分别发送给两方
    public void onRemoteChessDown(DownInfoVo downInfoVo) {
        // 表明当前棋子信息，是我自己下的，他的刷新和判断在onTouchEvent已经做了
        if (downInfoVo.who == currentUserPosition) {

        }
        // 表示是里一个用户
        else {
            // 更新另外一个人的下子信息
            chess[downInfoVo.downX][downInfoVo.downY] = downInfoVo.who;

            // 刷新棋局，棋子是另一个人下的
            invalidate();

            // 判断输赢
            int result = CheckWinnerUtils.checkWinner(chess, who, downInfoVo.downX, downInfoVo.downY);
            if (result == 6) {
                if (onWhoChangeListener != null)
                    onWhoChangeListener.onWin(userGroup.get(who).username);
            }
        }

        // 转化下子方，在双方的本地进行下子方的转换
        changeWho();


    }

    public interface OnWhoChangeListener {
        // 谁赢了
        void onWin(String who);

        // 本地下子
        void onLocalChessDown(DownInfoVo down);
    }

    public TLNetChessView(Context context) {
        super(context);
        initView(context);
    }

    public TLNetChessView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TLNetChessView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TLNetChessView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public OnWhoChangeListener getOnWhoChangeListener() {
        return onWhoChangeListener;
    }

    public void setOnWhoChangeListener(OnWhoChangeListener onWhoChangeListener) {
        this.onWhoChangeListener = onWhoChangeListener;
    }

    public List<UserVo> getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(List<UserVo> userGroup) {
        this.userGroup = userGroup;
    }

    public int getWho() {
        return who;
    }

    public void setWho(int who) {
        this.who = who;
        invalidate();
    }

    public int getCurrentUserPosition() {
        return currentUserPosition;
    }

    public void setCurrentUserPosition(int currentUserPosition) {
        this.currentUserPosition = currentUserPosition;
    }

    public int getOtherUserPosition() {
        return otherUserPosition;
    }

    public UserVo getMy() {
        return my;
    }

    public void setMy(UserVo my) {
        this.my = my;
        invalidate();
    }

    public void setOtherUserPosition(int otherUserPosition) {
        this.otherUserPosition = otherUserPosition;
    }
}
