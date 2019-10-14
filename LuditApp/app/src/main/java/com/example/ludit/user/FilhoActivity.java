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

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;

import com.example.ludit.AtividadesActivity;
import com.example.ludit.R;
import java.util.ArrayList;
import java.util.List;

public class FilhoActivity extends AppCompatActivity {

    TextView tvNomeFilho;
    ImageView imgPerfil;

    Button btnAtividades, btnPontuacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filho);

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
