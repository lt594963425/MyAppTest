package com.example.administrator.fragmenttabhost;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.administrator.utils.WeiboDialogUtils;

import static com.example.administrator.utils.DialogThridUtils.closeDialog;

/**
 * Created by Carson_Ho on 16/5/23.
 */
public class Fragment2 extends Fragment {
    String url = "http://www.baidu.com/";
    private AlertDialog alertDialog;
    private ProgressDialog progressBar = null;
    private WebView webView;
    private Dialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item2, null);
        webView = (WebView) view.findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        initWebview();
        return view;
    }

    private void initWebview() {
        mDialog = WeiboDialogUtils.createLoadingDialog(getActivity(),"加载中");
        //设计进度条
        //progressBar = ProgressDialog.show(getActivity(), null, "正在进入网页，请稍后…");
        WebSettings settings = webView.getSettings();
        webView.loadUrl(url);
        settings.setJavaScriptEnabled(true);
        // webView.loadUrl("http://baidu.com");
        // alertDialog = new AlertDialog.Builder(getActivity()).create();

        webView.setWebViewClient(new MyWebViewClient());

    }


    class MyWebViewClient extends android.webkit.WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (mDialog .isShowing()) {
                closeDialog(mDialog);
            }
//            if (progressBar.isShowing()) {
//                progressBar.dismiss();
//            }
        }


    }
}