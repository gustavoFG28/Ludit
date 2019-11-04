package com.example.ludit.atividades;

import android.os.Bundle;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideosActivity extends AppCompatActivity{

    ListView lvLista;

    String url = "ttps://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50";
    public static final String API_KEY = "AIzaSyC_e1PUXyzTTNkUkdwWxTcohOtTyRYi7ds";
    public final String playlistId = "PLjf0D1j3KgIGa9tBFne55fKZ1_uErgM0i";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_selecionada);

        ((TextView)findViewById(R.id.tvTitulo)).setText("Vídeos");
        lvLista = findViewById(R.id.lista);

        List<Video> videos = getVideos();

        ListaVideosAdapter listaImagensAdapter = new ListaVideosAdapter(getApplicationContext(), videos);
        lvLista.setAdapter(listaImagensAdapter);

        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            }});
    }

    private List<Video> getVideos() {
        AtividadesService service = RetrofitConfig.getAtividade(url + "&playlistId=" + playlistId+"&key=" + API_KEY).create(AtividadesService.class);
        final List<Video> videos = null;
        Call<List<Video>> call = service.buscarVideos();

        call.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                if(!response.isSuccessful()){
                    return;
                }


            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG ).show();
            }
        });

        return  videos;
    }
}
