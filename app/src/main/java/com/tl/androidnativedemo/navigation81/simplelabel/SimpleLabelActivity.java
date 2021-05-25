package com.tl.androidnativedemo.navigation81.simplelabel;

import android.os.Bundle;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.tl.androidnativedemo.R;
import com.tl.androidnativedemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2017/7/17.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class SimpleLabelActivity extends BaseActivity
{

//    http://10.208.61.215:8080/005_H5Learning/01_simple_label.jsp
    private static final String BASE_URL = "http://10.208.61.124:8080/005_H5Learning/";
    private static final String PAGE_URL = BASE_URL + "01_simple_label.jsp";
    @BindView(R.id.wv_simple_label)
    WebView wvSimpleLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simlpe_label);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    public void initView()
    {

        wvSimpleLabel.getSettings().setJavaScriptEnabled(true);
        wvSimpleLabel.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result)
            {
                Log.d("my", "url = " + url);
                Log.d("my", "message = " + message);
                result.confirm();
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result)
            {
                Log.d("my", "url = " + url);
                Log.d("my", "message = " + message);
                return super.onJsConfirm(view, url, message, result);
            }
        });
        wvSimpleLabel.setWebViewClient(new WebViewClient(){
        });

        wvSimpleLabel.loadUrl(PAGE_URL);
    }
}
