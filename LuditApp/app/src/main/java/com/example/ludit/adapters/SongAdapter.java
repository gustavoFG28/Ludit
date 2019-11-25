package com.example.ludit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.ludit.R;
import com.example.ludit.atividades.Video;

import java.util.List;

public class SongAdapter extends ArrayAdapter {

    private Context context;
    private List<Video> lista = null;

    public SongAdapter(Context context, List<Video> lista) {
        super(context, 0 , lista);
        this.context = context;
        this.lista = lista;
    }


    @Override
    public View getView(final int position, View viewAtual, ViewGroup parent) {

        if(viewAtual == null)
            viewAtual = LayoutInflater.from(context).inflate(R.layout.layout_lista_song, null);

        Video qualMusica = lista.get(position);
        TextView textView = viewAtual.findViewById(R.id.song_title);
        textView.setText(qualMusica.getTitulo());

        if(position % 2 == 0)
            textView.setTextColor(ContextCompat.getColor(viewAtual.getContext(), R.color.startColorGradient));
        else
            textView.setTextColor(ContextCompat.getColor(viewAtual.getContext(), R.color.endColorGradient));
        return  viewAtual;
    }
}
