package com.tl.androidnativedemo.base;

import android.content.Context;

/**
 * Created by tianlin on 2017/3/31.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public interface IBaseView
{
    /**
     * 显示加载框
     */
    void showProgressDialog(String msg);

    /**
     * 显示加载框
     */
    void showProgressDialog(int msg);

    /**
     * 隐藏加载框
     */
    void hideProgressDialog();

    /**
     * 给出当前视图的提示
     *
     * @param msg 提示的内容
     */
    void response(Context context, String msg);

    void response(Context context, int msg);

    /**
     * 初始化视图
     */
    void initView();

}
