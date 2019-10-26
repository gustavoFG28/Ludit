package com.example.ludit.games;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.anychart.core.annotations.Line;
import com.example.ludit.R;

import java.util.Timer;

public class PinguimActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    Button btnCima;
    SharedPreferences preferences;
    String email, nomeFilho;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinguim);

        preferences = getApplicationContext().getSharedPreferences("minhaShared",MODE_PRIVATE);

        email = preferences.getString("email", null);
        nomeFilho = preferences.getString("nomeFilho", null);

        email  = "sasa";
        nomeFilho = "Henrique";

        linearLayout = (LinearLayout) findViewById(R.id.linear);
        final MyView view = new MyView(this, 50, nomeFilho, email);
        linearLayout.addView(view);

        btnCima = (Button) findViewById(R.id.btnPula);

        btnCima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.mexerPinguim(-100);
            }
        });
    }
}



