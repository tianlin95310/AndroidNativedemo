package com.tl.androidnativedemo.navigation21.rvpage;

import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianlin on 2018/7/11.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class FakeDataUtils {

    static List<DataBean> list = new ArrayList<>();
    static {
        for(int i = 0; i < 100; i++) {
            DataBean dataBean = new DataBean();
            dataBean.id = i;
            dataBean.content = "content---" + i;
            list.add(dataBean);
        }
    }

    public static List<DataBean> loadInitData(int initSize) {
//        return new ArrayList<>();
        Log.d("my", "loadInitData initSize = " + initSize);
        SystemClock.sleep(1000);
        return list.subList(0, 1);
    }

    public static List<DataBean> loadPageData(int page, int size) {

        SystemClock.sleep(2000);
        Log.d("my", "loadPageData加载 页数 = " + page);

//        如果后来的某页出现返回0的情况，就不往下加载了
//        size = 1;
        int totalPage = list.size() % size == 0 ? list.size() / size : list.size() / size + 1;

         // 如果后来的某页出现返回0的情况，就不往下加载了
//        if(page == 4 || page == 5) {
//            List<DataBean> dataBeans = list.subList((page - 1) * size, page * size);
//            for(DataBean dataBean : dataBeans) {
//                dataBean.isShow = false;
//            }
//        }

        if(page < totalPage) {
            return list.subList((page - 1) * size, page * size);
        }
        else if(page == totalPage){
            return list.subList((page - 1) * size, list.size());
        }
        else {
            return new ArrayList<>();
        }


    }
}
