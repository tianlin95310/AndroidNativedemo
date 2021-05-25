package com.tl.androidnativedemo.navigation71.tiaoxingtu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.tl.androidnativedemo.utils.display.PixsUtils;
import com.tl.androidnativedemo.utils.nullcheck.NullCheckUtils;
import com.tl.androidnativedemo.utils.ui.DataSet;

import java.util.List;


/**
 * Created by tianlin on 2018/4/8.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class TLTiaoXingTuView extends View
{

    // 真个空间的宽高
    int mWidth;

    int mHeight;

    Paint barPaint;
    Paint textPaint;
    Paint linePaint;
    Paint zhexianPaint;

    // 横轴坐标
    private List<String> hengZuoBiaos;

    // 左边的坐标值，倒序
    private List<String> zongZuoBiaosLeft;
    // 右边的坐标值，倒序
    private List<String> zongZuoBiaosRight;
    // 实际数据，请采用单位值来表示
    private List<DataSet> dataSets;

    // 每条柱的宽度，对于双柱则一半
    private int itemWidth;
    // 单位的图形高度
    private int itemHeight;

    // 每条柱的最大底边达到的距离
    private int itemMaxHeight;

    // 左刻度的边距
    private int leftWidth;
    // 右刻度的边距
    private int rightWidth;
    // 顶端空余的部分
    private int topHeight;
    // 底部刻度的边距
    private int bottomHeight;

    // 横轴坐标的单位
    private String xUnit = "月";

    // 折线点的大小
    private int pointSize = 1;

    // 坐标轴上的数字距离坐标轴的边距
    private int textPadding = 1;

    // 是否画额外的线，除了X轴的线外
    boolean isDrawAnotherLine = true;
    // 是否画坐标额text
    boolean isDrawZuoBiaoTextLeftY = true;
    boolean isDrawZuoBiaoTextRightY = true;
    boolean isDrawZuoBiaoTextX = true;
    // 是否在条形图上画统计数值
    boolean isDrawValueOnBar = false;
    // 是否画折线
    boolean isDrawZhexian = true;
    // 是否是双柱
    boolean isDrawDoubleBar = true;

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
        //设置测量完成的宽高
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        // 防止控件的长宽还没有初始化
        if(mWidth != 0) {
            setDataSets(this.dataSets);
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (NullCheckUtils.isEmpty(dataSets))
        {
            return;
        }

        // 画坐标线
        drawLine(canvas);
        // 画条形图
        drawTiaoxing(canvas);

        // 画折线
        if (isDrawZhexian)
        {
            drawZheXian(canvas);
        }

        // 画横坐标刻度
        if (isDrawZuoBiaoTextX)
        {
            drawBottomX(canvas);
        }

        // 画纵轴左边的坐标
        if (isDrawZuoBiaoTextLeftY)
        {
            drawLeftY(canvas);
        }

        // 画纵轴右边的坐标
        if (isDrawZuoBiaoTextRightY)
        {
            drawRightY(canvas);
        }

    }

    private void drawRightY(Canvas canvas)
    {
        textPaint.setColor(Color.parseColor("#888888"));
        for (int i = 0; i < zongZuoBiaosRight.size(); i++)
        {

            String leftY = zongZuoBiaosRight.get(i);
            Rect rect = new Rect();
            textPaint.getTextBounds(leftY, 0, leftY.length(), rect);
            int x = mWidth - rightWidth + textPadding;
            int y = topHeight + i * itemHeight + rect.height() / 2;

            canvas.drawText(leftY, x, y, textPaint);
        }
    }

    private void drawLeftY(Canvas canvas)
    {
        textPaint.setColor(Color.parseColor("#888888"));
        for (int i = 0; i < zongZuoBiaosLeft.size(); i++)
        {

            String leftY = zongZuoBiaosLeft.get(i);
            Rect rect = new Rect();
            textPaint.getTextBounds(leftY, 0, leftY.length(), rect);
            int x = leftWidth - rect.width() - textPadding;
            int y = topHeight + i * itemHeight + rect.height() / 2;

            canvas.drawText(leftY, x, y, textPaint);
        }
    }

    private void drawZheXian(Canvas canvas)
    {

        for (int i = 0; i < dataSets.size() - 1; i++)
        {
            DataSet dataSet = dataSets.get(i);

            int x1 = itemWidth * 2 * i + leftWidth + itemWidth + itemWidth / 2;
            int y1 = (int) (dataSet.rate * itemHeight + topHeight);
            canvas.drawCircle(x1, y1, pointSize, zhexianPaint);

            DataSet nextOne = dataSets.get(i + 1);
            int x2 = itemWidth * 2 * (i + 1) + leftWidth + itemWidth + itemWidth / 2;
            int y2 = (int) (nextOne.rate * itemHeight + topHeight);

            canvas.drawCircle(x2, y2, pointSize, zhexianPaint);

            canvas.drawLine(x1, y1, x2, y2, zhexianPaint);

        }
    }

    private void drawLine(Canvas canvas)
    {
        linePaint.setColor(Color.parseColor("#cccccc"));

        canvas.drawLine(leftWidth, itemMaxHeight, mWidth - rightWidth, itemMaxHeight, linePaint);

        if (isDrawAnotherLine)
        {
            canvas.drawLine(mWidth - rightWidth, 0, mWidth - rightWidth, itemMaxHeight, linePaint);
            canvas.drawLine(leftWidth, 0, leftWidth, itemMaxHeight, linePaint);

            linePaint.setColor(Color.parseColor("#eeeeee"));
            for (int i = 0; i < 5; i++)
            {
                canvas.drawLine(rightWidth, i * itemHeight + topHeight, mWidth - rightWidth, i * itemHeight + topHeight, linePaint);
            }
        }

    }

    private void drawBottomX(Canvas canvas)
    {

        textPaint.setColor(Color.parseColor("#666666"));
        for (int i = 0; i < hengZuoBiaos.size(); i++)
        {

            String text = hengZuoBiaos.get(i);
            Rect textBound = new Rect();

            textPaint.getTextBounds(text, 0, text.length(), textBound);

            int x = itemWidth * 2 * i + leftWidth + itemWidth + itemWidth / 2 - textBound.width() / 2;
            int y = itemMaxHeight + textBound.height() + textPadding;

            canvas.drawText(text, x, y, textPaint);
        }

        Rect unitBound = new Rect();
        textPaint.getTextBounds(xUnit, 0, xUnit.length(), unitBound);
        canvas.drawText(xUnit, mWidth - rightWidth, itemMaxHeight + unitBound.height() + textPadding, textPaint);

    }

    private void drawTiaoxing(Canvas canvas)
    {
        for (int i = 0; i < dataSets.size(); i++)
        {

            DataSet dataSet = dataSets.get(i);
            if (isDrawDoubleBar)
            {
                Rect rectLeft = new Rect();
                rectLeft.left = itemWidth * 2 * i + leftWidth + itemWidth;
                rectLeft.right = itemWidth * 2 * i + leftWidth + itemWidth + itemWidth / 2;
                rectLeft.top = (int) (dataSet.data1 * itemHeight + topHeight);
                rectLeft.bottom = itemMaxHeight;

                LinearGradient lg = new LinearGradient(
                        rectLeft.left + itemWidth / 2,
                        rectLeft.top,
                        rectLeft.left + itemWidth / 2,
                        rectLeft.bottom,
                        Color.parseColor("#6193D0"),
                        Color.parseColor("#3F62A6"),
                        Shader.TileMode.CLAMP);
                barPaint.setShader(lg);
                canvas.drawRect(rectLeft, barPaint);

                Rect rectRight = new Rect();
                rectRight.left = itemWidth * 2 * i + leftWidth + itemWidth + itemWidth / 2;
                rectRight.right = itemWidth * 2 * i + leftWidth + itemWidth + itemWidth;
                rectRight.top = (int) (dataSet.data2 * itemHeight + topHeight);
                rectRight.bottom = itemMaxHeight;

                lg = new LinearGradient(
                        rectLeft.left + itemWidth / 2,
                        rectLeft.top,
                        rectLeft.left + itemWidth / 2,
                        rectLeft.bottom,
                        Color.parseColor("#43E5FE"),
                        Color.parseColor("#248CBF"),
                        Shader.TileMode.CLAMP);
                barPaint.setShader(lg);
                canvas.drawRect(rectRight, barPaint);
            } else
            {
                Rect rectLeft = new Rect();
                rectLeft.left = itemWidth * 2 * i + leftWidth + itemWidth;
                rectLeft.right = itemWidth * 2 * i + leftWidth + itemWidth + itemWidth;
                rectLeft.top = (int) (dataSet.data1 * itemHeight + topHeight);
                rectLeft.bottom = itemMaxHeight;

                if (dataSet.color != null)
                {
                    barPaint.setColor(Color.parseColor(dataSet.color));
                    textPaint.setColor(Color.parseColor(dataSet.color));
                }
                canvas.drawRect(rectLeft, barPaint);

                if (isDrawValueOnBar)
                {
                    Rect valueRect = new Rect();
                    textPaint.getTextBounds(dataSet.value, 0, dataSet.value.length(), valueRect);
                    canvas.drawText(dataSet.value,
                            rectLeft.left + itemWidth / 2 - valueRect.width() / 2,
                            rectLeft.top - textPadding,
                            textPaint
                    );
                }

            }

        }
    }

    private void initView(Context context)
    {
        barPaint = new Paint();
        barPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(36);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(3);

        zhexianPaint = new Paint();
        zhexianPaint.setAntiAlias(true);
        zhexianPaint.setStrokeWidth(3);
        zhexianPaint.setColor(Color.parseColor("#F8B822"));

        pointSize = PixsUtils.dp2px(context, 3);
        textPadding = PixsUtils.dp2px(context, 5);
    }

    public List<String> getHengZuoBiaos()
    {
        return hengZuoBiaos;
    }

    public void setHengZuoBiaos(List<String> hengZuoBiaos)
    {
        if (NullCheckUtils.isEmpty(hengZuoBiaos))
        {
            isDrawZuoBiaoTextX = false;
            return;
        }
        this.hengZuoBiaos = hengZuoBiaos;

        Rect textSize = new Rect();
        textPaint.getTextBounds(hengZuoBiaos.get(0), 0, hengZuoBiaos.get(0).length(), textSize);
        bottomHeight = textSize.height() * 2;
        topHeight = bottomHeight;

    }

    public List<String> getZongZuoBiaosLeft()
    {
        return zongZuoBiaosLeft;
    }

    public void setZongZuoBiaosLeft(List<String> zongZuoBiaosLeft)
    {
        if (NullCheckUtils.isEmpty(zongZuoBiaosLeft))
        {
            isDrawZuoBiaoTextLeftY = false;
            return;
        }
        this.zongZuoBiaosLeft = zongZuoBiaosLeft;

        Rect textSize = new Rect();
        textPaint.getTextBounds(zongZuoBiaosLeft.get(0), 0, zongZuoBiaosLeft.get(0).length(), textSize);
        leftWidth = textSize.width() * 2;
        rightWidth = textSize.width() * 2;
    }

    public List<DataSet> getDataSets()
    {
        return dataSets;
    }

    public void setDataSets(List<DataSet> dataSets)
    {

        if(NullCheckUtils.isEmpty(dataSets)) {
            return;
        }
        this.dataSets = dataSets;

        if(isDrawValueOnBar) {
            Rect rect = new Rect();
            textPaint.getTextBounds(dataSets.get(0).value, 0, dataSets.get(0).value.length(), rect);
            topHeight = rect.height() * 2;
            bottomHeight = 0;
            leftWidth = 0;
            rightWidth = 0;
        }

        itemWidth = (mWidth - leftWidth - rightWidth) / (dataSets.size() * 2 + 1);
        itemMaxHeight = mHeight - bottomHeight;
        itemHeight = (mHeight - topHeight - bottomHeight) / 5;

        invalidate();
    }

    public List<String> getZongZuoBiaosRight()
    {
        return zongZuoBiaosRight;
    }

    public void setZongZuoBiaosRight(List<String> zongZuoBiaosRight)
    {
        this.zongZuoBiaosRight = zongZuoBiaosRight;
    }

    public boolean isDrawAnotherLine()
    {
        return isDrawAnotherLine;
    }

    public void setDrawAnotherLine(boolean drawAnotherLine)
    {
        isDrawAnotherLine = drawAnotherLine;
    }

    public boolean isDrawZuoBiaoTextLeftY()
    {
        return isDrawZuoBiaoTextLeftY;
    }

    public void setDrawZuoBiaoTextLeftY(boolean drawZuoBiaoTextLeftY)
    {
        isDrawZuoBiaoTextLeftY = drawZuoBiaoTextLeftY;
    }

    public boolean isDrawZuoBiaoTextRightY()
    {
        return isDrawZuoBiaoTextRightY;
    }

    public void setDrawZuoBiaoTextRightY(boolean drawZuoBiaoTextRightY)
    {
        isDrawZuoBiaoTextRightY = drawZuoBiaoTextRightY;
    }

    public boolean isDrawZuoBiaoTextX()
    {
        return isDrawZuoBiaoTextX;
    }

    public void setDrawZuoBiaoTextX(boolean drawZuoBiaoTextX)
    {
        isDrawZuoBiaoTextX = drawZuoBiaoTextX;
    }

    public boolean isDrawValueOnBar()
    {
        return isDrawValueOnBar;
    }

    public void setDrawValueOnBar(boolean drawDataOnBar)
    {
        isDrawValueOnBar = drawDataOnBar;
    }

    public boolean isDrawZhexian()
    {
        return isDrawZhexian;
    }

    public void setDrawZhexian(boolean drawZhexian)
    {
        isDrawZhexian = drawZhexian;
    }

    public boolean isDrawDoubleBar()
    {
        return isDrawDoubleBar;
    }

    public void setDrawDoubleBar(boolean isDrawDoubleBar)
    {
        this.isDrawDoubleBar = isDrawDoubleBar;
    }

    public TLTiaoXingTuView(Context context)
    {
        super(context);
        initView(context);
    }

    public TLTiaoXingTuView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        initView(context);
    }

    public TLTiaoXingTuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TLTiaoXingTuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }
}
