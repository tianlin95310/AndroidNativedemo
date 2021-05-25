package com.tl.androidnativedemo.navigation71.menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;

import com.tl.androidnativedemo.utils.display.DensityUtils;
import com.tl.androidnativedemo.utils.display.PixsUtils;
import com.tl.androidnativedemo.utils.thread.ThreadManager;

/**
 * Created by tianlin on 2018/5/25.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class ShanXingMenuView extends View
{
    // 没两秒钟菜单自动弹出
    private static final long AUTO_SHOW_TIME = 2000;
    /**
     * 菜单伸长扩展速度
     */
    private int SPREAD_SPEED = 5;
    /**
     * 扩展界面的刷新速度1000 / 10 = 100帧
     */
    private static final long REFRESH_SPEED = 10;

    private Context context;
    /**
     * 视图的长和宽
     */
    private int mWidth;
    private int mHeight;
    /**
     * 小圆的画笔
     */
    private Paint initPaint;
    /**
     * 三个菜单的画笔
     */
    private Paint menu1Paint;
    private Paint menu2Paint;
    private Paint menu3Paint;

    private Bitmap initIcon;
    private Bitmap openingIcon;
    private Bitmap openingIconDraw;
    private Bitmap menu1Icon;
    private Bitmap menu2Icon;
    private Bitmap menu3Icon;
    /**
     * 写文字的画笔
     */
    private Paint textPaint;

    /**
     * 菜单全部展开的半径
     */
    private int radius;

    /**
     * 菜单展开的进度
     */
    private int progress;

    // 小圆的半径
    private int initArcRadius;

    // 小圆的半径
    private int initArcRadiusOpen;

    private int menu = 0;

    // 边长
    private int viewLength;

    // 菜单是否完全展开
    private boolean isOpen;

    // 菜单标题
    private String menu1Title;
    private String menu2Title;
    private String menu3Title;

    private String menu3Message;

    // 消息提示数量
    private String message;

    private double padding = 12;

    private boolean isAnimRunning;
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 1)
            {
                show();
            }
        }
    };

    private OnMenuListener onMenuListener;
    private int progressIcon;
    private boolean opening;

    private int degree;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if(progress < initArcRadius) {
            drawInitCycle(canvas);
            drawMovingIcon(canvas);
        }
        else if(progress >= initArcRadius && progress < initArcRadiusOpen){
            drawInitCycleSpread(canvas);
            drawInitCycle(canvas);
            drawMovingIcon(canvas);
        }
        else if(progress >= initArcRadiusOpen) {
            drawMenu(canvas);
            drawInitCycleOpen(canvas);
            drawMovingIcon(canvas);
        }

        if(progress > radius){
            drawMenuText(canvas);
            drawMenuIcon(canvas);
        }

    }

    private void drawInitCycleSpread(Canvas canvas)
    {
        int radius = progress;
        if(progress > initArcRadiusOpen) {
            radius = initArcRadiusOpen;
        }
        RectF initRect = new RectF();
        initRect.left = viewLength - radius;
        initRect.top = viewLength - radius;
        initRect.right = initRect.left + radius * 2;
        initRect.bottom = initRect.top + radius * 2;

        initPaint.setColor(Color.WHITE);
        canvas.drawArc(initRect, -180, 90, true, initPaint);
    }

    private void drawMovingIcon(Canvas canvas)
    {
        drawAMovingIcon(menu1Icon, canvas, 15);
        drawAMovingIcon(menu2Icon, canvas, 45);
        drawAMovingIcon(menu3Icon, canvas, 75);
    }

    private void drawAMovingIcon(Bitmap bitmap, Canvas canvas, int degree)
    {
        int x = (int) (viewLength - progressIcon * Math.cos(Math.toRadians(degree)));
        int y = (int) (viewLength - progressIcon * Math.sin(Math.toRadians(degree)));

        int radius = progressIcon / 10;

        RectF proRect = new RectF();
        proRect.left = x - radius;
        proRect.top = y - radius;
        proRect.right = proRect.left + radius * 2;
        proRect.bottom = proRect.top + radius * 2;

        canvas.drawBitmap(bitmap, null, proRect, new Paint());
    }

    private void drawMenuIcon(Canvas canvas)
    {
        Paint tempPaint = new Paint();
        tempPaint.setAntiAlias(true);

        drawAMenuIcon(canvas, tempPaint, menu1Icon, 15);
        drawAMenuIcon(canvas, tempPaint, menu2Icon, 45);
        drawAMenuIcon(canvas, tempPaint, menu3Icon, 75);
    }

    private void drawAMenuIcon(Canvas canvas, Paint paint, Bitmap icon, double degree)
    {
        Rect menuIconRect = new Rect();
        int x = (int) (progressIcon * Math.cos(Math.toRadians(degree)));
        int y = (int) (progressIcon * Math.sin(Math.toRadians(degree)));
        int radius = progressIcon / 10;

        menuIconRect.left = viewLength - x - radius;
        menuIconRect.top = viewLength - y - radius;
        menuIconRect.right = viewLength - x + radius;
        menuIconRect.bottom = viewLength - y + radius;

        if (icon != null)
            canvas.drawBitmap(icon, null, menuIconRect, paint);

        if (degree == 75)
        {
            if (TextUtils.isEmpty(menu3Message))
            {
                return;
            }
            Rect msgRect = new Rect();
            textPaint.setTextSize(initArcRadius / 5);
            textPaint.getTextBounds(menu3Message, 0, menu3Message.length(), msgRect);
            // 画红色圆圈
            paint.setColor(Color.RED);
            canvas.drawCircle(menuIconRect.right, menuIconRect.top, msgRect.width() * 1.5f, paint);
            // 画菜单3的数字
            textPaint.setColor(Color.WHITE);
            canvas.drawText(menu3Message, menuIconRect.right - msgRect.width() / 2,
                    menuIconRect.top + msgRect.height() / 2, textPaint);
        }

    }

    private void drawMenuText(Canvas canvas)
    {
        textPaint.setTextSize(initArcRadius / 4);
        textPaint.setColor(Color.GRAY);
        if (menu == 0)
        {
            drawAMenuText(canvas, menu1Title, 15);
            drawAMenuText(canvas, menu2Title, 45);
            drawAMenuText(canvas, menu3Title, 75);
        }
        if (menu == 1)
        {
            drawAMenuText(canvas, menu1Title, 15);
        }
        if (menu == 2)
        {
            drawAMenuText(canvas, menu2Title, 45);
        }
        if (menu == 3)
        {
            drawAMenuText(canvas, menu3Title, 75);
        }
    }

    private void drawAMenuText(Canvas canvas, String menu, double degree)
    {
        if (TextUtils.isEmpty(menu))
            return;

        Rect menuRect = new Rect();
        textPaint.getTextBounds(menu, 0, menu.length(), menuRect);
        float x = viewLength - (float) (progressIcon / 0.8f * Math.cos(Math.toRadians(degree)) + menuRect.width() +
                padding * Math.cos(Math.toRadians(degree)));
        float y = viewLength - (float) (progressIcon / 0.8f * Math.sin(Math.toRadians(degree)) +
                padding * Math.sin(Math.toRadians(degree)));
        canvas.drawText(menu, x, y, textPaint);
    }

    private void drawInitCycleOpen(Canvas canvas)
    {
        RectF initRect = new RectF();
        initRect.left = viewLength - initArcRadiusOpen;
        initRect.top = viewLength - initArcRadiusOpen;
        initRect.right = initRect.left + initArcRadiusOpen * 2;
        initRect.bottom = initRect.top + initArcRadiusOpen * 2;
        initPaint.setColor(Color.WHITE);
        canvas.drawArc(initRect, -180, 90, true, initPaint);

        Paint tempPaint = new Paint();
        tempPaint.setAntiAlias(true);

        RectF initRectIcon = new RectF();
        initRectIcon.left = viewLength - initArcRadiusOpen * 0.99f;
        initRectIcon.top = viewLength - initArcRadiusOpen * 0.99f;
        initRectIcon.right = viewLength + initArcRadiusOpen * 0.99f;
        initRectIcon.bottom = viewLength + initArcRadiusOpen * 0.99f;

        if (openingIconDraw != null && !openingIconDraw.isRecycled())
            canvas.drawBitmap(openingIconDraw, null, initRectIcon, tempPaint);
    }

    private void drawInitCycle(Canvas canvas)
    {
        Paint tempPaint = new Paint();
        tempPaint.setAntiAlias(true);

        RectF initRect = new RectF();
        initRect.left = viewLength - initArcRadius;
        initRect.top = viewLength - initArcRadius;
        initRect.right = initRect.left + initArcRadius * 2;
        initRect.bottom = initRect.top + initArcRadius * 2;
        initPaint.setColor(Color.parseColor("#A5D6A7"));
        canvas.drawArc(initRect, -180, 90, true, initPaint);

        Rect iconRect = new Rect();
        iconRect.right = viewLength - initArcRadius * 2 / 10;
        iconRect.bottom = viewLength - initArcRadius * 2 / 10;
        iconRect.left = viewLength - initArcRadius * 7 / 10;
        iconRect.top = viewLength - initArcRadius * 7 / 10;

        if (initIcon != null)
        {
            canvas.drawBitmap(initIcon, null, iconRect, tempPaint);
        }

        if (!TextUtils.isEmpty(message))
        {
            Rect textRect = new Rect();
            textPaint.setTextSize(initArcRadius / 5);
            textPaint.getTextBounds(message, 0, message.length(), textRect);

            tempPaint.setColor(Color.RED);
            canvas.drawCircle(iconRect.right, iconRect.top, textRect.width() * 1.5f, tempPaint);

            float x = iconRect.right - textRect.width() / 2;
            float y = iconRect.top + textRect.height() / 2;
            textPaint.setColor(Color.WHITE);
            canvas.drawText(message, x, y, textPaint);
        }


    }

    private void drawMenu(Canvas canvas)
    {
        RectF proRect = new RectF();
        int proArc = progress;
        proRect.left = viewLength - proArc;
        proRect.top = viewLength - proArc;
        proRect.right = proRect.left + proArc * 2;
        proRect.bottom = proRect.top + proArc * 2;
        canvas.drawArc(proRect, -180, 30, true, menu1Paint);
        canvas.drawArc(proRect, -150, 30, true, menu2Paint);
        canvas.drawArc(proRect, -120, 30, true, menu3Paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                boolean result = checkInInnerCycle(event, initArcRadius);

                if (result)
                {
                    opening(true);
                } else
                {
                    opening(false);
                    hide();
                }

                break;
            case MotionEvent.ACTION_MOVE:

                boolean inCycle = checkInInnerCycle(event, initArcRadiusOpen);

                if (!inCycle && isOpen)
                {
                    int menu = checkWhichMenu(event);

                    this.menu = menu;

                    if (menu == 1)
                    {
                        menu1Paint.setColor(Color.parseColor("#7FC482"));
                        menu2Paint.setColor(Color.parseColor("#6FB172"));
                        menu3Paint.setColor(Color.parseColor("#6FB172"));
                        invalidate();
                    } else if (menu == 2)
                    {
                        menu2Paint.setColor(Color.parseColor("#7FC482"));
                        menu1Paint.setColor(Color.parseColor("#6FB172"));
                        menu3Paint.setColor(Color.parseColor("#6FB172"));
                        invalidate();
                    } else if (menu == 3)
                    {
                        menu3Paint.setColor(Color.parseColor("#7FC482"));
                        menu1Paint.setColor(Color.parseColor("#6FB172"));
                        menu2Paint.setColor(Color.parseColor("#6FB172"));
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                opening(false);

                response();
                break;
        }

        return true;
    }

    private void response()
    {
        if (onMenuListener != null && menu != 0)
        {
            onMenuListener.onSelectMenu(menu);
        }
        menu = 0;
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();

        if(menu1Icon != null && !menu1Icon.isRecycled()) {
            menu1Icon.recycle();
            menu1Icon = null;
        }

        if(menu2Icon != null && !menu2Icon.isRecycled()) {
            menu2Icon.recycle();
            menu2Icon = null;
        }

        if(menu3Icon != null && !menu3Icon.isRecycled()) {
            menu3Icon.recycle();
            menu3Icon = null;
        }

        if(openingIcon != null && !openingIcon.isRecycled()) {
            openingIcon.recycle();
            openingIcon = null;
        }

        if(initIcon != null && !initIcon.isRecycled()) {
            initIcon.recycle();
            initIcon = null;
        }

        if(openingIconDraw != null && !openingIconDraw.isRecycled()) {
            openingIconDraw.recycle();
            openingIconDraw = null;
        }
    }

    private int checkWhichMenu(MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();

        float atan = (viewLength - y) / (viewLength - x);

        double degree = Math.toDegrees(Math.atan(atan));

        if (degree >= 0 && degree < 30)
        {
            return 1;
        } else if (degree >= 30 && degree < 60)
        {
            return 2;
        } else if (degree >= 60 && degree <= 90)
        {
            return 3;
        }
        return 0;
    }

    private boolean checkInInnerCycle(MotionEvent event, int radius)
    {
        float x = event.getX();
        float y = event.getY();

        float X = viewLength;
        float Y = viewLength;

        if ((x - X) * (x - X) + (y - Y) * (y - Y) <= radius * radius)
        {
            return true;
        }
        return false;
    }

    public void hide()
    {
        anim(-viewLength / 10, -viewLength / 10, viewLength, viewLength);
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                handler.sendEmptyMessage(1);
            }
        }, AUTO_SHOW_TIME);
    }

    public void show()
    {
        anim(0, 0,0, 0);
    }

    final ViewPropertyAnimatorListener viewPropertyAnimatorListener = new ViewPropertyAnimatorListener()
    {
        @Override
        public void onAnimationStart(View view)
        {
        }
        @Override
        public void onAnimationEnd(View view)
        {
            isAnimRunning = false;
        }
        @Override
        public void onAnimationCancel(View view)
        {
        }
    };
    public void anim(final int dx1, final int dy1, final int dx2, final int dy2)
    {
        if(isAnimRunning) {
            return;
        }

        ViewCompat.animate(this)
                .translationX(dx1)
                .translationY(dy1)
                .setDuration(150)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new ViewPropertyAnimatorListener()
                {
                    @Override
                    public void onAnimationStart(View view)
                    {
                        isAnimRunning = true;
                    }

                    @Override
                    public void onAnimationEnd(View view)
                    {
                        ViewCompat.animate(ShanXingMenuView.this)
                                .translationX(dx2)
                                .translationY(dy2)
                                .setListener(viewPropertyAnimatorListener)
                                .setInterpolator(new DecelerateInterpolator())
                                .setDuration(850)
                                .start();
                    }

                    @Override
                    public void onAnimationCancel(View view)
                    {

                    }
                })
                .start();

    }

    private void opening(final boolean isOpening)
    {
        opening = isOpening;

        if (opening)
        {
            ThreadManager.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    Paint paint = new Paint();
                    paint.setAntiAlias(true);
                    Matrix matrix = new Matrix();

                    Bitmap bitmap = null;
                    while (opening)
                    {
                        bitmap = Bitmap.createBitmap(openingIcon.getWidth(), openingIcon.getHeight(), openingIcon.getConfig());
                        Canvas canvas = new Canvas(bitmap);

                        degree -= 1;
                        matrix.setRotate(degree % 360, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
                        canvas.drawBitmap(openingIcon, matrix, paint);

                        openingIconDraw = bitmap;

                        postInvalidate();

                        if(!opening) {
                            break;
                        }
                        SystemClock.sleep(200);
                    }

//                    Log.d("my", "前面的循环结束。。。" + System.currentTimeMillis());
                }
            });

            ThreadManager.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    for (int i = progress; i <= radius && opening; )
                    {
                        progress = i += SPREAD_SPEED;

                        progressIcon = (int) (progress * 0.8);
                        // 自定义匀加速
                        SPREAD_SPEED++;

                        postInvalidate();
                        SystemClock.sleep(REFRESH_SPEED);
                    }
                    SPREAD_SPEED = 5;

                    if (progress > radius)
                    {
                        int temp = progressIcon;
                        int max = (int) (temp * 1.2);
                        for(int i = temp; i < max;) {
                            progressIcon = i;
                            postInvalidate();
                            SystemClock.sleep(REFRESH_SPEED);
                            i += SPREAD_SPEED;
                            SPREAD_SPEED++;
                        }
                        for(int i = max; i > temp;) {
                            progressIcon = i;
                            postInvalidate();
                            SystemClock.sleep(REFRESH_SPEED);
                            i -= SPREAD_SPEED;
                            SPREAD_SPEED++;
                        }
                        SPREAD_SPEED = 5;

                        // 菜单完全打开
                        isOpen = true;
                        if(onMenuListener != null) {
                            onMenuListener.onMenuOpen(true);
                        }

                    }
                }
            });

        } else
        {
            isOpen = false;
            if(onMenuListener != null) {
                onMenuListener.onMenuOpen(false);
            }

            menu1Paint.setColor(Color.parseColor("#6FB172"));
            menu2Paint.setColor(Color.parseColor("#6FB172"));
            menu3Paint.setColor(Color.parseColor("#6FB172"));
            ThreadManager.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    for (int i = progress; i >= 0 && !opening; )
                    {
                        progress = i -= SPREAD_SPEED;
                        progressIcon = (int) (progress * 0.8);
                        // 自定义匀加速
                        SPREAD_SPEED++;

                        postInvalidate();
                        SystemClock.sleep(REFRESH_SPEED);
                    }
                    SPREAD_SPEED = 5;
                }
            });
        }
    }

    private void initView(Context context)
    {
        this.context = context;

        initPaint = new Paint();
        initPaint.setAntiAlias(true);
        initPaint.setColor(Color.parseColor("#A5D6A7"));

        menu1Paint = new Paint();
        menu1Paint.setAntiAlias(true);
        menu1Paint.setColor(Color.parseColor("#6FB172"));

        menu2Paint = new Paint();
        menu2Paint.setAntiAlias(true);
        menu2Paint.setColor(Color.parseColor("#6FB172"));

        menu3Paint = new Paint();
        menu3Paint.setAntiAlias(true);
        menu3Paint.setColor(Color.parseColor("#6FB172"));

        textPaint = new Paint();
        textPaint.setAntiAlias(true);

        padding = PixsUtils.dp2px(context, 12);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int mWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int mHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int mHeightSize = MeasureSpec.getSize(heightMeasureSpec);

//        Log.d("my", "widthMeasureSpec = " + MeasureSpec.toString(widthMeasureSpec) + ", heightMeasureSpec = " + MeasureSpec.toString(heightMeasureSpec));

        // 如果明确大小,直接设置大小
        if (mWidthMode == MeasureSpec.EXACTLY)
        {
            mWidth = mWidthSize;
        } else
        {
            // 计算宽度,可以根据实际情况进行计算
            mWidth = (getPaddingLeft() + getPaddingRight());
            // 如果为AT_MOST, 不允许超过默认宽度的大小
            if (mWidthMode == MeasureSpec.AT_MOST)
            {
//                mWidth = Math.min(mWidth, mWidthSize);
                mWidth = DensityUtils.getScreenWidth(context) * 3 / 4;
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
//                mHeight = Math.min(mHeight, mHeightSize);
                mHeight = mWidth;
            }
        }

        viewLength = Math.min(mWidth, mHeight);
        radius = viewLength * 2 / 3;
        initArcRadius = radius / 3;
        initArcRadiusOpen = (int) (initArcRadius * 1.5f);

        setMeasuredDimension(viewLength, viewLength);
    }

    public ShanXingMenuView(Context context)
    {
        super(context);
        initView(context);
    }


    public ShanXingMenuView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        initView(context);
    }

    public ShanXingMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ShanXingMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public String getMenu1Title()
    {
        return menu1Title;
    }

    public void setMenu1Title(String menu1Title)
    {
        this.menu1Title = menu1Title;
    }

    public String getMenu2Title()
    {
        return menu2Title;
    }

    public void setMenu2Title(String menu2Title)
    {
        this.menu2Title = menu2Title;
    }

    public String getMenu3Title()
    {
        return menu3Title;
    }

    public void setMenu3Title(String menu3Title)
    {
        this.menu3Title = menu3Title;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getMenu3Message()
    {
        return menu3Message;
    }

    public Bitmap getInitIcon()
    {
        return initIcon;
    }

    public void setInitIcon(Bitmap initIcon)
    {
        this.initIcon = initIcon;
    }

    public Bitmap getOpeningIcon()
    {
        return openingIcon;
    }

    public void setOpeningIcon(Bitmap openingIcon)
    {
        this.openingIcon = openingIcon;
    }

    public Bitmap getMenu1Icon()
    {
        return menu1Icon;
    }

    public void setMenu1Icon(Bitmap menu1Icon)
    {
        this.menu1Icon = menu1Icon;
    }

    public Bitmap getMenu2Icon()
    {
        return menu2Icon;
    }

    public void setMenu2Icon(Bitmap menu2Icon)
    {
        this.menu2Icon = menu2Icon;
    }

    public Bitmap getMenu3Icon()
    {
        return menu3Icon;
    }

    public void setMenu3Icon(Bitmap menu3Icon)
    {
        this.menu3Icon = menu3Icon;
    }

    public void setMenu3Message(String menu3Message)
    {
        this.menu3Message = menu3Message;
    }

    public OnMenuListener getOnMenuListener()
    {
        return onMenuListener;
    }

    public void setOnMenuListener(OnMenuListener onMenuListener)
    {
        this.onMenuListener = onMenuListener;
    }
}
