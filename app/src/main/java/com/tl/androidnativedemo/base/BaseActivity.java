package com.tl.androidnativedemo.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.tl.androidnativedemo.R;


/**
 * Created by tianlin on 2017/3/31.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView
{

    AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void showProgressDialog(String msg)
    {
        if(alertDialog == null)
        {
            View view = View.inflate(this, R.layout.progress_dialog, null);
            alertDialog = new AlertDialog.Builder(this, 0)
                    .setCancelable(false)
                    .setView(view)
                    .setMessage(msg)
                    .create();
        }
        else
        {
            alertDialog.setMessage(msg);
        }

        alertDialog.show();

        android.view.WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.width = (int) getResources().getDimension(R.dimen.progress_dialog_w);
        layoutParams.height = (int) getResources().getDimension(R.dimen.progress_dialog_h);

        alertDialog.getWindow().setAttributes(layoutParams);
    }

    @Override
    public void showProgressDialog(int msg)
    {
        String str = getString(msg);
        showProgressDialog(str);
    }

    @Override
    public void hideProgressDialog()
    {
        if(alertDialog != null)
            alertDialog.dismiss();
    }

    @Override
    public void response(Context context, String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void response(Context context, int msg)
    {
        Toast.makeText(context, context.getString(msg), Toast.LENGTH_SHORT).show();
    }
}
