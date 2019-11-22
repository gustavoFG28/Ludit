package com.example.ludit.atividades;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.R;
import com.example.ludit.SliderActivity;
import com.example.ludit.SliderIntro;
import com.example.ludit.adapters.JogosAdapter;
import com.example.ludit.bluetooth.ConnectionThread;
import com.example.ludit.games.FormasActivity;
import com.example.ludit.games.GeniusActivity;
import com.example.ludit.games.MatematicaActivity;
import com.example.ludit.games.PinguimActivity;
import com.example.ludit.games.ReciclagemActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JogosActivity extends AppCompatActivity {

    static Context context;
    ListView lvLista;
    List<Integer> imagens = new LinkedList<Integer>();
    ConnectionThread connect;

    String[] titulos ={"Jogo das Formas", "Genius", "Jogo da Reciclagem", "Bola de Neve", "Jogo dos Números"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_selecionada);

        context = this;
        iniciarBluetooth();

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        ((TextView)findViewById(R.id.tvTitulo)).setText("Jogos");
        lvLista = findViewById(R.id.lista);

        imagens.add(R.drawable.capa_formas); //Formas
        imagens.add( R.drawable.capa_genius); //Genius
        imagens.add(R.drawable.capa_reciclagem); //Reciclagem
        imagens.add( R.drawable.capa_pinguim); //Pinguim
        imagens.add(R.drawable.capa_matematica); //Matemática

        JogosAdapter listaImagensAdapter = new JogosAdapter(getApplicationContext(), imagens, titulos);
        lvLista.setAdapter(listaImagensAdapter);

        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(JogosActivity.this, SliderActivity.class);
                Class<?> destino = null;
                ArrayList<SliderIntro> lista = new ArrayList<>();
                switch (position)
                {
                    case 0:
                        lista.add(new SliderIntro("Vídeos", "Vídeos direto do youtube, selecionados para você", R.drawable.rac_ins_1));
                        lista.add(new SliderIntro("Músicas", "Músicas muito divertidas para te fazer mexer o esqueleto", R.drawable.rac_ins_2));
                        destino = FormasActivity.class;

                        break;
                    case 1:
                        lista.add(new SliderIntro("Vídeos", "Vídeos direto do youtube, selecionados para você", R.drawable.mem_ins_1));
                        lista.add(new SliderIntro("Músicas", "Músicas muito divertidas para te fazer mexer o esqueleto", R.drawable.mem_ins_2));
                        lista.add(new SliderIntro("Vídeos", "Vídeos direto do youtube, selecionados para você", R.drawable.mem_ins_3));
                        lista.add(new SliderIntro("Músicas", "Músicas muito divertidas para te fazer mexer o esqueleto", R.drawable.mem_ins_4));
                        lista.add(new SliderIntro("Vídeos", "Vídeos direto do youtube, selecionados para você", R.drawable.mem_ins_5));
                        destino = GeniusActivity.class;
                        break;
                    case 2:
                        lista.add(new SliderIntro("Vídeos", "Vídeos direto do youtube, selecionados para você", R.drawable.rec_ins_1));
                        lista.add(new SliderIntro("Músicas", "Músicas muito divertidas para te fazer mexer o esqueleto", R.drawable.rec_ins_2));
                        lista.add(new SliderIntro("Vídeos", "Vídeos direto do youtube, selecionados para você", R.drawable.rec_ins_3));
                        destino = ReciclagemActivity.class;
                        break;
                    case 3:
                        lista.add(new SliderIntro("Vídeos", "Vídeos direto do youtube, selecionados para você", R.drawable.pin_ins_1));
                        lista.add(new SliderIntro("Músicas", "Músicas muito divertidas para te fazer mexer o esqueleto", R.drawable.pin_ins_2));
                        lista.add(new SliderIntro("Histórias", "Histórias animadas com muito amor e dedicação", R.drawable.pin_ins_3));
                        destino = PinguimActivity.class;
                        break;
                    case 4:
                        lista.add(new SliderIntro("Vídeos", "Vídeos direto do youtube, selecionados para você", R.drawable.mat_ins_1));
                        lista.add(new SliderIntro("Músicas", "Músicas muito divertidas para te fazer mexer o esqueleto", R.drawable.mat_ins_2));
                        destino = MatematicaActivity.class;
                        break;
                }



                i.putExtra("array", lista);
                i.putExtra("destino", destino);

                startActivity(i);
            }});

        ((Button)findViewById(R.id.btnSair)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void iniciarBluetooth()
    {
        /*  Teste rápido. O hardware Bluetooth do dispositivo Android
            está funcionando ou está bugado de forma misteriosa?
            Será que existe, pelo menos? Provavelmente existe.
         */
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            Toast.makeText(this,"Que pena! Hardware Bluetooth não está funcionando", Toast.LENGTH_SHORT).show();
        } else {

         /* A chamada do seguinte método liga o Bluetooth no dispositivo Android
            sem pedido de autorização do usuário. É altamente não recomendado no
            Android Developers, mas, para simplificar este app, que é um demo,
            faremos isso. Na prática, em um app que vai ser usado por outras
            pessoas, não faça isso.
         */
            if(!btAdapter.isEnabled()) {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, 3);
            }
        }
    }
}
