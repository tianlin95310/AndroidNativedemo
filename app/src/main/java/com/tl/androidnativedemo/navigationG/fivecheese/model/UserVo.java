package com.tl.androidnativedemo.navigationG.fivecheese.model;

/**
 * Created by tianlin on 2017/10/27.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class UserVo
{
    public String username;

    public String gender;

    public String id;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserVo userVo = (UserVo) o;

        return id.equals(userVo.id);
    }

    @Override
    public int hashCode()
    {
        return id.hashCode();
    }
}
