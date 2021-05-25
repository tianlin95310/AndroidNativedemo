package com.tl.androidnativedemo.navigation71.shanxing;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.tl.androidnativedemo.R;

/**
 * Created by tianlin on 2017/6/6.
 * Tel : 15071485690
 * QQ : 953108373
 * Function : 圆环百分比图视图
 */

public class TLCycleRatioView extends View
{

    /**
     * 旋转模式
     */
    public static final int MODE_SCAN = 1;

    /**
     * 比率模式
     */
    public static final int MODE_RATIO = 2;

    private int mode = MODE_SCAN;

    /**
     * 显示的单位
     */
    private String unit = "%";
    /**
     * 默认背景色
     */
    private static final int DEFAULT_BG_COLOR = Color.WHITE;
    /**
     * 默认前景色，线段颜色
     */
    private static final int DEFAULT_FORE_COLOR = Color.GREEN;
    /**
     * 默认字体为黑色
     */
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;

    /**
     * 视图的宽
     */
    private int w;
    /**
     * 视图的高
     */
    private int h;
    /**
     * 源的半径
     */
    private int radius;
    /**
     * 所占的百分比
     */
    private int percent;
    /**
     * 背景色，默认为灰色
     */
    private int bgColor = DEFAULT_BG_COLOR;
    /**
     * 大圆前景色，默认为
     */
    private int foreColor = DEFAULT_FORE_COLOR;
    /**
     * 文字颜色，默认为黑色
     */
    private int textColor = DEFAULT_TEXT_COLOR;
    /**
     * 画笔
     */
    private Paint paint;
    /**
     * 线段位置
     */
    float startRate = 6.0f / 8;
    float endRate = 7.0f / 8;

    public TLCycleRatioView(Context context)
    {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public TLCycleRatioView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        paint = new Paint();
        paint.setAntiAlias(true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TLCycleRatioView);

        for (int i = 0; i < typedArray.getIndexCount(); i++)
        {
            /**
             * 在Android Library里面不能使用资源id来作为case的判断条件
             */

            switch (i)
            {
                case R.styleable.TLCycleRatioView_backgroundColor:
                    bgColor = typedArray.getColor(i, DEFAULT_BG_COLOR);
                    break;
                case R.styleable.TLCycleRatioView_foreColor:
                    foreColor = typedArray.getColor(i, DEFAULT_FORE_COLOR);
                    break;
                case R.styleable.TLCycleRatioView_innerTextColor:
                    textColor = typedArray.getColor(i, DEFAULT_TEXT_COLOR);
                    break;

            }
        }
        typedArray.recycle();

    }

    public TLCycleRatioView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TLCycleRatioView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        w = getMeasuredWidth();
        h = getMeasuredHeight();

        if (w >= h)
        {
            radius = h / 2;
        } else
        {
            radius = w / 2;
        }

        setMeasuredDimension(2 * radius, 2 * radius);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 最终获得的分数
     */
    private int score = 0;

    @Override
    protected void onDraw(Canvas canvas)
    {
        /**
         * 画背景
         * 这里虽然是画在圆点，但是View的onLayout会自动布局,View移动到指定的位置
         */
        paint.setColor(bgColor);
        canvas.drawCircle(radius, radius, radius, paint);
        paint.setStrokeWidth(5);

        /**
         * 画100根灰色线
         */
        paint.setColor(Color.GRAY);
        for (int i = 0; i < 100; i++)
        {
            canvas.drawLine(
                    radius - (float) (radius * startRate * Math.cos((-90 + i * 3.6) * Math.PI / 180)),
                    radius + (float) (radius * startRate * Math.sin((-90 + i * 3.6) * Math.PI / 180)),
                    radius - (float) (radius * endRate * Math.cos((-90 + i * 3.6) * Math.PI / 180)),
                    radius + (float) (radius * endRate * Math.sin((-90 + i * 3.6) * Math.PI / 180)),
                    paint);
        }

        if (mode == MODE_SCAN)
        {
            /**
             * 根据传入的角度画10根绿色线,进行循环操作
             */
            paint.setColor(foreColor);
            for (int i = 0; i < 10; i++)
            {
                canvas.drawLine(
                        radius - (float) (radius * startRate * Math.cos((-90 + percent * 3.6 + i * 3.6) * Math.PI / 180)),
                        radius + (float) (radius * startRate * Math.sin((-90 + percent * 3.6 + i * 3.6) * Math.PI / 180)),
                        radius - (float) (radius * endRate * Math.cos((-90 + percent * 3.6 + i * 3.6) * Math.PI / 180)),
                        radius + (float) (radius * endRate * Math.sin((-90 + percent * 3.6 + i * 3.6) * Math.PI / 180)),
                        paint);
            }

            /**
             * 画百分比文字
             */
            paint.setColor(textColor);
            paint.setTextSize(radius / 3);
            String strPercent = percent + unit;
            Rect textBound = new Rect();
            paint.getTextBounds(strPercent, 0, strPercent.length(), textBound);
            // x,y是文字左下角的位置
            canvas.drawText(strPercent, radius - textBound.width() / 2, radius + textBound.height() / 2, paint);

        } else if (mode == MODE_RATIO)
        {
            /**
             * 画指定跟根绿色线，即为最终的分数线
             */
            paint.setColor(foreColor);
            for (int i = 0; i < score; i++)
            {
                canvas.drawLine(
                        radius - (float) (radius * startRate * Math.cos((-90 + i * 3.6) * Math.PI / 180)),
                        radius + (float) (radius * startRate * Math.sin((-90 + i * 3.6) * Math.PI / 180)),
                        radius - (float) (radius * endRate * Math.cos((-90 + i * 3.6) * Math.PI / 180)),
                        radius + (float) (radius * endRate * Math.sin((-90 + i * 3.6) * Math.PI / 180)),
                        paint);
            }

            /**
             * 画最终的分数文字
             */
            paint.setColor(textColor);
            paint.setTextSize(radius / 3);
            String strPercent = score + unit;
            Rect textBound = new Rect();
            paint.getTextBounds(strPercent, 0, strPercent.length(), textBound);
            // x,y是文字左下角的位置
            canvas.drawText(strPercent, radius - textBound.width() / 2, radius + textBound.height() / 2, paint);
        }

    }

    public float getPercent()
    {
        return percent;
    }

    public void setPercent(int percent)
    {
        this.percent = percent;
        invalidate();
    }

    public void setPercentSync(int percent)
    {
        this.percent = percent;
        postInvalidate();
    }

    public int getBgColor()
    {
        return bgColor;
    }

    public TLCycleRatioView setBgColor(int bgColor)
    {
        this.bgColor = bgColor;
        return this;
    }

    public int getForeColor()
    {
        return foreColor;
    }

    public TLCycleRatioView setForeColor(int foreColor)
    {
        this.foreColor = foreColor;
        return this;
    }

    public int getTextColor()
    {
        return textColor;
    }

    public TLCycleRatioView setTextColor(int textColor)
    {
        this.textColor = textColor;
        return this;
    }

    public String getUnit()
    {
        return unit;
    }

    public TLCycleRatioView setUnit(String unit)
    {
        this.unit = unit;
        return this;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
        invalidate();
    }

    public void setScoreSync(int score)
    {
        this.score = score;
        postInvalidate();
    }

    public int getMode()
    {
        return mode;
    }

    public void setMode(int mode)
    {
        this.mode = mode;
    }
}
