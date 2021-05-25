package com.tl.androidnativedemo.navigation71.menu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2018/5/24.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class ShanXingMenuActivity extends BaseActivity implements OnMenuListener
{
    @BindView(R.id.sh_menu)
    ShanXingMenuView shMenu;
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    @BindView(R.id.ll_bg_layer)
    LinearLayout llBgLayer;
    @BindView(R.id.tv1)
    TextView tv1;

    private Bitmap bitmap;

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            Log.d("my", "msg.what = " + msg.what);
            if (msg.what == 1)
            {
                Bitmap blur = (Bitmap) msg.obj;
                if (blur != null && !blur.isRecycled())
                {
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), blur);
                    llBgLayer.setBackground(bitmapDrawable);
                }
            } else if (msg.what == 2)
            {
                llBgLayer.setBackground(null);
            }
        }
    };

    @Override
    public void initView()
    {
        Bitmap initIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_message);
        Bitmap openingIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_opening);
        Bitmap menu1Icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_history_record);
        Bitmap menu2Icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_copy);
        Bitmap menu3Icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_peisong);
        shMenu.setInitIcon(initIcon);
        shMenu.setOpeningIcon(openingIcon);
        shMenu.setMenu1Icon(menu1Icon);
        shMenu.setMenu2Icon(menu2Icon);
        shMenu.setMenu3Icon(menu3Icon);

        shMenu.setMenu1Title("历史订单");
        shMenu.setMenu2Title("复制当日订单");
        shMenu.setMenu3Title("未配送订单");
        shMenu.setMessage("8");
        shMenu.setMenu3Message("5");

        shMenu.setOnMenuListener(this);

        nsv.setDrawingCacheEnabled(true);
    }

    @Override
    public void onSelectMenu(int menu)
    {
        response(this, "选择的功能是" + menu);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (bitmap != null && !bitmap.isRecycled())
        {
            bitmap.recycle();
            bitmap = null;
        }
    }

    @Override
    public void onMenuOpen(boolean isOpen)
    {
        if (isOpen)
        {
            bitmap = nsv.getDrawingCache(true);
            float radius = 25;

            if (bitmap != null && !bitmap.isRecycled())
            {
                Bitmap blur = blurBitmap(bitmap, radius);
                Message message = handler.obtainMessage();
                message.what = 1;
                message.obj = blur;
                handler.sendMessage(message);
            }
        } else
        {
            handler.sendEmptyMessage(2);
        }
    }

    public Bitmap blurBitmap(Bitmap bitmap, float radius)
    {
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        RenderScript rs = RenderScript.create(getApplicationContext());

        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        //Set the radius of the blur: 0 < radius <= 25
        blurScript.setRadius(radius);

        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        allOut.copyTo(outBitmap);

        //recycle the original bitmap
//        bitmap.recycle();

        rs.destroy();

        return outBitmap;

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shanxing_menu);
        ButterKnife.bind(this);

        initView();
    }

    @OnClick(R.id.nsv)
    public void onViewClicked()
    {
        shMenu.hide();
    }

    @OnClick(R.id.tv1)
    public void tv1()
    {
        response(this, "tv1我被点击了");
    }
}
