package com.example.ludit;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.ludit.adapters.SliderAdapter;
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

        final int[] imagens = {R.drawable.slider_videos, R.drawable.slider_musica, R.drawable.slider_livros, R.drawable.slider_jogos};

        sliderAdapter = new SliderAdapter(this, imagens, R.layout.imagens_slider);
        viewPager.setAdapter(sliderAdapter);
        tabLayout.setupWithViewPager(viewPager, true);

    }
}