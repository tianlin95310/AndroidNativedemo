package com.tl.androidnativedemo.utils.edittext;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by tianlin on 2017/3/20.
 * Tel : 15071485692
 * QQ : 953108373
 * Function : 自动去除前面的0，针对double，主要思想是分为两个部分
 */

public class AutoDelZero4ET4Double implements TextWatcher {
    EditText editText;

    public AutoDelZero4ET4Double(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // 如果你想在这里做一些事情，可以重写他

        Log.d("my", "beforeTextChanged text = " + charSequence);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String text = s.toString();

        if (text.contains(".")) {
            String[] nums = text.split("\\.");

            if (nums.length > 1) {
                // 整数部分
                String str_long = nums[0];
                // 小数部分
                String str_dot = "0." + nums[1];

                long long_text = 0;
                try {
                    long_text = Long.parseLong(str_long);
                } catch (Exception e) {
                }

                double double_text = 0.00;
                try {
                    double_text = Double.parseDouble(str_dot);
                } catch (Exception e) {
                }

                String new_text = String.valueOf(long_text) + String.valueOf(double_text).replaceFirst("0", "");

                if (!text.equals(new_text)) {
                    editText.setText(new_text);
                    editText.setSelection(editText.length());
                    return;
                }
            }
        } else {
            long i_text = 0;
            try {
                i_text = Long.parseLong(text);
            } catch (Exception e) {
            }
            if (!text.equals(String.valueOf(i_text))) {
                editText.setText(String.valueOf(i_text));
                return;
            }
        }

        if (editText.length() == 1)
            editText.setSelection(editText.length());

    }

    @Override
    public void afterTextChanged(Editable editable) {
        Log.d("my", "afterTextChanged text = " + editable.toString());
        // 如果你想在这里获取输入框的值，可以重写他
    }
}
