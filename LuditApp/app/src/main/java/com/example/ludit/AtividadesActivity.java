package com.example.ludit;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class AtividadesActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividades);

        sharedPreferences = getPreferences(MODE_PRIVATE);
        if(sharedPreferences.contains("email"))
            trocarPagina(FilhoActivity.class);
        else
            trocarPagina(SliderActivity.class);

    }


    private void trocarPagina(Class<?> qualActivity)
    {
        Intent i = new Intent(AtividadesActivity.this, qualActivity);
        startActivity(i);
    }


}
