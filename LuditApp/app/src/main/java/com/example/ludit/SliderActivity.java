package com.example.ludit;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.ludit.adapters.SliderAdapter;
import com.example.ludit.user.MenuActivity;
import com.google.android.material.tabs.TabLayout;

public class SliderActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SliderAdapter sliderAdapter;
    private Button btnBack, btnNext;
    int qualPagina = 0;
    private int[] imagens = {R.drawable.slider_videos, R.drawable.slider_musica, R.drawable.slider_livros, R.drawable.slider_jogos};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        btnBack = findViewById(R.id.btnVoltarSlider);
        btnBack.setVisibility(View.INVISIBLE);
        btnNext = findViewById(R.id.btnAvancarSlider);
        sliderAdapter = new SliderAdapter(this, imagens, R.layout.imagens_slider);
        viewPager.setAdapter(sliderAdapter);
        viewPager.addOnPageChangeListener(viewListener);
        tabLayout.setupWithViewPager(viewPager, true);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qualPagina >= imagens.length - 1)
                {
                    Intent i = new Intent(SliderActivity.this, MenuActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                    viewPager.setCurrentItem(qualPagina + 1);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(qualPagina - 1);
            }
        });
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            qualPagina = position;
            if (position == 0) {
                btnBack.setEnabled(false);
                btnBack.setVisibility(View.INVISIBLE);
            } else if (position >= imagens.length - 1) {
                btnNext.setEnabled(true);
                btnNext.setText("Terminar");
            } else {
                btnBack.setEnabled(true);
                btnNext.setText("Próximo");
                btnBack.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}