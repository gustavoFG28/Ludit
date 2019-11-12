package com.example.ludit.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ludit.R;
import com.example.ludit.atividades.Video;

import java.util.List;

public class ListaVideosAdapter extends ArrayAdapter {

    private int layout;
    private Context context;
    private List<Video> videos = null;

    public ListaVideosAdapter(Context context,  List<Video> listaImagens) {
        super(context,0 , listaImagens);
        this.context = context;
        this.videos = listaImagens;
    }

    @Override
    public View getView(int position, View viewAtual, ViewGroup parent) {

        if(viewAtual == null)
            viewAtual = LayoutInflater.from(context).inflate(R.layout.layout_videos, null);

        Video qualVideo = videos.get(position);
        ImageView img = viewAtual.findViewById(R.id.imgVideo);

        img.setImageURI(Uri.parse((qualVideo.getUrl())));
        TextView txt = viewAtual.findViewById(R.id.playerTitulo);

        return  viewAtual;
    }
}
