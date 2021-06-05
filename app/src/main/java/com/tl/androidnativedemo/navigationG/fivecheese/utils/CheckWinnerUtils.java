package com.tl.androidnativedemo.navigationG.fivecheese.utils;

/**
 * Created by tianlin on 2017/11/10.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class CheckWinnerUtils
{
    /**
     *
     * @param a 棋盘信息
     * @param who 当前是谁下的
     * @param x 当前点的坐标
     * @param y
     * @return 当前下的点的分数
     */
    public static int checkWinner(int a[][], int who, int x, int y)
    {

        // 竖直方向
        int i = x;
        int j = y;
        int score = 0;
        while(a[i][j] == who)
        {
            score++;

            if(score == 6)
                return score;

            j++;

            if(j > a.length - 1)
                break;

        }
        j = y;
        while (a[i][j] == who)
        {
            score++;

            if(score == 6)
                return score;

            j--;
            if(j < 0)
                break;
        }

        // 水平方向
        i = x;
        j = y;
        score = 0;
        while (a[i][j] == who)
        {
            score++;

            if (score == 6)
                return score;

            i++;

            if(i > a.length - 1)
                break;
        }
        i = x;
        while (a[i][j] == who)
        {
            score++;

            if (score == 6)
                return score;

            i--;

            if(i < 0)
                break;
        }

        // 主对角线上
        i = x;
        j = y;
        score = 0;
        while (a[i][j] == who)
        {
            score++;

            if (score == 6)
                return score;

            i++;
            j++;

            if(i > a.length - 1 || j > a.length - 1)
                break;

        }

        i = x;
        j = y;
        while (a[i][j] == who)
        {
            score++;

            if (score == 6)
                return score;

            i--;
            j--;

            if(i < 0 || j < 0)
                break;

        }

        // 副对角线上
        i = x;
        j = y;
        score = 0;

        while (a[i][j] == who)
        {
            score++;

            if (score == 6)
                return score;

            i++;
            j--;
            if(i > a.length - 1 || j < 0)
                break;
        }
        i = x;
        j = y;
        while (a[i][j] == who)
        {
            score++;

            if (score == 6)
                return score;

            i--;
            j++;
            if(i < 0 || j > a.length - 1)
                break;
        }

        return score;

    }
}
