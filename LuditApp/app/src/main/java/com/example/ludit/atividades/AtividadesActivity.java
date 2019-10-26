package com.example.ludit.atividades;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ludit.R;

public class AtividadesActivity extends AppCompatActivity {

    Button btnJogos, btnMusicas, btnVideos, btnLivros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividades);

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
                Intent i = new Intent(AtividadesActivity.this, MusicaActivity.class);
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
    }
}
