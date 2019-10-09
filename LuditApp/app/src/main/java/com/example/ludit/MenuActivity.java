package com.example.ludit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button btnLogar, btnCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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
