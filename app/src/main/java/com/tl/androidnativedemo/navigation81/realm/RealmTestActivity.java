package com.tl.androidnativedemo.navigation81.realm;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/11/4.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class RealmTestActivity extends BaseActivity
{

    @BindView(R.id.main_thread_save)
    Button mainThreadSave;
    @BindView(R.id.io_thread_read)
    Button ioThreadRead;
    @BindView(R.id.io_thread_read_once)
    Button ioThreadReadOnce;
    @BindView(R.id.test_not_copy)
    Button testNotCopy;
    @BindView(R.id.test_not_copy2)
    Button testNotCopy2;
    @BindView(R.id.bt_diff_thread_async)
    Button btDiffThreadAsync;

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
//            List<DiffThreadReadAndWrite> list4 = DiffThreadReadAndWriteQuery.findAll();
//            Log.d("my", "list4.size = " + list4.size());
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm_test);

        ButterKnife.bind(this);
    }

    @Override
    public void initView()
    {

    }

    @OnClick(R.id.io_thread_read_once)
    public void io_thread_read_once()
    {
//        Realm realm = Realm.getDefaultInstance();
//
//        RealmResults results = realm.where(DiffThreadReadAndWrite.class).findAll();
//
//        List<DiffThreadReadAndWrite> copy = realm.copyFromRealm(results);
//
//        Log.d("my", "copy = " + copy.toString());
    }

    @OnClick(R.id.test_not_copy2)
    public void test_not_copy2()
    {
//        new Thread()
//        {
//            @Override
//            public void run()
//            {
//                Realm realm = Realm.getDefaultInstance();
//
//                final DiffThreadReadAndWrite diffThreadReadAndWrite = realm.where(DiffThreadReadAndWrite.class).findAll().first();
//
//                Log.d("my", "diffThreadReadAndWrite valid = " + diffThreadReadAndWrite.isValid());
//
//                realm.executeTransaction(new Realm.Transaction()
//                {
//                    @Override
//                    public void execute(Realm realm)
//                    {
//                        realm = null;
//
//                        SystemClock.sleep(8000);
//
//                        diffThreadReadAndWrite.setName("newName");
//                    }
//                });
//
//                // 此时发现diffThreadReadAndWrite对象还是有效的
//                Log.d("my", "diffThreadReadAndWrite valid = " + diffThreadReadAndWrite.isValid());
//            }
//        }.start();
    }

    @OnClick(R.id.test_not_copy)
    public void test_not_copy()
    {
//        Realm realm = Realm.getDefaultInstance();
//
//        DiffThreadReadAndWrite diffThreadReadAndWrite = realm.where(DiffThreadReadAndWrite.class).findAll().first();
//
//        Log.d("my", "diffThreadReadAndWrite valid = " + diffThreadReadAndWrite.isValid());
//
//        realm.close();
//
//        Log.d("my", "diffThreadReadAndWrite valid = " + diffThreadReadAndWrite.isValid());
    }

    @OnClick(R.id.io_thread_read)
    public void io_thread_read()
    {
//        new Thread()
//        {
//            @Override
//            public void run()
//            {
//
//                // 一个存在很久的realm实例
//                Realm realm = Realm.getDefaultInstance();
//
//                while (true)
//                {
//
//                    // 刷新一下数据流，让主线程新添加的数据，子线程能获取到
//                    realm.refresh();
//
//                    RealmResults results = realm.where(DiffThreadReadAndWrite.class).findAll();
//
//                    List<DiffThreadReadAndWrite> copy = realm.copyFromRealm(results);
//
//                    Log.d("my", "copy = " + copy.toString());
//
//                    SystemClock.sleep(5000);
//                }
//
//            }
//        }.start();
    }

    @OnClick(R.id.main_thread_save)
    public void main_thread_save()
    {
//        Realm realm = Realm.getDefaultInstance();
//        try
//        {
//            realm.executeTransaction(new Realm.Transaction()
//            {
//                @Override
//                public void execute(Realm realm)
//                {
//                    DiffThreadReadAndWrite diffThreadReadAndWrite = new DiffThreadReadAndWrite();
//                    diffThreadReadAndWrite.setId("1");
//                    diffThreadReadAndWrite.setName("a");
//
//                    realm.copyToRealm(diffThreadReadAndWrite);
//
//                }
//            });
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        } finally
//        {
//            if (realm != null && !realm.isClosed())
//                realm.close();
//        }
    }

    @OnClick(R.id.bt_diff_thread_async)
    public void bt_diff_thread_async()
    {
//
//        new Thread(){
//            @Override
//            public void run()
//            {
//                List<DiffThreadReadAndWrite> list1 = DiffThreadReadAndWriteQuery.findAll();
//                Log.d("my", "list1.size = " + list1.size());
//
//                DiffThreadReadAndWriteQuery.clearAll();
//
//                List<DiffThreadReadAndWrite> list2 = DiffThreadReadAndWriteQuery.findAll();
//                Log.d("my", "list2.size = " + list2.size());
//
//                DiffThreadReadAndWrite diffThreadReadAndWrite = new DiffThreadReadAndWrite();
//                diffThreadReadAndWrite.setId("2");
//                diffThreadReadAndWrite.setName("b");
//                DiffThreadReadAndWriteQuery.save(diffThreadReadAndWrite);
//
//                List<DiffThreadReadAndWrite> list3 = DiffThreadReadAndWriteQuery.findAll();
//                Log.d("my", "list3.size = " + list3.size());
//
//                handler.sendEmptyMessage(1);
//            }
//        }.start();


    }
}
