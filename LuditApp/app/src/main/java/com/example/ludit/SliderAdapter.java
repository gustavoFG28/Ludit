package com.example.ludit;

import android.content.Context;
import android.os.Parcelable;
import android.text.Layout;
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
    int layout;
    private int[] images;

    public SliderAdapter(Context context, int[] img, int layout) {
        this.context = context;
        this.images = img;
        this.layout = layout;
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

        View view = inflater.inflate(layout, container, false);

        LinearLayout layoutSlide = view.findViewById(R.id.slideLinearLayout);
        ImageView imageView = (ImageView)view.findViewById(R.id.slideImage);

        layoutSlide.setBackgroundColor(R.drawable.background_gradient);
        imageView.setImageResource(images[position]);
        container.addView(view);

        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}
