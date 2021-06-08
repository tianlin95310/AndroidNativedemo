package com.tl.androidnativedemo.navigationG.fivecheese.view.robotfight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
import com.tl.androidnativedemo.utils.display.DensityUtils;
import com.tl.androidnativedemo.utils.toast.ToastUtils;

import java.util.List;

/**
 * Created by tianlin on 2017/9/28.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class TLLocalChessView extends View
{

    // 棋盘上的辅助点大小，值越大点越小
    private static final int DOT_SIZE = 8;
    private static final int CHESS_SIZE = 3;
    // 棋盘的高宽
    int cheeseW;
    int cheeseH;
    // 线的条数
    public static final int LINE_COUNT = 15;
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

    // 当前用户的位置，只有who等于当前用户的位置，他才可以下棋
    int currentUserPosition;

    // 其他用户的位置
    int otherUserPosition;

    // 当前房间内的所有用户
    List<UserVo> userGroup;

    private OnWhoChangeListener onWhoChangeListener;

    Context context;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        cheeseW = getMeasuredWidth();
        cheeseH = getMeasuredHeight();

        recLength = Math.min(cheeseW, cheeseH);

        // 去掉两端的空隙
        itemLength = recLength / (LINE_COUNT + 1);

        setMeasuredDimension(recLength, recLength);
    }

    private void initView(Context context)
    {

        this.context = context;
        paint = new Paint();
        paint.setAntiAlias(true);

        // 初始化棋盘
        for (int i = 0; i < chess.length; i++)
        {
            for (int j = 0; j < chess[i].length; j++)
            {
                chess[i][j] = -1;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        // 画格子
        paint.setColor(Color.BLACK);
        for (int i = 1; i <= LINE_COUNT; i++)
        {
            canvas.drawLine(itemLength, itemLength * i, LINE_COUNT * itemLength, i * itemLength, paint);
        }
        for (int i = 1; i <= LINE_COUNT; i++)
        {
            canvas.drawLine(itemLength * i, itemLength, i * itemLength, LINE_COUNT * itemLength, paint);
        }

        // 画辅助中心点
        canvas.drawCircle((LINE_COUNT + 1) / 2 * itemLength, (LINE_COUNT + 1) / 2 * itemLength, itemLength / DOT_SIZE, paint);

        // 画棋子
        for (int i = 0; i < chess.length; i++)
        {
            for (int j = 0; j < chess[i].length; j++)
            {
                if (chess[i][j] == currentUserPosition)
                {
                    paint.setColor(Color.BLACK);
                    canvas.drawCircle((i + 1) * itemLength, (j + 1) * itemLength, itemLength / CHESS_SIZE, paint);

                } else if (chess[i][j] == otherUserPosition)
                {
                    paint.setColor(Color.WHITE);
                    canvas.drawCircle((i + 1) * itemLength, (j + 1) * itemLength, itemLength / CHESS_SIZE, paint);
                }

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_UP:

                // 对于电脑版，电脑还没有下完时，我不能下子
                if (who != currentUserPosition)
                {
                    ToastUtils.show(context, "请等待电脑计算");
                    return true;
                }

                // 实际点坐标
                float x = event.getX();
                float y = event.getY();
                Log.d("my", "x = " + x + ", y = " + y);

                // 近似数组下标
                float pointX = x * 1.0f / itemLength - 1;
                float pointY = y * 1.0f / itemLength - 1;

                Log.d("my", "pointX = " + pointX + ", pointY = " + pointY);

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
                if (chess[retX][retY] != -1)
                {
                    ToastUtils.show(context, "该处已经落子，请不要重复");
                    return true;
                }

                // 给当前用户下子，之后当前用户才会走到这里，电脑不走这里
                chess[retX][retY] = who;

                // 刷新本地
                invalidate();

                // 先判断输赢，赢了就没必要下子
                int result = CheckWinnerUtils.checkWinner(chess, who, retX, retY);
                if (result == 6)
                {
                    if (onWhoChangeListener != null)
                        onWhoChangeListener.onWin(userGroup.get(who).username);
                }
                else
                {
                    // 通知电脑下子
                    if (onWhoChangeListener != null)
                    {
                        onWhoChangeListener.onMenChessDown(chess);
                    }
                }

                break;
        }
        return true;
    }

    public void changeWho()
    {
        who = (who + 1) % 2;

        // 更新页面
        updateWho();
    }

    public void updateWho()
    {
        if (onWhoChangeListener != null && userGroup != null)
            onWhoChangeListener.onWho(userGroup.get(who).username);
    }

    public void finish()
    {
        // 赢了之后就不能在下了
        for (int i = 0; i < chess.length; i++)
        {
            for (int j = 0; j < chess[i].length; j++)
            {
                if (chess[i][j] == 0)
                    chess[i][j] = -1;
            }
        }
    }

    // 电脑
    public void onRobotChessDown(DownInfoVo downInfoVo)
    {

        Log.d("my", "down = " + downInfoVo.toString());

        // 当前谁下子
        who = downInfoVo.who;

        // 电脑下子
        chess[downInfoVo.downX][downInfoVo.downY] = who;

        invalidate();

        // 更新下子方
        changeWho();

    }

    public interface OnWhoChangeListener
    {
        // 谁赢了
        void onWin(String who);

        // 本地下子
        void onMenChessDown(int[][] chess);

        // 下子方发生变化
        void onWho(String who);
    }

    public TLLocalChessView(Context context)
    {
        super(context);
        initView(context);
    }

    public TLLocalChessView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        initView(context);
    }

    public TLLocalChessView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TLLocalChessView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public OnWhoChangeListener getOnWhoChangeListener()
    {
        return onWhoChangeListener;
    }

    public void setOnWhoChangeListener(OnWhoChangeListener onWhoChangeListener)
    {
        this.onWhoChangeListener = onWhoChangeListener;
    }

    public List<UserVo> getUserGroup()
    {
        return userGroup;
    }

    public void setUserGroup(List<UserVo> userGroup)
    {
        this.userGroup = userGroup;
    }

    public int getWho()
    {
        return who;
    }

    public void setWho(int who)
    {
        this.who = who;
    }

    public int getCurrentUserPosition()
    {
        return currentUserPosition;
    }

    public void setCurrentUserPosition(int currentUserPosition)
    {
        this.currentUserPosition = currentUserPosition;
    }

    public int getOtherUserPosition()
    {
        return otherUserPosition;
    }

    public void setOtherUserPosition(int otherUserPosition)
    {
        this.otherUserPosition = otherUserPosition;
    }
}
