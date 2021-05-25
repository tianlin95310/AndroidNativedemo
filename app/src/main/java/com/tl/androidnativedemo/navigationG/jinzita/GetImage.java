package com.tl.androidnativedemo.navigationG.jinzita;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianlin on 2018/12/13.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class GetImage {
    public static List<BitmapInfo> pukes = new ArrayList<>(52);

    public static Bitmap getBitmap(PukeInfo pukeInfo) {
        for (BitmapInfo bitmapInfo : pukes) {
            if (bitmapInfo.value == pukeInfo.value && bitmapInfo.color == pukeInfo.color) {
                return bitmapInfo.bitmap;
            }
        }
        return null;
    }
    public static void loadAssets (Context context) {
        pukes = new ArrayList<>(52);

        for (int i = 1 ;i <= 13; i++) {
            for (int j = 1 ;j <= 4; j++) {
                Bitmap bitmap = getImageInSd(i ,j);
                if (bitmap == null) {
                    bitmap = getImageInAsset(context, i, j);
                }
                BitmapInfo bitmapInfo = new BitmapInfo();
                bitmapInfo.value = i;
                bitmapInfo.color = j;
                bitmapInfo.bitmap = bitmap;
                pukes.add(bitmapInfo);
            }
        }
    }
    public static File dir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "images");
    public static Bitmap getImageInSd(int value, int color) {
        if (!dir.exists()) {
            return null;
        }
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            bitmap = BitmapFactory.decodeFile(dir.getAbsolutePath() + "/" + value + color + ".png", options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public static Bitmap getImageInAsset(Context context, int value, int color) {
        Bitmap bitmap = null;
        try {
            InputStream stream = context.getAssets().open("pukes/" + value + color + ".png");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            bitmap = BitmapFactory.decodeStream(stream, null, options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
