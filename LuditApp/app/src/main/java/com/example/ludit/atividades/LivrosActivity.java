package com.example.ludit.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.R;
import com.example.ludit.adapters.JogosAdapter;

import java.util.ArrayList;
import java.util.List;

public class LivrosActivity extends AppCompatActivity {

    ListView lvLista;
    String[] titulos ={"Os Três Porquinhos", "Patinho Feio", "Pinóquio", "Festa no céu", "Lebre e o coelho"};
    String[] ids ={"Os Três Porquinhos", "Patinho Feio", "Pinóquio", "Festa no céu", "Lebre e o coelho"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_selecionada);
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
        ((TextView)findViewById(R.id.tvTitulo)).setText("Livros");
        lvLista = findViewById(R.id.lista);

        List<Integer> imagens = new ArrayList<Integer>();
        imagens.add(R.drawable.capa_porcos);
        imagens.add(R.drawable.capa_patinhos);
        imagens.add(R.drawable.capa_pinoquio);
        imagens.add(R.drawable.capa_ceu);
        imagens.add(R.drawable.capa_lebre);

        JogosAdapter listaImagensAdapter = new JogosAdapter(getApplicationContext(), imagens, titulos);
        lvLista.setAdapter(listaImagensAdapter);

        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(LivrosActivity.this, VideoPlayer.class);
                i.putExtra("id", ids[position]);
                startActivity(i);
            }});
    }
}
