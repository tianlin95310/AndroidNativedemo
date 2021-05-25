package com.tl.androidnativedemo.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianlin on 2018/4/27.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class ActivityList
{
    private static List<Activity> activities = new ArrayList<>();

    public static List<Activity> getActivities()
    {
        return activities;
    }

    public static void add(Activity activity)
    {
        activities.add(activity);
    }

    public static void clear() {
        activities.clear();
    }
}
