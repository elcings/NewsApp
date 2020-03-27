package com.elchinaliyev.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import dmax.dialog.SpotsDialog;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    WebView webView;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();
        dialog.show();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("Finish1",url);
                dialog.dismiss();
            }
        });
        if(getIntent()!=null) {
            if(!getIntent().getStringExtra("webUrl").isEmpty()) {
                webView.loadUrl(getIntent().getStringExtra("webUrl"));
            }
        }
    }

    void init()
    {
        webView=findViewById(R.id.web);
        dialog = new SpotsDialog.Builder().setContext(this).build();
    }
}
