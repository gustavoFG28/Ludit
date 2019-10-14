package com.example.ludit.canvas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        ViewCanvas mGameView = new ViewCanvas(this);
        //LinearLayout linearLayout = findViewById(R.id.linearLayout);
        //linearLayout.addView(mGameView);
        setContentView(mGameView);
        //mGameView.thread.doStart();
    }
}
