package com.tl.androidnativedemo.utils.device;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.net.wifi.aware.WifiAwareManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

/**
 * Created by tianlin on 2018/12/21.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class NetWorkState {
    public static void getCurrentNet (Context context) {
        if (context == null) {
            return;
        }
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        Log.d("my", "getCurrentNet");
    }

    public static void getCurrentWifi (Context context) {
        if (context == null) {
            return;
        }
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        Log.d("my", "getCurrentWifi");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void getCurrentWifiAware (Context context) {
        if (context == null) {
            return;
        }
        WifiAwareManager manager = (WifiAwareManager) context.getSystemService(Context.WIFI_AWARE_SERVICE);
        Log.d("my", "getCurrentWifiAware");
    }

    public static void getCurrentWifiP2p (Context context) {
        if (context == null) {
            return;
        }
        WifiP2pManager manager = (WifiP2pManager) context.getSystemService(Context.WIFI_P2P_SERVICE);
        Log.d("my", "getCurrentWifiP2p");
    }
}
