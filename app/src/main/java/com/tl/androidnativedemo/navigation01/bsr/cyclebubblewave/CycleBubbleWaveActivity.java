package com.tl.androidnativedemo.navigation01.bsr.cyclebubblewave;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.annotation.Nullable;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2018/7/24.
 * Tel : 15071485690
 * QQ : 953108373
 */
public class CycleBubbleWaveActivity extends BaseActivity {
    @BindView(R.id.cbw)
    CycleBubbleWave cbw;
    @BindView(R.id.sb_progress)
    SeekBar sbProgress;
    @BindView(R.id.sb_amplitude)
    SeekBar sbAmplitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_buble_wave);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    public void initView() {

        sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                cbw.setDegree(seekBar.getProgress());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbAmplitude.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cbw.setmWaveAmplitude(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
