package com.tl.androidnativedemo.navigation01.bsr.waveview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.tl.androidnativedemo.utils.thread.ThreadManager;

/**
 * Created by tianlin on 2018/7/23.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class WaveView extends View {

    private int mWidth;
    private int mHeight;
    /**
     * 振幅
     */
    private float mWaveAmplitude = 35f;
    /**
     * 水位
     */
    private float mWaveHeight = 250f;

    private Paint mPaint;

    private Path mPath;

    private int mWaterColor = 0xBB0000FF;

    private boolean hasInit = false;

    private boolean isRunning;

    private PointF mLeft1;
    private PointF mLeft2;
    private PointF mCenter;
    private PointF mRight1;
    private PointF mRight2;

    private PointF mControlLeft1;
    private PointF mControlLeft2;
    private PointF mControlRight1;
    private PointF mControlRight2;

    private PointF mLeftStart;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0x1001) {

                Log.d("my", "handleMessage msg.what == 0x1001");
                initPoint();

                isRunning = true;

                beginValueAnim();
            }
        }
    };

    private void beginValueAnim() {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mLeftStart.x, 0);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(Animation.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                mLeft1.x = (float) animation.getAnimatedValue();
                mLeft1.y = mHeight - mWaveHeight;
                mLeft2.x = mLeft1.x + mWidth / 2;
                mLeft2.y = mHeight - mWaveHeight;
                mCenter.x = mLeft2.x + mWidth / 2;
                mCenter.y = mHeight - mWaveHeight;
                mRight1.x = mCenter.x + mWidth / 2;
                mRight1.y = mHeight - mWaveHeight;
                mRight2.x = mRight1.x + mWidth / 2;
                mRight2.y = mHeight - mWaveHeight;

                mControlLeft1.x = mLeft1.x + mWidth / 4;
                mControlLeft1.y = mLeft1.y + mWaveAmplitude;
                mControlLeft2.x = mLeft2.x + mWidth / 4;
                mControlLeft2.y = mLeft2.y - mWaveAmplitude;
                mControlRight1.x = mCenter.x + mWidth / 4;
                mControlRight1.y = mCenter.y + mWaveAmplitude;
                mControlRight2.x = mRight1.x + mWidth / 4;
                mControlRight2.y = mRight1.y - mWaveAmplitude;

                invalidate();
            }
        });
        valueAnimator.start();

    }

    private void initPoint() {
        // 控制点
        mLeftStart = new PointF(-mWidth, mHeight - mWaveHeight);
        // 5个X轴焦点
        mLeft1 = new PointF(-mWidth, mHeight - mWaveHeight);
        mLeft2 = new PointF(-mWidth / 2, mHeight - mWaveHeight);
        mCenter = new PointF(0, mHeight - mWaveHeight);
        mRight1 = new PointF(mWidth / 2, mHeight - mWaveHeight);
        mRight2 = new PointF(mWidth, mHeight - mWaveHeight);
        // 4个控制点，分别在波峰波谷的位置
        mControlLeft1 = new PointF(mLeft1.x + mWidth / 4, mLeft1.y + mWaveAmplitude);
        mControlLeft2 = new PointF(mLeft2.x + mWidth / 4, mLeft2.y - mWaveAmplitude);
        mControlRight1 = new PointF(mCenter.x + mWidth / 4, mCenter.y + mWaveAmplitude);
        mControlRight2 = new PointF(mRight1.x + mWidth / 4, mCenter.y - mWaveAmplitude);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        if (w != 0 && !hasInit) {
            mWidth = w;
            mHeight = h;
            hasInit = true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (!isRunning || !hasInit) {
            return;
        }

        mPath.reset();
        mPath.moveTo(mLeft1.x, mLeft1.y);
        mPath.quadTo(mControlLeft1.x, mControlLeft1.y, mLeft2.x, mLeft2.y);
        mPath.quadTo(mControlLeft2.x, mControlLeft2.y, mCenter.x, mCenter.y);
        mPath.quadTo(mControlRight1.x, mControlRight1.y, mRight1.x, mRight1.y);
        mPath.quadTo(mControlRight2.x, mControlRight2.y, mRight2.x, mRight2.y);
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);

        canvas.drawPath(mPath, mPaint);
    }

    public void startAnim() {
        ThreadManager.execute(() -> {
            while (!hasInit) {
                SystemClock.sleep(50);
            }
            handler.sendEmptyMessage(0x1001);
        });
    }

    public float getmWaveAmplitude() {
        return mWaveAmplitude;
    }

    public void setmWaveAmplitude(float mWaveAmplitude) {
        this.mWaveAmplitude = mWaveAmplitude;
    }

    public float getmWaveHeight() {
        return mWaveHeight;
    }

    public void setmWaveHeight(float mWaveHeight) {
        this.mWaveHeight = mWaveHeight * mHeight * 0.9f / 100;
    }

    public int getmWaterColor() {
        return mWaterColor;
    }

    public void setmWaterColor(int mWaterColor) {
        this.mWaterColor = mWaterColor;
    }

    private void initView(Context context) {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mWaterColor);
    }

    public WaveView(Context context) {
        super(context);
        initView(context);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //宽度的模式
        int mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        //宽度大小
        int mWidthSize = MeasureSpec.getSize(widthMeasureSpec);

        int mHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int mHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        //如果明确大小,直接设置大小
        if (mWidthMode == MeasureSpec.EXACTLY) {
            mWidth = mWidthSize;
        } else {
            //计算宽度,可以根据实际情况进行计算
            mWidth = (getPaddingLeft() + getPaddingRight());
            //如果为AT_MOST, 不允许超过默认宽度的大小
            if (mWidthMode == MeasureSpec.AT_MOST) {
                mWidth = Math.min(mWidth, mWidthSize);
            }
        }

        if (mHeightMode == MeasureSpec.EXACTLY) {
            mHeight = mHeightSize;
        } else {
            mHeight = (getPaddingTop() + getPaddingBottom());
            if (mHeightMode == MeasureSpec.AT_MOST) {
                mHeight = Math.min(mHeight, mHeightSize);
            }
        }

        setMeasuredDimension(mWidth, mHeight);
    }

}
