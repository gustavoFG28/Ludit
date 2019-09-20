package com.example.ludit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnJogos, btnMusicas, btnVideos, btnLivros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnJogos = (Button)findViewById(R.id.btnJogos);
        btnMusicas = (Button) findViewById(R.id.btnMusicas);
        btnVideos = (Button) findViewById(R.id.btnVideos);
        btnLivros = (Button) findViewById(R.id.btnLivros);

        btnJogos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnMusicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trocarPagina(MusicaActivity.class);
            }
        });

        btnLivros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trocarPagina(SlideActivity.class);
            }
        });

    }


    private void trocarPagina(Class<?> qualActivity)
    {
        Intent i = new Intent(MainActivity.this, qualActivity);
        startActivity(i);
    }


}
