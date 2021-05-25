package com.tl.androidnativedemo.navigation11;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.navigation11.gridrecycler.GridRecyclerActivity;
import com.tl.androidnativedemo.navigation11.longpressd.LongPressedMoveActivity;
import com.tl.androidnativedemo.navigation11.slidedelete.SlideDeleteActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by tianlin on 2017/7/2.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class Fragment11 extends Fragment
{
    Unbinder unbinder;
    @BindView(R.id.bt_long_pressed_move)
    Button btLongPressedMove;
    @BindView(R.id.bt_slide_del)
    Button btSlideDel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment11, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView()
    {

    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bt_long_pressed_move, R.id.bt_slide_del})
    public void onViewClicked(View view)
    {
        Intent intent = null;

        switch (view.getId())
        {
            case R.id.bt_long_pressed_move:
                intent = new Intent(getActivity(), LongPressedMoveActivity.class);
                break;
            case R.id.bt_slide_del:
                intent = new Intent(getActivity(), SlideDeleteActivity.class);
                break;
        }

        startActivity(intent);
    }

    @OnClick(R.id.bt_3)
    public void bt_3()
    {
        Intent intent = new Intent(getActivity(), GridRecyclerActivity.class);
        startActivity(intent);
    }
}
