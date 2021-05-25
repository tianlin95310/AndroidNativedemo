package com.tl.androidnativedemo.navigation71.check;

import com.tl.androidnativedemo.base.BasePresenter;


/**
 * Created by tianlin on 2017/7/10.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class CheckControl extends BasePresenter<ICheckView>
{

    @Override
    public void attachView(ICheckView view)
    {
        super.attachView(view);
    }

    @Override
    public void initData()
    {

        CheckItemTaskVo checkItemTaskVo = new CheckItemTaskVo();
        checkItemTaskVo.text = "网络连接";
        view.addTask(checkItemTaskVo);
        networkCheck.start();
    }

    private Thread networkCheck = new Thread(){
        @Override
        public void run()
        {

        }
    };
}
