package com.tl.androidnativedemo.navigation71.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by tianlin on 2017/10/19.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class TLRefreshRecyclerView extends RecyclerView
{

    private TLOnRefreshListener tlOnRefreshListener;

    private TLRefresh tlRefresh;

    public TLRefreshRecyclerView(Context context)
    {
        super(context);
    }

    public TLRefreshRecyclerView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public TLRefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public TLRefresh getTlRefresh()
    {
        return tlRefresh;
    }

    public void setTlRefresh(TLRefresh tlRefresh)
    {
        this.tlRefresh = tlRefresh;
    }

    public TLOnRefreshListener getTlOnRefreshListener()
    {
        return tlOnRefreshListener;
    }

    public void setTlOnRefreshListener(TLOnRefreshListener tlOnRefreshListener)
    {
        this.tlOnRefreshListener = tlOnRefreshListener;
    }

    public void finish()
    {
        if(tlRefresh != null)
        {
            tlRefresh.finish();
        }
    }
}
