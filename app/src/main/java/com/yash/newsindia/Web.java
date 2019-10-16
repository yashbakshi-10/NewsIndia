package com.yash.newsindia;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Web extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        WebView webView;
        FloatingActionButton actionButton;
        final LottieAnimationView animationView;
        String url;
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        url = intent.getStringExtra("url");

        setContentView(R.layout.activity_web);
        webView = findViewById(R.id.webView);
        actionButton = findViewById(R.id.back);
        animationView=findViewById(R.id.anim);

        webView.loadUrl(url);

        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                animationView.playAnimation();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                animationView.pauseAnimation();
                animationView.setVisibility(View.INVISIBLE);

            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
