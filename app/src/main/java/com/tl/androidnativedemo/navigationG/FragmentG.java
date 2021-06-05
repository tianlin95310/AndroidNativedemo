package com.tl.androidnativedemo.navigationG;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.navigationG.fivecheese.view.MenuActivity;
import com.tl.androidnativedemo.navigationG.jinzita.JinZiTaPukeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by tianlin on 2018/11/26.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class FragmentG extends Fragment {
    @BindView(R.id.jinzita)
    Button jinzita;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_g, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.jinzita)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), JinZiTaPukeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.gobang)
    public void gobang() {
        Intent intent = new Intent(getContext(), MenuActivity.class);
        startActivity(intent);
    }
}
