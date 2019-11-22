package com.example.ludit.games;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.R;

public class PinguimActivity extends AppCompatActivity {


    LinearLayout linearLayout;
    SharedPreferences preferences;
    String email, nomeFilho;
    MyView view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinguim);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        MediaPlayer mediaPlayer= MediaPlayer.create(PinguimActivity.this,R.raw.pinguim);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        preferences = getApplicationContext().getSharedPreferences("minhaShared",MODE_PRIVATE);

        email = preferences.getString("email", null);
        nomeFilho = preferences.getString("nomeFilho", null);

        email  = "sasa";
        nomeFilho = "Henrique";

        view  = new MyView(this, 50, nomeFilho, email);
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        linearLayout.addView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        view.pararJogo();
    }
}



