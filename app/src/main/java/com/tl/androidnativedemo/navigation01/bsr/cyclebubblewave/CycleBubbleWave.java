package com.tl.androidnativedemo.navigation01.bsr.cyclebubblewave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Created by tianlin on 2018/7/23.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class CycleBubbleWave extends View {

    /**
     * 整个视图的高宽
     */
    private int mWidth;
    private int mHeight;

    private int radius;

    /**
     * 周期长度，最大的长度是mWidth
     */
    private float periodWidth;
    /**
     * 振幅
     */
    private float mWaveAmplitude = 50f;
    /**
     * 水位
     */
    private float mWaveHeight = 0f;

    private Paint mPaint;

    private Path mPath;

    private int mWaterColor = 0xBB0000FF;

    private PointF mLeft1;
    private PointF mLeft2;
    private PointF mCenter;
    private PointF mRight1;
    private PointF mRight2;

    private PointF mControlLeft1;
    private PointF mControlLeft2;
    private PointF mControlRight1;
    private PointF mControlRight2;

    private float degree;
    private boolean isInit;

    @Override
    protected void onDraw(Canvas canvas) {


        if(!isInit)
            return;

        mPath.reset();
        RectF rect = new RectF();
        rect.left = 0;
        rect.top = 0;
        rect.right = mWidth;
        rect.bottom = mHeight;
        mPath.addArc(rect, 90 - degree, degree * 2);

        mPath.moveTo(mCenter.x, mCenter.y);
        mPath.quadTo(mControlRight1.x, mControlRight1.y, mRight1.x, mRight1.y);
        mPath.quadTo(mControlRight2.x, mControlRight2.y, mRight2.x, mRight2.y);
        canvas.drawPath(mPath, mPaint);
    }


    private void initPoint() {
        mCenter = new PointF((float) (radius - radius * Math.sin(Math.toRadians(degree))), mHeight - mWaveHeight);

        // 5个X轴焦点
        mLeft1 = new PointF(mCenter.x - periodWidth, mHeight - mWaveHeight);
        mLeft2 = new PointF(mCenter.x - periodWidth / 2, mHeight - mWaveHeight);

        mRight1 = new PointF(mCenter.x + periodWidth / 2, mHeight - mWaveHeight);
        mRight2 = new PointF(mCenter.x + periodWidth, mHeight - mWaveHeight);
        // 4个控制点，分别在波峰波谷的位置
        mControlLeft1 = new PointF(mLeft1.x + periodWidth / 4, mLeft1.y + mWaveAmplitude);
        mControlLeft2 = new PointF(mLeft2.x + periodWidth / 4, mLeft2.y - mWaveAmplitude);
        mControlRight1 = new PointF(mCenter.x + periodWidth / 4, mCenter.y + mWaveAmplitude);
        mControlRight2 = new PointF(mRight1.x + periodWidth / 4, mCenter.y - mWaveAmplitude);

        isInit = true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
       invalidate();
    }

    public void setmWaveAmplitude(float mWaveAmplitude) {
        this.mWaveAmplitude = mWaveAmplitude;
    }

    public void setDegree(float degree) {
        this.degree = degree;

        periodWidth = (float) (radius * Math.sin(Math.toRadians(degree))) * 2;
        mWaveHeight = (float) (radius - radius * Math.cos(Math.toRadians(degree)));

        initPoint();

        invalidate();
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

    public CycleBubbleWave(Context context) {
        super(context);
        initView(context);
    }

    public CycleBubbleWave(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CycleBubbleWave(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CycleBubbleWave(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

        radius = mWidth / 2;
        mWaveHeight = mHeight / 2;
        setMeasuredDimension(mWidth, mHeight);
    }

}
