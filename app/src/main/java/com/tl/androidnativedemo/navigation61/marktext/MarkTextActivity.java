package com.tl.androidnativedemo.navigation61.marktext;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/10/25.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class MarkTextActivity extends BaseActivity
{
    @BindView(R.id.text_content)
    TextView textContent;

    String key = "使用";

    String content = "Android是一种基于Linux的自由及开放源代码的操作系统，主要使用于移动设备，如智能手机和平板电脑，由Google公司和开放手机联盟领导及开发。尚未有统一中文名称，中国大陆地区较多人使用“安卓”或“安致”。Android操作系统最初由Andy Rubin开发，主要支持手机。2005年8月由Google收购注资。2007年11月，Google与84家硬件制造商、软件开发商及电信营运商组建开放手机联盟共同研发改良Android系统。随后Google以Apache开源许可证的授权方式，发布了Android的源代码。第一部Android智能手机发布于2008年10月。Android逐渐扩展到平板电脑及其他领域上，如电视、数码相机、游戏机等。2011年第一季度，Android在全球的市场份额首次超过塞班系统，跃居全球第一。 2013年的第四季度，Android平台手机的全球市场份额已经达到78.1%。[1]  2013年09月24日谷歌开发的操作系统Android在迎来了5岁生日，全世界采用这款系统的设备数量已经达到10亿台。Android是一种基于Linux的自由及开放源代码的操作系统，主要使用于移动设备，如智能手机和平板电脑，由Google公司和开放手机联盟领导及开发。尚未有统一中文名称，中国大陆地区较多人使用“安卓”或“安致”。Android操作系统最初由Andy Rubin开发，主要支持手机。2005年8月由Google收购注资。2007年11月，Google与84家硬件制造商、软件开发商及电信营运商组建开放手机联盟共同研发改良Android系统。随后Google以Apache开源许可证的授权方式，发布了Android的源代码。第一部Android智能手机发布于2008年10月。Android逐渐扩展到平板电脑及其他领域上，如电视、数码相机、游戏机等。2011年第一季度，Android在全球的市场份额首次超过塞班系统，跃居全球第一。 2013年的第四季度，Android平台手机的全球市场份额已经达到78.1%。[1]  2013年09月24日谷歌开发的操作系统Android在迎来了5岁生日，全世界采用这款系统的设备数量已经达到10亿台。";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mark_text);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    public void initView()
    {
        textContent.setText(content);

    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     *
     * data contains result
     * result is a hashMap
     * key  :   value
     * status: 'cancel' 取消支付
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("my", "requestCode = " + requestCode + ", resultCode = " + resultCode);
    }

    @OnClick(R.id.bt_begin)
    public void onViewClicked()
    {
        String[] strs = content.split(key);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

        for (int i = 0; i < strs.length; i++)
        {

            String str = strs[i];
            spannableStringBuilder.append(str);

            if (i == strs.length - 1)
                continue;

            SpannableString spannedString = new SpannableString(key);
            CharacterStyle span = new BackgroundColorSpan(Color.GREEN);
            spannedString.setSpan(span, 0, key.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.append(spannedString);

        }

        textContent.setText(spannableStringBuilder);
    }
}
