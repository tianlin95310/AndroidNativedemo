package com.tl.androidnativedemo.utils.mathabout;

import android.graphics.PointF;

/**
 * Created by tianlin on 2018/9/8.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class MathProblemUtils {


    /**
     * 给出三点求圆心和半径
     *
     * @param pt1
     * @param pt2
     * @param pt3
     * @return
     */
    public static CycleInfo findCircle1(PointF pt1, PointF pt2, PointF pt3) {
        // 定义两个点，分别表示两个中点
        PointF midpt1 = new PointF();
        PointF midpt2 = new PointF();

        // 求出点1和点2的中点
        midpt1.x = (pt2.x + pt1.x) / 2;
        midpt1.y = (pt2.y + pt1.y) / 2;
        // 求出点3和点1的中点
        midpt2.x = (pt3.x + pt1.x) / 2;
        midpt2.y = (pt3.y + pt1.y) / 2;
        // 求出分别与直线pt1pt2，pt1pt3垂直的直线的斜率
        float k1 = -(pt2.x - pt1.x) / (pt2.y - pt1.y);
        float k2 = -(pt3.x - pt1.x) / (pt3.y - pt1.y);

        // 然后求出过中点midpt1，斜率为k1的直线方程（既pt1pt2的中垂线）：y - midPt1.y = k1( x - midPt1.x)
        // 以及过中点midpt2，斜率为k2的直线方程（既pt1pt3的中垂线）：y - midPt2.y = k2( x - midPt2.x)
        // 定义一个圆的数据的结构体对象CD
        CycleInfo CD = new CycleInfo();
        // 连立两条中垂线方程求解交点得到：
        CD.center.x = (midpt2.y - midpt1.y - k2 * midpt2.x + k1 * midpt1.x) / (k1 - k2);
        CD.center.y = midpt1.y + k1 * (midpt2.y - midpt1.y - k2 * midpt2.x + k2 * midpt1.x) / (k1 - k2);
        // 用圆心和其中一个点求距离得到半径：
        CD.radius = (float) Math.sqrt((CD.center.x - pt1.x) * (CD.center.x - pt1.x) + (CD.center.y - pt1.y) * (CD.center.y - pt1.y));
        return CD;
    }

    /**
     * 给出三点求圆心和半径
     *
     * @param pt1
     * @param pt2
     * @param pt3
     * @return
     */
    public static CycleInfo findCircle2(PointF pt1, PointF pt2, PointF pt3) {
        // A1 = 2 * pt2.x - 2 * pt1.x      B1 = 2 * pt1.y - 2 * pt2.y       C1 = pt1.y² + pt2.x² - pt1.x² - pt2.y²
        // A2 = 2 * pt3.x - 2 * pt2.x      B2 = 2 * pt2.y - 2 * pt3.y       C2 = pt2.y² + pt3.x² - pt2.x² - pt3.y²
        float A1, A2, B1, B2, C1, C2, temp;
        A1 = pt1.x - pt2.x;
        B1 = pt1.y - pt2.y;
        C1 = (float) ((Math.pow(pt1.x, 2) - Math.pow(pt2.x, 2) + Math.pow(pt1.y, 2) - Math.pow(pt2.y, 2)) / 2);
        A2 = pt3.x - pt2.x;
        B2 = pt3.y - pt2.y;
        C2 = (float) ((Math.pow(pt3.x, 2) - Math.pow(pt2.x, 2) + Math.pow(pt3.y, 2) - Math.pow(pt2.y, 2)) / 2);
        // 为了方便编写程序，令temp = A1*B2 - A2*B1
        temp = A1 * B2 - A2 * B1;

        CycleInfo cycleInfo = new CycleInfo();

        if (temp == 0) {
            // 共线则将第一个点pt1作为圆心
            cycleInfo.center.x = pt1.x;
            cycleInfo.center.y = pt1.y;
        } else {
            //不共线则求出圆心：
            cycleInfo.center.x = (C1 * B2 - C2 * B1) / temp;
            cycleInfo.center.y = (A1 * C2 - A2 * C1) / temp;
        }
        cycleInfo.radius = (float) Math.sqrt((cycleInfo.center.x - pt1.x) * (cycleInfo.center.x - pt1.x) +
                (cycleInfo.center.y - pt1.y) * (cycleInfo.center.y - pt1.y));

        return cycleInfo;
    }

    public static class CycleInfo {

        public PointF center = new PointF();
        public float radius;
    }

}
