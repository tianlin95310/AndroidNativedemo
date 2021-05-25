package com.tl.androidnativedemo.navigation51;

import android.util.Log;

/**
 * Created by tianlin on 2017/5/10.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */
public class MyJniClass
{
    /**
     * 加载本地已经存在的so库文件,若类名发生变化，那么对应的c语言实现的函数名也得发生变化
     */
    static
    {
        System.loadLibrary("jniTest");
    }

    /***********************调用c函数*********************************/
    /**
     * 让java调用c的加法
     * @param a
     * @param b
     * @return
     */
    public native int sum(int a, int b);

    /**
     * 让java调用c语言添加一个字符串
     * @param str
     * @return
     */
    public native String append(String str);

    /**
     * 让c把数组的每一个元素加10
     * @param array
     * @return
     */
    public native int[] operateEle(int[] array);

    /**
     * 让c检查密码是否正确
     * @param pwd
     * @return
     */
    public native boolean checkPwd(String pwd);

    /**********************触发调用c语言函数，在C函数中调用java函数*****************************/

    /**
     * 当执行这个方法的时候，让C代码调用
     * public int add(int x, int y)
     */
    public native void callbackAdd();

    /**
     * 当执行这个方法的时候，让C代码调用
     * public void helloFromJava()
     */
    public native void callbackHelloFromJava();

    /**
     * 当执行这个方法的时候，让C代码调用void printString(String s)
     */
    public native void callbackPrintString();

    /**
     * 当执行这个方法的时候，让C代码静态方法 static void sayHello(String s)
     */
    public native void callbackSayHello();

    /*************************触发c调用java后，这些方法会被执行******************************/
    /**
     * 参数是c语言函数内部的
     * @param x
     * @param y
     * @return
     */
    public int add(int x, int y) {
        Log.e("my", "add() x=" + x + " y=" + y);
        return x + y;
    }
    public void helloFromJava() {
        Log.e("my", "helloFromJava()");
    }
    public void printString(String s) {
        Log.e("my","C中输入的：" + s);
    }
    public static void sayHello(String s){
        Log.e("my",  "我是java代码中的JNI."
                + "java中的sayHello(String s)静态方法，我被C调用了:"+ s);
    }
}
