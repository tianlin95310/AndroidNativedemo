package com.tl.androidnativedemo.navigationG.fivecheese.view.robotfight;


import androidx.annotation.NonNull;

import com.tl.androidnativedemo.navigationG.fivecheese.model.DownInfoVo;

/**
 * Created by tianlin on 2017/11/10.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class DownWithScore extends DownInfoVo implements Comparable<DownWithScore>
{
    @Override
    public int compareTo(@NonNull DownWithScore o)
    {
        if(o == null)
            return 1;

        return score - o.score;
    }

    public int score;
}
