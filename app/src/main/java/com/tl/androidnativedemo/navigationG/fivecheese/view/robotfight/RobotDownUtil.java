package com.tl.androidnativedemo.navigationG.fivecheese.view.robotfight;

import com.tl.androidnativedemo.navigationG.fivecheese.model.DownInfoVo;
import com.tl.androidnativedemo.navigationG.fivecheese.utils.CheckWinnerUtils;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by tianlin on 2017/11/10.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class RobotDownUtil
{

    static TreeSet<DownWithScore> downWithScores = new TreeSet<>();

    public static DownInfoVo whereToDown(int[][] chess, int who)
    {
        DownWithScore downWithScore = new DownWithScore();
        for(int i = 0; i < chess.length; i++)
        {
            for(int j = 0; j < chess[i].length; j++)
            {
                // 已经下过的不用计算了
                if(chess[i][j] != -1)
                    continue;

                int score = CheckWinnerUtils.checkWinner(chess, who, i, j);

                downWithScore.who = who;
                downWithScore.downX = i;
                downWithScore.downY = j;
                downWithScore.score = score;
                downWithScores.add(downWithScore);
            }
        }

        DownInfoVo downInfoVo = null;

        Iterator<DownWithScore> iterator = downWithScores.iterator();
        while(iterator.hasNext())
        {
            downInfoVo = iterator.next();
            if(chess[downInfoVo.downX][downInfoVo.downY] == -1)
                break;
        }

        downWithScores.clear();
        return downInfoVo;
    }
}
