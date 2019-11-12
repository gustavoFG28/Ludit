package com.example.ludit.user;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.atividades.AtividadesActivity;
import com.example.ludit.R;

public class FilhoActivity extends AppCompatActivity {

    TextView tvNomeFilho;
    ImageView imgPerfil;

    Button btnAtividades, btnPontuacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filho);
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
        tvNomeFilho = findViewById(R.id.tvNomeFilho);
        imgPerfil = findViewById(R.id.imgPerfil);

        Button btnAtividades = findViewById(R.id.btnAtividades);
        Button btnPontuacao = findViewById(R.id.btnPontuacao);

        SharedPreferences sr = getApplicationContext().getSharedPreferences("filhoShared", MODE_PRIVATE);
        tvNomeFilho.setText(sr.getString("nome", ""));
        imgPerfil.setImageResource(Integer.parseInt(sr.getString("imagem", R.drawable.foto_dodo + "")));

        btnAtividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FilhoActivity.this, AtividadesActivity.class);
                startActivity(i);
            }
        });

        btnPontuacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FilhoActivity.this, PontuacaoActivity.class);
                i.putExtra("nomeFilho",tvNomeFilho.getText());
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_filho, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        //tratar opcoes do menu
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("filhoShared", MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        super.onBackPressed();
    }
}
