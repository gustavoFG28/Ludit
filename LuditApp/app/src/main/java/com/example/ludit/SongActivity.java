package com.example.ludit;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.os.Bundle;

import com.example.ludit.adapters.ListaVideosAdapter;
import com.example.ludit.adapters.SongAdapter;
import com.example.ludit.atividades.Video;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistItemSnippet;


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

        final List<Video> musicasArray = getMusicas();

        SongAdapter adapter = new SongAdapter(getApplicationContext(), musicasArray);

        lvLista.setAdapter(adapter);
        
        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private List<Video> getMusicas() {

        List<Video> musicas = new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        int i = 0;

        StrictMode.setThreadPolicy(policy);

        YouTube yt = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {
            }
        }).build();

        List<PlaylistItem> playlistItems = new ArrayList<PlaylistItem>();

        try {
            YouTube.PlaylistItems.List request = yt.playlistItems().list("snippet");

            request.setPlaylistId(playlistId);

            request.setFields("items(snippet/title,snippet/thumbnails/medium/url,snippet/resourceId/videoId),nextPageToken");

            request.setKey(API_KEY);

            String nextToken = "";

            do {
                request.setPageToken(nextToken);
                PlaylistItemListResponse playlistItemResult = request.execute();

                playlistItems.addAll(playlistItemResult.getItems());
                PlaylistItemSnippet snippet = playlistItems.get(i).getSnippet();
                Video video = new Video(snippet.getResourceId().getVideoId(), snippet.getTitle(), snippet.getThumbnails().getMedium().getUrl());
                musicas.add(video);
                i++;

                nextToken = playlistItemResult.getNextPageToken();
            } while (nextToken != null);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return  musicas;
    }
}
