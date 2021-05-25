package com.tl.androidnativedemo.navigation31;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.navigation31.inputlimit.InputLimitActivity;
import com.tl.androidnativedemo.navigation31.parandchild.ParentAndChildActivity;
import com.tl.androidnativedemo.navigation31.recyclereuse.RecyclerReuseActivity;
import com.tl.androidnativedemo.navigation31.transition.ActivityTransition;
import com.tl.androidnativedemo.navigation31.transition.ActivityViewTransition;
import com.tl.androidnativedemo.navigation31.viewanim.ViewAnimActivity;
import com.tl.androidnativedemo.utils.display.DensityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by tianlin on 2017/3/18.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class Fragment31 extends Fragment {

    @BindView(R.id.bt_input_limit)
    Button btInputLimit;
    @BindView(R.id.bt_recycler_reuse)
    Button btRecyclerReuse;
    Unbinder unbinder;
    @BindView(R.id.bt_parent_child)
    Button btParentChild;
    @BindView(R.id.bt_view_anim)
    Button btViewAnim;
    @BindView(R.id.bt_view_anim_trans1)
    FloatingActionButton btViewAnimTrans1;
    @BindView(R.id.bt_view_anim_createCircularReveal)
    Button btViewAnimCreateCircularReveal;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.bt_view_start_and_finish)
    Button btViewStartAndFinish;
    private boolean show = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment31, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
    }

    @OnClick({R.id.bt_input_limit, R.id.bt_recycler_reuse, R.id.bt_parent_child, R.id.bt_view_anim})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.bt_input_limit:
                intent = new Intent(getActivity(), InputLimitActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_recycler_reuse:
                intent = new Intent(getActivity(), RecyclerReuseActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_parent_child:
                intent = new Intent(getActivity(), ParentAndChildActivity.class);
                startActivity(intent);
                break;

            case R.id.bt_view_anim:
                intent = new Intent(getActivity(), ViewAnimActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onResume() {
        super.onResume();

        if (show) {
            btViewAnimTrans1.setVisibility(View.VISIBLE);

        } else {
            btViewAnimTrans1.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.bt_view_anim_trans1)
    public void bt_view_anim_trans1() {

        show = false;

        String transitionName = ViewCompat.getTransitionName(btViewAnimTrans1) == null ? "" : ViewCompat.getTransitionName(btViewAnimTrans1);
        Log.d("my", "transitionName = " + transitionName);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(), btViewAnimTrans1, transitionName);

        Intent intent = new Intent(getActivity(), ActivityViewTransition.class);
        startActivity(intent, options.toBundle());

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_view_anim_createCircularReveal)
    public void bt_view_anim_createCircularReveal() {

        int cx = (btViewAnimCreateCircularReveal.getLeft() + btViewAnimCreateCircularReveal.getRight()) / 2;
        int cy = (btViewAnimCreateCircularReveal.getTop() + btViewAnimCreateCircularReveal.getBottom()) / 2;

        Animator anim = ViewAnimationUtils.createCircularReveal(scrollView,
                cx,
                cy,
                0,
                DensityUtils.getScreenHeight(getActivity()));
        anim.setDuration(500);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });

        anim.start();
    }

    @OnClick(R.id.bt_view_start_and_finish)
    public void bt_view_start_and_finish() {

        ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(getActivity(),
                R.anim.activity_tran_in,
                R.anim.activity_out);
        Intent intent = new Intent(getActivity(), ActivityTransition.class);
        startActivity(intent, compat.toBundle());

    }



}
