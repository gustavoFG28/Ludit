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

import java.util.List;

public class SliderActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SliderAdapter sliderAdapter;
    private Button btnBack, btnNext;
    int qualPagina = 0;
    private List<SliderIntro> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        btnBack = findViewById(R.id.btnVoltarSlider);
        btnBack.setVisibility(View.INVISIBLE);
        btnNext = findViewById(R.id.btnAvancarSlider);
        list =(List<SliderIntro>) getIntent().getSerializableExtra("array");
        sliderAdapter = new SliderAdapter(this, R.layout.imagens_slider, list);
        viewPager.setAdapter(sliderAdapter);
        viewPager.addOnPageChangeListener(viewListener);
        tabLayout.setupWithViewPager(viewPager, true);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qualPagina >= list.size() - 1)
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
            } else if (position >= list.size() - 1) {
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