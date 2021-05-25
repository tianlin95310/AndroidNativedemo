package com.tl.androidnativedemo.navigation01.bsr.flowwater;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PathFlowView extends View {

    private PointF mPointF1;

    private PointF mPointF2;

    private PointF mControl;

    public PathFlowView(Context context) {
        super(context);
        init();
    }

    public PathFlowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PathFlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xFF412129);
        mPath = new Path();
        mPointF1 = new PointF(100, 200);
        mPointF2 = new PointF(500, 200);
        mControl = new PointF(0, 0);

        timer.schedule(timerTask, 0, 50);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mControl.x = event.getX();
                mControl.y = event.getY();
                invalidate();
                break;
        }
        return true;
    }

    private Paint mPaint;

    private Path mPath;

    Handler handler = new Handler();

    Timer timer = new Timer();

    Random r = new Random();

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            i++;
            mControl.x += (r.nextBoolean() ? 1 : -1) * i;
            mControl.y = i * 20;
            if(i == 30) {
                i = 0;
            }
            postInvalidate();
        }
    };
    int i = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mPointF1.x, mPointF1.y);
        // 画曲线
        mPath.quadTo(mControl.x, mControl.y, mPointF2.x, mPointF2.y);
        // 画直线
        mPath.lineTo(mPointF2.x, mPointF2.y);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        timerTask.cancel();
        timer.cancel();
    }

}
