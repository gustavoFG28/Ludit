package com.example.ludit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.user.PerfilActivity;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override public void run() {
                Iniciar();
            }
        }, 2000);
    }

    private void Iniciar() {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE);
        if(sharedPreferences.contains("email"))
            trocarPagina(PerfilActivity.class);
        else
            trocarPagina(SliderActivity.class);
    }

    private void trocarPagina(Class<?> qualActivity)
    {
        Intent i = new Intent(SplashScreenActivity.this, qualActivity);
        startActivity(i);
        finish();
    }
}
