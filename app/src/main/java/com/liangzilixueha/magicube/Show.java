package com.liangzilixueha.magicube;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.liangzilixueha.magicube.databinding.ActivityShowBinding;

public class Show extends AppCompatActivity {

    private ActivityShowBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        binding = ActivityShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        WebView web = new WebView(this);
        //加载assets目录下的html文件
        web.loadUrl("https://www.baidu.com");
        //初始化webview
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setAllowFileAccess(true);
        web.getSettings().setAllowFileAccessFromFileURLs(true);
        web.getSettings().setAllowUniversalAccessFromFileURLs(true);
        setContentView(web);

    }

    @Override
    protected void onDestroy() {
        Log.e("csw", "onDestroy: " );
        super.onDestroy();
    }
}