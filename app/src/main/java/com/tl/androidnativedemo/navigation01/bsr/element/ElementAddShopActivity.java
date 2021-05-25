package com.tl.androidnativedemo.navigation01.bsr.element;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;
import com.tl.androidnativedemo.base.BaseVo;
import com.tl.androidnativedemo.utils.display.PixsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2018/2/8.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class ElementAddShopActivity extends BaseActivity implements BeiSaiErAdapter.OnItemClickListener
{
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.bt_end)
    Button bt_end;
    @BindView(R.id.bt_beisaier)
    Button btBeisaier;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bei_sai_er_element);
        ButterKnife.bind(this);
        initView();
    }


    class MyTypeEvaluator implements TypeEvaluator<Point>
    {
        private Point controlPoint;

        public MyTypeEvaluator(Point controlPoint) {
            this.controlPoint = controlPoint;
        }

        @Override
        public Point evaluate(float t, Point startValue, Point endValue)
        {

            Log.d("my", "evaluate t = " + t);
            int x = (int) ((1 - t) * (1 - t) * startValue.x + 2 * t * (1 - t) * controlPoint.x + t * t * endValue.x);
            int y = (int) ((1 - t) * (1 - t) * startValue.y + 2 * t * (1 - t) * controlPoint.y + t * t * endValue.y);
            return new Point(x, y);
        }
    }
    @Override
    public void onItemClick(View itemBt)
    {

        btBeisaier.setVisibility(View.VISIBLE);
        int []recycler = new int[2];
        recyclerView.getLocationOnScreen(recycler);
        Log.d("my", "---状态栏加上标题栏---" + recycler[1]);

        int position[] = new int[2];
        itemBt.getLocationOnScreen(position);
        Point start = new Point();
        start.x = position[0];
        start.y = position[1] - recycler[1];
        Log.d("my", "---start---" + start.toString());

        int endPositions[] = new int[2];
        bt_end.getLocationOnScreen(endPositions);
        Point end = new Point();
        end.x = endPositions[0];
        end.y = endPositions[1] - recycler[1];
        Log.d("my", "---end---" + end.toString());

        int pointX = (start.x + end.x) / 2;
        int pointY = (start.y - PixsUtils.dp2px(this, 100));
        final Point controlPoint = new Point(pointX, pointY);
        Log.d("my", "---controlPoint---" + controlPoint.toString());

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new MyTypeEvaluator(controlPoint), start, end);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                Point position = (Point) animation.getAnimatedValue();

                Log.d("my", "onAnimationUpdate position = " + position.toString());
                btBeisaier.setX(position.x);
                btBeisaier.setY(position.y);

            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                btBeisaier.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationCancel(Animator animation)
            {
                btBeisaier.setVisibility(View.GONE);
            }
        });

        valueAnimator.setDuration(1000);
        valueAnimator.start();
    }

    @Override
    public void initView()
    {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<BaseVo> baseVos = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            baseVos.add(new BeiSaierVo("name" + i));
        }

        BeiSaiErAdapter beiSaiErAdapter = new BeiSaiErAdapter(baseVos, this);
        beiSaiErAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(beiSaiErAdapter);

    }
}
