package com.example.ludit.atividades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.R;

public class VideoPlayer extends AppCompatActivity{

    WebView video;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_playere);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        video = findViewById(R.id.player);
        Intent i =  getIntent();
        id = i.getStringExtra("id");

        String link = "<body style='margin: 0; padding: 0'><iframe width='100%' height='100%' allowfullscreen src='https://www.youtube.com/embed/" +  id + "'></iframe></body>";
        video.getSettings().setJavaScriptEnabled(true);
        video.setWebChromeClient(new WebChromeClient());
        video.getSettings().setUseWideViewPort(true);
        video.getSettings().setLoadWithOverviewMode(true);
        video.loadData(link, "text/html", "utf-8");



    }

    private class WebClient extends WebViewClient
    {
        WebClient()
        {

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            super.onPageFinished(view, url);
        }

    }
}
