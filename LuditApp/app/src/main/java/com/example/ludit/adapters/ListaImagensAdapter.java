package com.example.ludit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.ludit.R;

import java.util.List;

public class ListaImagensAdapter extends ArrayAdapter {

    private int layout;
    private Context context;
    private List<Integer> imagens = null;

    public ListaImagensAdapter(Context context,  List<Integer> listaImagens) {
        super(context,0 , listaImagens);
        this.context = context;
        this.layout = layout;
    }

    @Override
    public View getView(int position, View viewAtual, ViewGroup parent) {

        if(viewAtual == null)
            viewAtual = LayoutInflater.from(context).inflate(R.layout.layout_dialog_lista_imagens, null);

       int qualImg = imagens.get(position);
        ImageView img = viewAtual.findViewById(R.id.opcaoImg);

        img.setImageResource(qualImg);


        return  viewAtual;
    }
}
