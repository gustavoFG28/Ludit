package com.example.ludit.atividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ludit.games.PinguimActivity;
import com.example.ludit.R;
import com.example.ludit.adapters.ListaImagensAdapter;
import com.example.ludit.games.FormasActivity;
import com.example.ludit.games.GeniusActivity;
import com.example.ludit.games.MatematicaActivity;
import com.example.ludit.games.ReciclagemActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class JogosActivity extends AppCompatActivity {

    ListView lvLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_selecionada);

        ((TextView)findViewById(R.id.tvTitulo)).setText("Jogos");
        lvLista = findViewById(R.id.lista);

        List<Integer> imagens = new ArrayList<Integer>();
        imagens.add(R.drawable.xvbfxb);
        imagens.add(R.drawable.xvbfxb);
        imagens.add(R.drawable.xvbfxb);
        imagens.add(R.drawable.xvbfxb);
        imagens.add(R.drawable.xvbfxb);

        ListaImagensAdapter listaImagensAdapter = new ListaImagensAdapter(getApplicationContext(), imagens);
        lvLista.setAdapter(listaImagensAdapter);

        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Class<?> qual = null;
                switch (position)
                {
                    case 0: qual = FormasActivity.class; break;
                    case 1: qual = GeniusActivity.class; break;
                    case 2: qual = ReciclagemActivity.class; break;
                    case 3: qual = PinguimActivity.class; break;
                    case 4: qual = MatematicaActivity.class; break;
                }

                Intent i = new Intent(JogosActivity.this, qual);
                startActivity(i);
            }});
    }
}
