package com.example.ludit.atividades;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.R;
import com.example.ludit.adapters.ListaVideosAdapter;
import com.example.ludit.webservice.AtividadesService;
import com.example.ludit.webservice.RetrofitConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideosActivity extends YouTubeBaseActivity {

    ListView lvLista;

    String url = "http://www.googleapis.com/";
    public static final String params = "?part=snippet&maxResults=50";
    public static final String API_KEY = "AIzaSyC_e1PUXyzTTNkUkdwWxTcohOtTyRYi7ds";
    public final String playlistId = "PLjf0D1j3KgIGa9tBFne55fKZ1_uErgM0i";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_selecionada);

        ((TextView)findViewById(R.id.tvTitulo)).setText("Vídeos");
        lvLista = findViewById(R.id.lista);

        final List<Video> videos = getVideos();

        ListaVideosAdapter listaImagensAdapter = new ListaVideosAdapter(getApplicationContext(), videos);

        YouTubePlayerView a;
        a = (YouTubePlayerView) findViewById(R.id.teste);
        a.initialize(VideosActivity.API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if(!b)
                {
                    youTubePlayer.loadVideo(videos.get(0).getId());
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
       // lvLista.setAdapter(listaImagensAdapter);*/
    }

    private List<Video> getVideos() {
        List<Video> videos= new ArrayList<>();
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
                videos.add(video);
                i++;

                nextToken = playlistItemResult.getNextPageToken();
            } while (nextToken != null);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        /*AtividadesService service = RetrofitConfig.getAtividade(url).create(AtividadesService.class);
        final List<Video> videos = null;
        Call<PlaylistItemListResponse> call = service.getYouTubeVideos(API_KEY, playlistId, "snippet");
        try {


            call.enqueue(new Callback<PlaylistItemListResponse>() {
                @Override
                public void onResponse(Call<PlaylistItemListResponse> call, Response<PlaylistItemListResponse> response) {
                    if (!response.isSuccessful()) {
                        return;
                    }


                }

                @Override
                public void onFailure(Call<PlaylistItemListResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        */
        return  videos;
    }
}
