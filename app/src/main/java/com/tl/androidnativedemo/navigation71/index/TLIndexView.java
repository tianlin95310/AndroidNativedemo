package com.tl.androidnativedemo.navigation71.index;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.tl.androidnativedemo.R;

/**
 * Created by tianlin on 2017/2/10.
 * Tel : 15071485690
 * QQ 953108373
 * Function : 索引视图
 */

public class TLIndexView extends View
{
    /**
     * 默认字符集
     */
    private static final String[] DEFAULT_CHARS = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};
    private static final int DEFAULT_BG_COLOR = Color.GRAY;
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_TEXT_SIZE = 30;
    private static final int DEFAULT_SEL_TEXT_COLOR = Color.BLACK;
    /**
     * item的宽度与高度
     */
    /**
     * 控件的宽
     */
    int itemWidth = 0;
    /**
     * 控件的高
     */
    int itemHeight = 0;
    /**
     *
     */
    int viewHeight = 0;
     /**
     * 控件要显示的字符
     */
    private String chars[] = DEFAULT_CHARS;
    /**
     * 画笔
     */
    Paint paint;

    // 当前选择的索引位置
    int currentSelect = -1;

    /**
     * 背景颜色，默认为灰色
     */
    private int bgColor = DEFAULT_BG_COLOR;
    /**
     * 文字颜色，默认为白色
     */
    private int textColor = DEFAULT_TEXT_COLOR;

    /**
     * 选中的文字颜色
     */
    private int selectTextColor = DEFAULT_SEL_TEXT_COLOR;
    /**
     * 字体大小
     */
    private int textSize = DEFAULT_TEXT_SIZE;

    public TLIndexView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(DEFAULT_TEXT_SIZE);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TLIndexView);

        for(int i = 0; i < typedArray.getIndexCount(); i++)
        {
            if(i == R.styleable.TLIndexView_bgColor)
            {
                bgColor = typedArray.getColor(i, DEFAULT_BG_COLOR);
            }
            else if(i == R.styleable.TLIndexView_textColor)
            {
                textColor = typedArray.getColor(i, DEFAULT_TEXT_COLOR);
            }
            else if(i == R.styleable.TLIndexView_selectTextColor)
            {
                selectTextColor = typedArray.getColor(i, DEFAULT_SEL_TEXT_COLOR);
            }
            else if(i == R.styleable.TLIndexView_textSize)
            {
                textSize = (int) typedArray.getDimension(i ,DEFAULT_TEXT_SIZE);
            }


        }
        typedArray.recycle();
    }

    public TLIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TLIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TLIndexView(Context context)
    {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(DEFAULT_TEXT_SIZE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        viewHeight = getMeasuredHeight();
        itemHeight = getMeasuredHeight() / chars.length;
        itemWidth = getMeasuredWidth();
    }

    /**
     * 监听器
     */
    public interface OnSelectChangeListener
    {
        /**
         * @param text 当前的文字,"" means no no select
         * @param view 当前的视图
         * @param isChange 所选内容是否变化，如果没有变化
         */
        void onSelectChange(String text, View view, boolean isChange);
    }

    private OnSelectChangeListener onSelectChangeListener;

    public void setOnSelectChangeListener(OnSelectChangeListener onSelectChangeListener)
    {
        this.onSelectChangeListener = onSelectChangeListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float downY = event.getY();
                int position = (int) (downY / itemHeight);

                if(position < chars.length || position >= 0)
                {
                    currentSelect = position;

                    if(onSelectChangeListener != null)
                        onSelectChangeListener.onSelectChange(chars[currentSelect], this, true);
                }
                break;
            case MotionEvent.ACTION_UP:

                currentSelect = -1;
                if(onSelectChangeListener != null)
                    onSelectChangeListener.onSelectChange("", this, false);
                break;
        }

        // 刷新界面
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        int x = 0;
        int y = 0;

        // 画背景
        paint.setColor(bgColor);
        canvas.drawRect(0, 0, itemWidth, viewHeight, paint);

        // 画文字
        paint.setTextSize(textSize);
        for(int i = 0; i < chars.length ; i++)
        {
            if(i == currentSelect)
            {
                paint.setColor(selectTextColor);
            }
            else
            {
                paint.setColor(textColor);
            }
            final String CHAR = chars[i];
            Rect charBounds = new Rect();
            paint.getTextBounds(CHAR, 0, 1, charBounds);
            x = itemWidth / 2  - charBounds.width() / 2;
            y = itemHeight / 2 + charBounds.height() / 2 + i * itemHeight;

            canvas.drawText(CHAR, x, y, paint);
        }
    }

    public String[] getChars()
    {
        return chars;
    }

    public void setChars(String[] chars)
    {
        this.chars = chars;
    }

    public int getBgColor()
    {
        return bgColor;
    }

    public void setBgColor(int bgColor)
    {
        this.bgColor = bgColor;
    }

    public int getTextColor()
    {
        return textColor;
    }

    public void setTextColor(int textColor)
    {
        this.textColor = textColor;
    }

    public int getSelectTextColor()
    {
        return selectTextColor;
    }

    public void setSelectTextColor(int selectTextColor)
    {
        this.selectTextColor = selectTextColor;
    }

    public int getTextSize()
    {
        return textSize;
    }

    public void setTextSize(int textSize)
    {
        this.textSize = textSize;
    }

    public OnSelectChangeListener getOnSelectChangeListener()
    {
        return onSelectChangeListener;
    }
}
