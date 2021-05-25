package com.tl.androidnativedemo.navigation72.procicle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.tl.androidnativedemo.utils.display.PixsUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tianlin on 2018/9/7.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class CycleView extends View {

    private float DELETE_POINT_TIMES = 4;

    private float EXTRA_SIZE;
    public List<MyPointF> points = new ArrayList<>();

    float x, y;
    float radius;
    Paint paint;

    Paint paintDot;

    float pointSize;
    private float startX;
    private float startY;
    private float totalDx;
    private float totalDy;

    public float getDELETE_POINT_TIMES() {
        return DELETE_POINT_TIMES;
    }

    public void setDELETE_POINT_TIMES(float DELETE_POINT_TIMES) {
        this.DELETE_POINT_TIMES = DELETE_POINT_TIMES;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(totalDx, totalDy);
        Log.d("my", "totalDx = " + totalDx);

        getCyclePositionAndRadius();

        canvas.drawCircle(x, y, radius, paint);

        for(PointF pointF : points) {
            canvas.drawCircle(pointF.x, pointF.y, pointSize, paintDot);
        }
    }

    private void getCyclePositionAndRadius() {
        if(points.size() == 0) {
            return;
        }

        if(points.size() == 1) {

            PointF one = points.get(0);
            x = one.x;
            y = one.y;
            radius = EXTRA_SIZE;
        }
        else if(points.size() > 1) {

            dealWithPoints();
        }
    }

    /**
     * 先找中心点，在排除，再找中心点
     */
    private void dealWithPoints() {
        float totalX = 0;
        float totalY = 0;

        int actSize = 0;
        for(MyPointF pointF : points) {

            if(!pointF.isRemove) {
                totalX += pointF.x;
                totalY += pointF.y;
                actSize ++;
            }
        }

        float avgX = totalX / actSize;
        float avgY = totalY / actSize;

        PointF center = new PointF(avgX, avgY);

        // 平均距离
        float avgDistance = 0;
        for(MyPointF pointF : points) {

            if(!pointF.isRemove) {
                float distance = cacuDistance(pointF, center);
                avgDistance += distance / actSize;
            }
        }

        // 最大距离
        float maxDistance = 0;
        for(MyPointF pointF : points) {
            if(!pointF.isRemove) {
                float distance = cacuDistance(pointF, center);
                if(distance > maxDistance) {
                    maxDistance = distance;
                }
            }
        }

        if(maxDistance / avgDistance > DELETE_POINT_TIMES) {

            Iterator<MyPointF> iterator =  points.iterator();
            while(iterator.hasNext()) {
                MyPointF pointF = iterator.next();
                if(!pointF.isRemove) {
                    float distance = cacuDistance(pointF, center);
                    if(distance >= maxDistance) {
                        pointF.isRemove = true;
                    }
                }

            }
            dealWithPoints();
        }
        else {
            x = avgX;
            y = avgY;
            radius = maxDistance;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                Log.d("my", "event.getRawX() = " + event.getRawX());
                MyPointF point = new MyPointF(event.getRawX() - totalDx, event.getRawY() - totalDy);
                points.add(point);

                invalidate();

                startX = event.getRawX();
                startY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                float dx = event.getRawX() - startX;
                float dy = event.getRawY() - startY;

                Log.d("my", "dx = " + dx);

                totalDx += dx;
                totalDy += dy;

                startX = event.getRawX();
                startY = event.getRawY();

                invalidate();
                break;


            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }


    private float cacuDistance(PointF pointF1, PointF pointF2) {
        return (float) Math.sqrt((pointF1.x - pointF2.x) * (pointF1.x - pointF2.x) + (pointF1.y - pointF2.y) * (pointF1.y - pointF2.y));
    }

    private void initView(Context context) {
        paint = new Paint();
        paint.setColor(Color.parseColor("#66888888"));

        paintDot = new Paint();
        paintDot.setColor(Color.BLUE);

        pointSize = PixsUtils.dp2px(context, 3);
        EXTRA_SIZE = PixsUtils.dp2px(context, 5);
    }

    public CycleView(Context context) {
        super(context);
        initView(context);
    }

    public CycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CycleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CycleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void refresh() {
        for(MyPointF pointF : points) {
            pointF.isRemove = false;
            invalidate();
        }
    }
}
