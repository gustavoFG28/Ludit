package com.example.ludit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ludit.R;
import com.example.ludit.atividades.Video;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

public class ListaVideosAdapter extends ArrayAdapter {

    private Context context;
    private List<Video> lista = null;

    public ListaVideosAdapter(Context context, List<Video> lista) {
        super(context, 0 , lista);
        this.context = context;
        this.lista = lista;
    }


    @Override
    public View getView(int position, View viewAtual, ViewGroup parent) {

        if(viewAtual == null)
            viewAtual = LayoutInflater.from(context).inflate(R.layout.layout_videos, null);

        YouTubePlayerView youTubePlayerView = viewAtual.findViewById(R.id.player);
        TextView textView = viewAtual.findViewById(R.id.playerTitulo);




        return  viewAtual;
    }
}
