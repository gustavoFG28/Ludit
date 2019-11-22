package com.example.ludit.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.R;

import java.util.ArrayList;
import java.util.List;
import com.example.ludit.adapters.ListaLivrosAdapter;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import java.io.IOException;

public class LivrosActivity extends AppCompatActivity {
    ListView lvLista;
    public static final String API_KEY = "AIzaSyAZ_TLsDPOQy4w4GZysdXjq83j-P4nFcEM";
    public final String playlistId = "PLCE7fgij3imj6sEFu6AqUkUrsAjfb0Yml";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_selecionada);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
        ((TextView)findViewById(R.id.tvTitulo)).setText("Livros");
        lvLista = findViewById(R.id.lista);

        List<Integer> imagens = new ArrayList<Integer>();
        imagens.add(R.drawable.capa_lebre);
        imagens.add(R.drawable.capa_patinhos);
        imagens.add(R.drawable.capa_porcos);
        imagens.add(R.drawable.capa_pinoquio );
        imagens.add(R.drawable.capa_ceu);
        final List<Video> videos = getVideos();

        ListaLivrosAdapter listaImagensAdapter = new ListaLivrosAdapter(getApplicationContext(), videos, imagens);
        lvLista.setAdapter(listaImagensAdapter);


        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(LivrosActivity.this, VideoPlayer.class);
                i.putExtra("id", videos.get(position).getId());
                startActivity(i);
            }});

        ((Button)findViewById(R.id.btnSair)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private List<Video> getVideos(){

        List<Video> videos= new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        YouTube yt = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {
            }
        }).build();

        try {
            YouTube.PlaylistItems.List request = yt.playlistItems().list("snippet");

            request.setPlaylistId(playlistId);

            request.setFields("items(snippet),nextPageToken");

            request.setKey(API_KEY);

            String nextToken = "";

            do {
                request.setPageToken(nextToken);
                PlaylistItemListResponse playlistItemResult = request.execute();

                for(int i = 0; i < playlistItemResult.getItems().size(); i++) {
                    List<PlaylistItem> playlistItems = playlistItemResult.getItems();
                    PlaylistItemSnippet snippet = playlistItems.get(i).getSnippet();
                    Video video = new Video(snippet.getResourceId().getVideoId(), snippet.getTitle(), snippet.getThumbnails().getMedium().getUrl());
                    videos.add(video);
                }


                nextToken = playlistItemResult.getNextPageToken();
            } while (nextToken != null);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return  videos;
    }

}
