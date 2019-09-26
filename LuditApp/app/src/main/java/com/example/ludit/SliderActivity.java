package com.example.ludit;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.ludit.R;
import com.google.android.material.tabs.TabLayout;

public class SliderActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        int[] imagens = {R.drawable.background_gradient, R.drawable.logo};

        sliderAdapter = new SliderAdapter(this, imagens, R.layout.imagens_slider);
        viewPager.setAdapter(sliderAdapter);

        tabLayout.setupWithViewPager(viewPager, true);
    }


}
