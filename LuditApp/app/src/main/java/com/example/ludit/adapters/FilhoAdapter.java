package com.example.ludit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ludit.R;
import com.example.ludit.ui.filho.Filho;

import java.util.List;

public class FilhoAdapter extends ArrayAdapter {
    Context cnt;
    List<Filho> lista;

    public FilhoAdapter(Context context, List<Filho> lista) {
        super(context, 0, lista);

        this.lista = lista;
        cnt = context;
    }

    @Override
    public View getView(int position, View viewAtual, ViewGroup parent) {
        Filho qualAluno = lista.get(position);

        if(viewAtual == null)
            viewAtual = LayoutInflater.from(cnt).inflate(R.layout.layout_dialog_lista_filhos, null);

        TextView tvNome = viewAtual.findViewById(R.id.tvNomeFilho);
        ImageView image = viewAtual.findViewById(R.id.imgFilho);

        tvNome.setText(qualAluno.getNome());
        image.setImageResource(Integer.parseInt(qualAluno.getImgPerfil()));
        return  viewAtual;
    }
}
