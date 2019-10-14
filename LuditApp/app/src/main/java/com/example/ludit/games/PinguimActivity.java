package com.example.ludit.games;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.anychart.core.annotations.Line;
import com.example.ludit.R;

import java.util.Timer;

public class PinguimActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinguim);
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        MyView view = new MyView(this);
        linearLayout.addView(view);
    }
}



