package com.example.ludit.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.R;

public class VideoPlayer extends AppCompatActivity {

    WebView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_playere);

        video = findViewById(R.id.player);
        Intent i =  getIntent();
        String id = i.getStringExtra("id");

        String link = "<iframe width='100%' height='100%' allow='autoplay; fullscreen' src='https://www.youtube.com/embed/" +  id + "'></iframe>";

        video.loadData(link, "text/html", "utf-8");
        video.getSettings().setJavaScriptEnabled(true);
        video.setWebChromeClient(new WebChromeClient());
    }
}
