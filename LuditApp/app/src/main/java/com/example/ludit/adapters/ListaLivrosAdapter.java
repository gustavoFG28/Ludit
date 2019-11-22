package com.example.ludit.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ludit.R;
import com.example.ludit.atividades.Video;

import java.io.InputStream;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;

public class ListaLivrosAdapter extends ArrayAdapter {

    private int layout;
    private Context context;
    private List<Video> videos;
    private  List<Integer> imagens;

    public ListaLivrosAdapter(Context context,  List<Video> videos, List<Integer> imagens) {
        super(context,0 , videos);
        this.context = context;
        this.videos = videos;
        this.imagens = imagens;
    }

    @Override
    public View getView(int position, View viewAtual, ViewGroup parent) {

        if(viewAtual == null)
            viewAtual = LayoutInflater.from(context).inflate(R.layout.layout_videos, null);

        Video qualVideo = videos.get(position);

        ImageView img = viewAtual.findViewById(R.id.imgVideo);
        img.setImageResource(imagens.get(position));

        TextView txt = viewAtual.findViewById(R.id.playerTitulo);

        txt.setText(qualVideo.getTitulo());

        return  viewAtual;
    }

}
