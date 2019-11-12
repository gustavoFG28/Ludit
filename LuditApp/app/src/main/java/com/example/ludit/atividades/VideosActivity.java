package com.example.ludit.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.R;
import com.example.ludit.adapters.ListaVideosAdapter;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistItemSnippet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VideosActivity extends AppCompatActivity {

    ListView lista;
    String videoId;
    String url = "http://www.googleapis.com/";

    public static final String params = "?part=snippet&maxResults=50";
    public static final String API_KEY = "AIzaSyAZ_TLsDPOQy4w4GZysdXjq83j-P4nFcEM";
    public final String playlistId = "PLjf0D1j3KgIGa9tBFne55fKZ1_uErgM0i";

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
        ((TextView)findViewById(R.id.tvTitulo)).setText("Vídeos");
        lista = findViewById(R.id.lista);
        final List<Video> videos = getVideos();

        ListaVideosAdapter adapter = new ListaVideosAdapter(getApplicationContext(), videos);

        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(VideosActivity.this, VideoPlayer.class);
                i.putExtra("id", videos.get(position).getId());
                startActivity(i);
            }});
    }


    private List<Video> getVideos() {
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
