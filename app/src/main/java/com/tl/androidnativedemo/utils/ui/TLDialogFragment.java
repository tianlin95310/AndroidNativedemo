package com.tl.androidnativedemo.utils.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.tl.androidnativedemo.utils.nullcheck.NullCheckUtils;


/**
 * Created by tianlin on 2017/3/15.
 * Tel : 15071485692
 * QQ : 953108373
 * Function : v4 DialogFragment对话框
 */

public class TLDialogFragment extends DialogFragment
{

    /**
     * ok键的按钮事件
     */
    private DialogInterface.OnClickListener okListener;

    /**
     * 取消键的按钮事件
     */
    private DialogInterface.OnClickListener noListener;

    /**
     * 对话框的标题
     */
    private String title;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 提示的视图，view与msg二选一
     */
    private View view;

    /**
     * 确认键的文本
     */
    private String okText = "确认";

    /**
     * 取消键的文本
     */
    private String noText = "取消";

    /**
     * 资源文件的id，默认值为0表示使用的是activity的Theme
     */
    private int styleId = 0;

    public TLDialogFragment setView(View view)
    {
        this.view = view;
        return this;
    }

    public static TLDialogFragment newInstance()
    {
        TLDialogFragment fragment = new TLDialogFragment();
        return fragment;
    }

    public TLDialogFragment setStyleId(int styleId)
    {
        this.styleId = styleId;
        return this;
    }

    public TLDialogFragment setOkText(String okText)
    {
        this.okText = okText;
        return this;
    }

    public TLDialogFragment setNoText(String noText)
    {
        this.noText = noText;
        return this;
    }

    public TLDialogFragment setOkListener(DialogInterface.OnClickListener okListener)
    {
        this.okListener = okListener;
        return this;
    }

    public TLDialogFragment setNoListener(DialogInterface.OnClickListener noListener)
    {
        this.noListener = noListener;
        return this;
    }

    public TLDialogFragment setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public TLDialogFragment setMsg(String msg)
    {
        this.msg = msg;
        return this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), styleId)
                .setTitle(title)
                .setPositiveButton(okText, okListener)
                .setNegativeButton(noText, noListener);

        if (!NullCheckUtils.isEmpty(msg))
        {
            builder.setMessage(msg);
        }
        else if (view != null)
        {
            builder.setView(view);
        }
        return builder.create();
    }

}
