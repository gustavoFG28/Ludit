package com.example.ludit;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;


public class SliderAdapter extends PagerAdapter {

    private int[] images = {R.drawable.logo, R.drawable.background_gradient};
    private LayoutInflater inflater;
    private Context context;
    private String[] titles = {"logo", "fundo"};
    private String[] descriptions = {"aaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbb"};

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View imageLayout = inflater.inflate(R.layout.activity_slider, view, false);

        LinearLayout layout = view.findViewById(R.id.slideLinearLayout);

        ImageView imageView = (ImageView)view.findViewById(R.id.slideImage);

        TextView textView1 = (TextView)view.findViewById(R.id.tvTitulo);

        TextView textView2 = (TextView)view.findViewById(R.id.tvDescricao);

        imageView.setImageResource(images[position]);
        textView1.setText(titles[position]);
        textView2.setText(descriptions[position]);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
