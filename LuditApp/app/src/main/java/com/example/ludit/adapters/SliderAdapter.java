package com.example.ludit.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.ludit.R;


public class SliderAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    int layout;
    private int[] imagens;
    private  String[] titulo, descricao;

    public SliderAdapter(Context context, int[] img, int layout, String[] titulo, String[] descricao) {
        this.context = context;
        this.imagens = img;
        this.layout = layout;
        this.descricao = descricao;
        this.titulo = titulo;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public int getCount() {
        return imagens.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(layout, container, false);

        LinearLayout layoutSlide = view.findViewById(R.id.slideLinearLayout);
        ImageView imageView = (ImageView)view.findViewById(R.id.slideImage);
        TextView txtTitulo = (TextView)view.findViewById(R.id.tvTitulo);
        TextView txtDescricao = (TextView)view.findViewById(R.id.tvDescricao);

        txtTitulo.setText(titulo[position]);
        txtDescricao.setText(descricao[position]);
        imageView.setImageResource(imagens[position]);
        container.addView(view);

        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}
