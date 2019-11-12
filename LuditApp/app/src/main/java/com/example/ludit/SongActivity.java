package com.example.ludit;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.os.Bundle;


public class SongActivity extends AppCompatActivity {
    ListView lvLista;

    String url = "http://www.googleapis.com/";
    public static final String params = "?part=snippet&maxResults=50";
    public static final String API_KEY = "AIzaSyC_e1PUXyzTTNkUkdwWxTcohOtTyRYi7ds";
    public final String playlistId = "PLCE7fgij3imhP8nVdnBPX4yb-ayHQ3tni";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_selecionada);

        lvLista = findViewById(R.id.lista);

        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
