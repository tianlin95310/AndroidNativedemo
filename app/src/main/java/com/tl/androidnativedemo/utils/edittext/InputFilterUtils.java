package com.tl.androidnativedemo.utils.edittext;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by tianlin on 2017/3/20.
 * Tel : 15071485692
 * QQ : 953108373
 * Function : InputFilter只能决定当前输入的字符能不能输入，最好不要用它来进行格式化之前已经输入的字符
 */

public class InputFilterUtils {

    // 获取输入框的理论值
    public static InputFilter getLiLunValue() {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String before = dest.toString();
                String lilun = null;
                // 如果是空的说明是删除
                if (TextUtils.isEmpty(source.toString())) {
                    if (before.length() >= 2) {
                        if (dstart > 0 && dstart < before.length() - 1) {
                            lilun = before.substring(0, dstart) + before.substring(dstart + 1, before.length());
                        } else if (dstart == 0) {
                            lilun = before.substring(1, before.length());
                        } else if (dstart == before.length() - 1) {
                            lilun = before.substring(0, before.length() - 1);
                        }
                    } else {
                        lilun = "";
                    }
                } else {
                    lilun = before.substring(0, dstart) +
                            source.toString() +
                            before.substring(dstart, before.length());
                }

                Log.e("my", "filter lilun = " + lilun);

                return null;
            }
        };
    }

    /**
     * 禁止0开头，以及在顶端输入0
     *
     * @return
     */
    public static InputFilter banZeroBegin() {
        return new InputFilter() {
            /**
             * @param source 当前输入的字符，如果是setText可能他不只是一个字符，但位置是没有问题的，删除的时候他为空
             * @param start
             * @param end
             * @param dest 之前显示的字符
             * @param dstart 当前输入的位置，从0开始
             * @param dend
             * @return
             */
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // 首位不能输入0
                if (dstart == 0 && "0".equals(source.toString())) {
                    Log.d("my", "首位不能输入0");
                    return "";
                }
                return source;
            }
        };
    }

    /**
     * 设置输入长度
     *
     * @param length
     * @return
     */
    public static InputFilter setLength(int length) {
        return new InputFilter.LengthFilter(length);
    }

}
