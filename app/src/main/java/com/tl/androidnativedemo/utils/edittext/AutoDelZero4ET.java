package com.tl.androidnativedemo.utils.edittext;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by tianlin on 2017/3/20.
 * Tel : 15071485692
 * QQ : 953108373
 * Function : 自动去除前面的0，默认为空
 */

public class AutoDelZero4ET implements TextWatcher
{
    EditText editText;

    public AutoDelZero4ET(EditText editText)
    {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {
        // 如果你想在这里做一些事情，可以重写他

        Log.d("my", "beforeTextChanged text = " + charSequence);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        // 自动去0方式一，默认为空
        String text = s.toString();
        String new_text = text.replaceFirst("^[0]+", "");

        if (!text.equals(new_text))
        {
            editText.setText(new_text);
            return;
        }
        if(new_text.length() == 1)
            editText.setSelection(new_text.length());

    }

    @Override
    public void afterTextChanged(Editable editable)
    {
        Log.d("my", "afterTextChanged text = " + editable.toString());
        // 如果你想在这里获取输入框的值，可以重写他
    }
}
