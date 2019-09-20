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


public class SliderAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    private String[] titles = {"logo", "fundo"};
    private String[] descriptions = {"aaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbb"};
    private int[] images = {R.drawable.logo, R.drawable.background_gradient};

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.imagens_slider, container, false);

        LinearLayout layoutSlide = view.findViewById(R.id.slideLinearLayout);
        ImageView imageView = (ImageView)view.findViewById(R.id.slideImage);
        TextView textView1 = (TextView)view.findViewById(R.id.tvTitulo);
        TextView textView2 = (TextView)view.findViewById(R.id.tvDescricao);

        layoutSlide.setBackgroundColor(R.drawable.background_gradient);
        imageView.setImageResource(images[position]);
        textView1.setText(titles[position]);
        textView2.setText(descriptions[position]);
        container.addView(view);

        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}
