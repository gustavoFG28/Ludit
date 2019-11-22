package com.example.ludit.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.R;

public class MenuActivity extends AppCompatActivity {

    Button btnLogar, btnCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
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

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE);
        if(!sharedPreferences.contains("jaEntrou"))
        {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean("jaEntrou", true);
            edit.commit();
        }


        btnLogar = findViewById(R.id.btnLogarPrincipal);
        btnCadastro = findViewById(R.id.btnCadastroPrincipal);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trocarPagina(LoginActivity.class);
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trocarPagina(CadastroActivity.class);
            }
        });
    }

    private void trocarPagina(Class<?> qualActivity)
    {
        Intent i = new Intent(MenuActivity.this, qualActivity);
        startActivity(i);
    }
}
