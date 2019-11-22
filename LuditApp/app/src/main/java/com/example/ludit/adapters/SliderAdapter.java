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
import com.example.ludit.SliderIntro;

import java.util.List;


public class SliderAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    int layout;
    List<SliderIntro> array;

    public SliderAdapter(Context context, int layout, List<SliderIntro> array) {
        this.context = context;
        this.layout = layout;
        this.array = array;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(layout, container, false);


        if(layout != R.layout.slider_int)
        {
            ImageView imageView = (ImageView) view.findViewById(R.id.slideImage);
            imageView.setImageResource(array.get(position).getImg());

            TextView txtTitulo = (TextView) view.findViewById(R.id.tvTitulo);
            TextView txtDescricao = (TextView) view.findViewById(R.id.tvDescricao);

            txtTitulo.setText(array.get(position).getTitulo());
            txtDescricao.setText(array.get(position).getDescricao());

        }
        else
            view.findViewById(R.id.slideLinearLayout).setBackgroundResource(array.get(position).getImg());
        container.addView(view);

        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}
