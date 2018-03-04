package com.uic.snaram2.mymusic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebviewActivity extends AppCompatActivity {
    Intent i;
    Bundle b;
    String url;
    WebView mWebview;
    String wikiurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
//
//        w =(WebView)findViewById(R.id.web);
//        i = getIntent();
//        b = i.getExtras();
//        data = b.getString("URL");
//        w.getSettings().setMediaPlaybackRequiresUserGesture(true);
//        w.loadUrl("http://"+data);


        i = getIntent();
        b = i.getExtras();
        wikiurl = "http://" + b.getString("wiki");
        url = "http://" + b.getString("URL");
        mWebview = new WebView(this);

//to access the youtube
        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
        final Activity activity = this;
// this sets the webview on to the webview activity
        mWebview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });
        if(!wikiurl.equals( "http://null")) //checks if it is a wikiurl or video url
        {
            mWebview.loadUrl(wikiurl);
        }else
        {
            if(!url.equals( "http://null"))
            mWebview.loadUrl(url);
        }
        setContentView(mWebview);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
