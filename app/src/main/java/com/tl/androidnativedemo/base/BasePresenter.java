package com.tl.androidnativedemo.base;

/**
 * Created by tianlin on 2017/3/31.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public abstract class BasePresenter<T extends IBaseView>
{
    /**
     * 核心视图引用
     */
    protected T view;

    /**
     * 绑定视图引用
     *
     * @param view
     */
    public void attachView(T view)
    {
        this.view = view;
    }

    /**
     * 释放视图引用
     */
    public void detachView()
    {
        this.view = null;
    }

    /**
     * 初始化数据
     */
    public abstract void initData();
}
