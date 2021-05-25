package com.tl.androidnativedemo.navigation72.procicle;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by tianlin on 2018/9/11.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class LatLng {
    public final double latitude;
    public final double longitude;
    private static DecimalFormat a;

    static {
        a = new DecimalFormat("0.000000", new DecimalFormatSymbols(Locale.US));
    }

    private static double a(double var0) {
        try {
            return Double.parseDouble(a.format(var0));
        } catch (Throwable var3) {
            var3.printStackTrace();
            return var0;
        }
    }

    public LatLng(double var1, double var3) {
        this(var1, var3, true);
    }
    public LatLng(double var1, double var3, boolean var5) {
        if (var5) {
            if (-180.0D <= var3 && var3 < 180.0D) {
                this.longitude = a(var3);
            } else {
                this.longitude = a(((var3 - 180.0D) % 360.0D + 360.0D) % 360.0D - 180.0D);
            }

            if (var1 < -90.0D || var1 > 90.0D) {
                try {
                    throw new AMapException("非法坐标值");
                } catch (AMapException var7) {
                    var7.printStackTrace();
                }
            }

            this.latitude = a(Math.max(-90.0D, Math.min(90.0D, var1)));
        } else {
            this.latitude = var1;
            this.longitude = var3;
        }

    }

    public boolean equals(Object var1) {
        if (this == var1) {
            return true;
        } else if (!(var1 instanceof LatLng)) {
            return false;
        } else {
            LatLng var2 = (LatLng)var1;
            return Double.doubleToLongBits(this.latitude) == Double.doubleToLongBits(var2.latitude) && Double.doubleToLongBits(this.longitude) == Double.doubleToLongBits(var2.longitude);
        }
    }

    public String toString() {
        return "lat/lng: (" + this.latitude + "," + this.longitude + ")";
    }
}
