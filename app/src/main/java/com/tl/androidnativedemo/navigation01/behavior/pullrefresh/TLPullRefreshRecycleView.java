package com.tl.androidnativedemo.navigation01.behavior.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by tianlin on 2017/10/18.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class TLPullRefreshRecycleView extends RecyclerView
{
    PullRefreshBehavior pullRefreshBehavior;

    public TLPullRefreshRecycleView(Context context)
    {
        super(context);
    }

    public TLPullRefreshRecycleView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public TLPullRefreshRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    private OnRefreshListener onRefreshListener;

    public interface OnRefreshListener
    {
        void onRefresh();
    }

    public OnRefreshListener getOnRefreshListener()
    {
        return onRefreshListener;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener)
    {
        this.onRefreshListener = onRefreshListener;
    }

    public void close()
    {
        if(pullRefreshBehavior != null)
            pullRefreshBehavior.close();

    }

    public PullRefreshBehavior getPullRefreshBehavior()
    {
        return pullRefreshBehavior;
    }

    public void setPullRefreshBehavior(PullRefreshBehavior pullRefreshBehavior)
    {
        this.pullRefreshBehavior = pullRefreshBehavior;
    }
}
