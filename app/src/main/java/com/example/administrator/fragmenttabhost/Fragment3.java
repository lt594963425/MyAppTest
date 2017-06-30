package com.example.administrator.fragmenttabhost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Carson_Ho on 16/5/23.
 */
public class Fragment3 extends Fragment {
    String url = "http://www.baidu.com/";

 private WebView webView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
        savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item2, null);
            webView = (WebView) view.findViewById(R.id.webView);
            WebSettings settings = webView.getSettings();
            webView.loadUrl(url);
            settings.setJavaScriptEnabled(true);
           // webView.loadUrl("http://baidu.com");
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
        return view;
    }
}
