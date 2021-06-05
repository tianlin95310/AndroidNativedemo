package com.tl.androidnativedemo.navigationG.fivecheese.model;

/**
 * Created by tianlin on 2017/10/21.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class DownInfoVo
{
    public int downX;

    public int downY;

    public int who;

    public DownInfoVo(int downX, int downY, int who)
    {
        this.downX = downX;
        this.downY = downY;
        this.who = who;
    }

    public int getDownX()
    {
        return downX;
    }

    public void setDownX(int downX)
    {
        this.downX = downX;
    }

    public int getDownY()
    {
        return downY;
    }

    public void setDownY(int downY)
    {
        this.downY = downY;
    }

    public int isWho()
    {
        return who;
    }

    public DownInfoVo()
    {
    }

    public void setWho(int who)
    {
        this.who = who;
    }

    @Override
    public String toString()
    {
        return "DownInfoVo{" +
                "downX=" + downX +
                ", downY=" + downY +
                ", who=" + who +
                '}';
    }
}
