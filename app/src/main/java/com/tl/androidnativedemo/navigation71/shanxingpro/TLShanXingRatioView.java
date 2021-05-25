package com.tl.androidnativedemo.navigation71.shanxingpro;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Created by tianlin on 2018/4/13.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class TLShanXingRatioView extends View
{

    private int mWidth;
    private int mHeight;

    Paint paint;

    Paint textPaint;

    private double percent = 0.7;

    private String color = "#186BB1";

    private int radius;

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

        radius = Math.min(mWidth, mHeight);
        mWidth = radius * 2;
        mHeight = radius * 2;

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        // 画里层
        paint.setColor(Color.parseColor("#EEECED"));
        canvas.drawCircle(radius, radius, radius, paint);

        // 画前景色
        paint.setColor(Color.parseColor(color));
        RectF rect = new RectF();
        rect.left = 0;
        rect.top = 0;
        rect.right = mWidth;
        rect.bottom = mHeight;
        canvas.drawArc(rect, -90, (float) ((percent * 360)), true, paint);

        // 画背景颜色
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawCircle(radius, radius, (float) (radius * 0.8), paint);

        // 画文字
        textPaint.setTextSize(radius * 2 / 5);
        Rect textRect = new Rect();
        String text = percent * 100 + "%";
        textPaint.getTextBounds(text, 0, text.length(), textRect);
        textPaint.setColor(Color.parseColor(color));
        canvas.drawText(text, radius - textRect.width() / 2, radius + textRect.height() / 2, textPaint);
    }

    public double getPercent()
    {
        return percent;
    }

    public void setPercent(double percent)
    {
        this.percent = percent;
        invalidate();
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        invalidate();
    }

    private void initView(Context context)
    {
        paint = new Paint();
        paint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
    }

    public TLShanXingRatioView(Context context)
    {
        super(context);

        initView(context);
    }

    public TLShanXingRatioView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        initView(context);
    }

    public TLShanXingRatioView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TLShanXingRatioView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }
}
