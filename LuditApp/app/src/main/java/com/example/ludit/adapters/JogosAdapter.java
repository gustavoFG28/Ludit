package com.example.ludit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ludit.R;

import java.util.List;


public class JogosAdapter extends ArrayAdapter {
    private int layout;
    private Context context;
    private List<Integer> imagens = null;
    private String[] titulos = null;

    public JogosAdapter(Context context,List<Integer> listaImagens, String[] titulos) {
        super(context,0, listaImagens);
        this.context = context;
        this.imagens = listaImagens;
        this.titulos = titulos;
    }

    @Override
    public View getView(int position, View viewAtual, ViewGroup parent) {
        if(viewAtual == null)
            viewAtual = LayoutInflater.from(context).inflate(R.layout.layout_videos, null);

        ImageView img = viewAtual.findViewById(R.id.imgVideo);
        img.setImageResource(imagens.get(position));

        TextView txt = viewAtual.findViewById(R.id.playerTitulo);
        txt.setText(titulos[position]);

        return  viewAtual;
    }
}

