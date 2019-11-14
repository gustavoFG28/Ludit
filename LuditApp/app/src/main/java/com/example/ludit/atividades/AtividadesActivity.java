package com.example.ludit.atividades;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.R;

public class AtividadesActivity extends AppCompatActivity {

    Button btnJogos, btnMusicas, btnVideos, btnLivros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividades);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        btnJogos = findViewById(R.id.btnJogos);
        btnMusicas = findViewById(R.id.btnMusicas);
        btnVideos = findViewById(R.id.btnVideos);
        btnLivros = findViewById(R.id.btnLivros);

        btnJogos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AtividadesActivity.this, JogosActivity.class);
                startActivity(i);
            }
        });

        btnMusicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AtividadesActivity.this, SongActivity.class);
                startActivity(i);
            }
        });

        btnVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AtividadesActivity.this, VideosActivity.class);
                startActivity(i);
            }
        });

        btnLivros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AtividadesActivity.this, LivrosActivity.class);
                startActivity(i);
            }
        });

        ((Button)findViewById(R.id.btnSair)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
