package com.tl.androidnativedemo.navigation51;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tl.androidnativedemo.R;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by tianlin on 2017/5/10.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class Fragment51 extends Fragment {
    @BindView(R.id.f9_sum)
    Button f9Sum;
    @BindView(R.id.f9_append)
    Button f9Append;
    @BindView(R.id.f9_operate_array)
    Button f9OperateArray;
    @BindView(R.id.f9_check)
    Button f9Check;
    Unbinder unbinder;

    MyJniClass jniClass;
    @BindView(R.id.f9_c_call_java_add)
    Button f9CCallJavaAdd;
    @BindView(R.id.f9_c_call_java_m1)
    Button f9CCallJavaM1;
    @BindView(R.id.f9_c_call_java_m2)
    Button f9CCallJavaM2;
    @BindView(R.id.f9_c_call_java_static_m)
    Button f9CCallJavaStaticM;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment51, container, false);
//        unbinder = ButterKnife.bind(this, view);
//        jniClass = new MyJniClass();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }

    @OnClick({R.id.f9_sum, R.id.f9_append, R.id.f9_operate_array, R.id.f9_check})
    public void onViewClicked1(View view) {
        switch (view.getId()) {
            case R.id.f9_sum:
                Log.d("my", "add = " + jniClass.sum(10, 20));
                break;
            case R.id.f9_append:
                Log.d("my", "append = " + jniClass.append("Iam from Java "));
                break;
            case R.id.f9_operate_array:

                int array[] = new int[]{1, 2, 3, 4, 5};

                int result[] = jniClass.operateEle(array);

                for (int i = 0; i < result.length; i++) {
                    Log.d("my", "i = " + result[i]);
                }
                break;
            case R.id.f9_check:

                Log.d("my", "result = " + jniClass.checkPwd("123456"));
                break;
        }
    }

    @OnClick({R.id.f9_c_call_java_add, R.id.f9_c_call_java_m1, R.id.f9_c_call_java_m2, R.id.f9_c_call_java_static_m})
    public void onViewClicked2(View view) {
        switch (view.getId()) {
            case R.id.f9_c_call_java_add:
                jniClass.callbackAdd();
                break;
            case R.id.f9_c_call_java_m1:
                jniClass.callbackHelloFromJava();
                break;
            case R.id.f9_c_call_java_m2:
                jniClass.callbackPrintString();
                break;
            case R.id.f9_c_call_java_static_m:
                jniClass.callbackSayHello();
                break;
        }
    }
}
