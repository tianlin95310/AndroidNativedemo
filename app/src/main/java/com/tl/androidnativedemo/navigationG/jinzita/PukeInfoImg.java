package com.tl.androidnativedemo.navigationG.jinzita;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.tl.androidnativedemo.utils.anim.AnimUtils;


/**
 * Created by tianlin on 2018/11/26.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class PukeInfoImg extends AppCompatImageView {
    public int value;
    public int color;
    public String pid;
    public int position;
    public int i;
    public int j;
    public boolean isChoose = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PukeInfoImg)) return false;

        PukeInfoImg pukeInfo = (PukeInfoImg) o;

        return pid.equals(pukeInfo.pid);
    }

    public void go_to(float x, float y, long delay) {
        AnimUtils.go_to(this, x, y, delay, 1000);
    }
    public void choose () {
        AnimUtils.scale(this, 1.3f, 1.3f, null, 0, 300);
    }
    public void unChoose() {
        AnimUtils.scale(this, 1f, 1f, null, 0,300);
    }
    @Override
    public int hashCode() {
        return pid.hashCode();
    }

    @Override
    public String toString() {
        return "PukeInfo{" +
                "id = " + pid + '\n' +
                ", x = " + getX() + "\n" +
                ", y = " + getY() + "\n" +
                '}';
    }

    public PukeInfoImg(Context context) {
        super(context);
    }

    public PukeInfoImg(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PukeInfoImg(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
