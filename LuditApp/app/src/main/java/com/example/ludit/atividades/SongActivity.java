package com.example.ludit.atividades;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.R;
import com.example.ludit.adapters.SongAdapter;
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
        lvLista = findViewById(R.id.lista);
        ((TextView)findViewById(R.id.tvTitulo)).setText("Músicas");

        final List<Video> musicasArray = getMusicas();

        SongAdapter adapter = new SongAdapter(getApplicationContext(), musicasArray);

        lvLista.setAdapter(adapter);

        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(SongActivity.this);

                View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_song, null);
                builder.setView(dialogView);

                AlertDialog dialog = builder.create();

                /*DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                int displayWidth = displayMetrics.widthPixels;
                int displayHeight = displayMetrics.heightPixels;

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());

                int dialogWindowWidth = (int) (displayWidth * 0.8f);
                int dialogWindowHeight = (int) (displayHeight * 0.7f);

                layoutParams.width = dialogWindowWidth;
                layoutParams.height = dialogWindowHeight;

                dialog.getWindow().setAttributes(layoutParams);*/

                dialogView.setMinimumHeight(700);

                WebView reprodutor = (WebView)dialogView.findViewById(R.id.wb_ver);

                Video musicaSelecionada = musicasArray.get(position);
                String idVideo = musicaSelecionada.getId();

                String link = "<iframe src='https://www.youtube.com/embed/" +  idVideo + "'></iframe>";

                reprodutor.getSettings().setJavaScriptEnabled(true);
                reprodutor.setWebChromeClient(new WebChromeClient());
                reprodutor.loadData(link, "text/html", "utf-8");

                dialog.setTitle(musicaSelecionada.getTitulo());
                dialog.show();
            }
        });
    }

    private List<Video> getMusicas() {

        List<Video> musicas = new ArrayList<>();

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

            request.setFields("items(snippet/title,snippet/resourceId/videoId),nextPageToken");

            request.setKey(API_KEY);

            String nextToken = "";

            do {
                request.setPageToken(nextToken);
                PlaylistItemListResponse playlistItemResult = request.execute();


                for(int i = 0; i<playlistItemResult.getItems().size(); i++)
                {
                    List<PlaylistItem> playlistItems = playlistItemResult.getItems();
                    PlaylistItemSnippet snippet = playlistItems.get(i).getSnippet();
                    Video video = new Video(snippet.getResourceId().getVideoId(), snippet.getTitle(), null);
                    musicas.add(video);
                }

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
