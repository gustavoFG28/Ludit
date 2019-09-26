package com.example.ludit;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.ludit.R;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getPreferences(MODE_PRIVATE);
        if(sharedPreferences.contains("email"))
            trocarPagina(FilhoActivity.class);
        else
            trocarPagina(SliderActivity.class);

    }


    private void trocarPagina(Class<?> qualActivity)
    {
        Intent i = new Intent(MainActivity.this, qualActivity);
        startActivity(i);
    }


}
