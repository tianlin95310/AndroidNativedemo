package com.tl.androidnativedemo.navigation42.draw;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by tianlin on 2018/12/18.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class MyGlView extends GLSurfaceView implements GLSurfaceView.Renderer{

    private Context context;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d("my", "onSurfaceCreated");
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d("my", "onSurfaceChanged");
        gl.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Log.d("my", "onDrawFrame");
    }

    private void initView(Context context) {
        this.context = context;
        setRenderer(new MyRender());
    }

    public MyGlView(Context context) {
        super(context);
        initView(context);
    }

    public MyGlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
}
