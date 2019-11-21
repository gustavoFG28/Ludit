package com.example.ludit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.user.PerfilActivity;

import java.util.ArrayList;


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
        if(qualActivity == SliderActivity.class)
        {
            ArrayList<SliderIntro> lista = new ArrayList<>();
            lista.add(new SliderIntro("Vídeos", "Vídeos direto do youtube, selecionados para você", R.drawable.slider_videos));
            lista.add(new SliderIntro("Músicas", "Músicas muito divertidas para te fazer mexer o esqueleto", R.drawable.slider_musica));
            lista.add(new SliderIntro("Histórias", "Histórias animadas com muito amor e dedicação", R.drawable.slider_livros));
            lista.add(new SliderIntro("Jogos", "Jogos super legais para aprender enquanto se diverte", R.drawable.slider_jogos));

            i.putExtra("array", lista);
        }
        startActivity(i);
        finish();
    }
}
