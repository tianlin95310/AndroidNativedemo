package com.tl.androidnativedemo.navigationG.jinzita;

/**
 * Created by tianlin on 2018/11/26.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class PukeInfo{
    public int value;
    public int color;
    public String id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PukeInfo)) return false;

        PukeInfo pukeInfo = (PukeInfo) o;

        return id.equals(pukeInfo.id);
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "PukeInfo{" +
                "id = " + id + '\n' +
                '}';
    }

}
