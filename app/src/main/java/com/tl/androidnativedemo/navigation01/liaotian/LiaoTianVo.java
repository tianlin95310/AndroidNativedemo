package com.tl.androidnativedemo.navigation01.liaotian;

/**
 * Created by tianlin on 2017/3/9.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class LiaoTianVo
{
    private String who;

    private String text;

    public LiaoTianVo()
    {
    }

    public LiaoTianVo(String who, String text)
    {

        this.who = who;
        this.text = text;
    }

    public String getWho()
    {
        return who;
    }

    public void setWho(String who)
    {
        this.who = who;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
