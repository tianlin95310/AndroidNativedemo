package com.tl.androidnativedemo.navigation71.shanxingpro;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.tl.androidnativedemo.utils.thread.ThreadManager;


/**
 * Created by tianlin on 2018/4/13.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class TLShanXingRatioView2 extends View
{

    private int mWidth;
    private int mHeight;

    private int viewLength;
    private int padding;
    private int radius;
    private float strokeWidth;

    private Paint paintAl;
    private Paint paintLeft;
    private Paint paintBg;

    private int colors[] = new int[]{0xFFC5E1A4, 0xFF2E7D32};
    private long duration = 1000;
    private float degree = 0;

    private OnAnimOverListener onAnimOverListener;
    private boolean isRunning = false;

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.rotate(90, viewLength / 2, viewLength / 2);

        // 画里层背景
        paintBg.setColor(Color.parseColor("#ffffff"));
        canvas.drawCircle(viewLength / 2, viewLength / 2, radius, paintBg);

        // 画剩余进度
        RectF rect = new RectF();
        rect.left = padding;
        rect.top = padding;
        rect.right = padding + radius * 2;
        rect.bottom = padding + radius * 2;
        canvas.drawArc(rect, 0, 360, false, paintLeft);

        // 画前景色
        paintAl.setShader(new SweepGradient(viewLength / 2, viewLength / 2, colors, null));
        canvas.drawArc(rect, 0, degree, false, paintAl);

        // 画小圆
        if(degree > 0 && degree < 360) {
            paintBg.setColor(Color.parseColor("#C5E1A4"));
            canvas.drawCircle(viewLength / 2 + radius, viewLength / 2, strokeWidth / 2, paintBg);
        }

        if(degree > 0) {
            int currentColor = getCurrentColor(degree / 360.0f, colors);
            paintBg.setColor(currentColor);
            canvas.drawCircle(
                    (float) (viewLength / 2 + radius * Math.sin(Math.toRadians(90 + degree))),
                    (float) (viewLength / 2 - radius * Math.cos(Math.toRadians(90 + degree))),
                    strokeWidth / 2,
                    paintBg);
        }

    }

    public static int getCurrentColor(float percent, int[] colors) {
        float[][] f = new float[colors.length][3];
        for (int i = 0; i < colors.length; i++) {
            f[i][0] = (colors[i] & 0xff0000) >> 16;
            f[i][1] = (colors[i] & 0x00ff00) >> 8;
            f[i][2] = (colors[i] & 0x0000ff);
        }
        float[] result = new float[3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < f.length; j++) {
                if (f.length == 1 || percent == j / (f.length - 1f)) {
                    result = f[j];
                } else {
                    if (percent > j / (f.length - 1f) && percent < (j + 1f) / (f.length - 1)) {
                        result[i] = f[j][i] - (f[j][i] - f[j + 1][i]) * (percent - j / (f.length - 1f)) * (f.length - 1f);
                    }
                }
            }
        }
        return Color.rgb((int) result[0], (int) result[1], (int) result[2]);
    }

    public void start() {

        if(isRunning) {
            return;
        }

        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {

                isRunning = true;

                for(int i = 0; i <= 360; i++) {

                    degree = i;
                    postInvalidate();

                    i += 4;
                    SystemClock.sleep(duration / (360 / 4));
                }

                if(onAnimOverListener != null) {
                    onAnimOverListener.onAnimOver();
                }
                isRunning = false;

            }
        });
    }

    public OnAnimOverListener getOnAnimOverListener() {
        return onAnimOverListener;
    }

    public void setOnAnimOverListener(OnAnimOverListener onAnimOverListener) {
        this.onAnimOverListener = onAnimOverListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //宽度的模式
        int mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        //宽度大小
        int mWidthSize = MeasureSpec.getSize(widthMeasureSpec);

        int mHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int mHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        //如果明确大小,直接设置大小
        if (mWidthMode == MeasureSpec.EXACTLY)
        {
            mWidth = mWidthSize;
        } else
        {
            //计算宽度,可以根据实际情况进行计算
            mWidth = (getPaddingLeft() + getPaddingRight());
            //如果为AT_MOST, 不允许超过默认宽度的大小
            if (mWidthMode == MeasureSpec.AT_MOST)
            {
                mWidth = Math.min(mWidth, mWidthSize);
            }
        }

        if (mHeightMode == MeasureSpec.EXACTLY)
        {
            mHeight = mHeightSize;
        } else
        {
            mHeight = (getPaddingTop() + getPaddingBottom());
            if (mHeightMode == MeasureSpec.AT_MOST)
            {
                mHeight = Math.min(mHeight, mHeightSize);
            }
        }

        viewLength = Math.min(mWidth, mHeight);

        padding = (int) (viewLength * 0.1f);

        radius = (viewLength - padding * 2) / 2;

        strokeWidth = radius * 0.2f;
        paintAl.setStrokeWidth(strokeWidth);
        paintLeft.setStrokeWidth(strokeWidth);
        setMeasuredDimension(viewLength, viewLength);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        invalidate();
    }

    private void initView(Context context)
    {
        paintAl = new Paint();
        paintAl.setAntiAlias(true);
        paintAl.setStyle(Paint.Style.STROKE);

        paintLeft = new Paint();
        paintLeft.setAntiAlias(true);
        paintLeft.setStyle(Paint.Style.STROKE);
        paintLeft.setColor(Color.parseColor("#EBECEA"));

        paintBg = new Paint();
        paintBg.setAntiAlias(true);

    }

    public TLShanXingRatioView2(Context context)
    {
        super(context);

        initView(context);
    }

    public interface OnAnimOverListener {
        void onAnimOver();
    }
    public TLShanXingRatioView2(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        initView(context);
    }

    public TLShanXingRatioView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TLShanXingRatioView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
