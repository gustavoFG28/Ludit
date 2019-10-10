package com.example.ludit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

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
