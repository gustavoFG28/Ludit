package com.example.ludit.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ludit.MenuActivity;
import com.example.ludit.R;
import com.example.ludit.adapters.FilhoAdapter;
import com.example.ludit.webservice.Filho;
import com.example.ludit.webservice.RetrofitConfig;
import com.example.ludit.webservice.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {

    Button btnConfiguracoes, btnSair;
    GridView listaFilhos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        btnSair = findViewById(R.id.btnSair);
        btnConfiguracoes = findViewById(R.id.btnConfiguracoes);
        listaFilhos = findViewById(R.id.grid_filhos);

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE).edit();
                editor.putString("email", null);
                editor.putString("nome", null);
                editor.commit();

                Intent i = new Intent(PerfilActivity.this, MenuActivity.class);
                startActivity(i);
                finish();
            }
        });

        listaFilhos = findViewById(R.id.grid_filhos);
        construirLista();

        listaFilhos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }

    public  void  construirLista() {
        UserService service = RetrofitConfig.getClient().create(UserService.class);

        Call<List<Filho>> call = service.buscarFilhos(getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE).getString("email", ""));

        call.enqueue(new Callback<List<Filho>>() {
            @Override
            public void onResponse(Call<List<Filho>> call, Response<List<Filho>> response) {
                if(!response.isSuccessful()){
                    return;
                }

                FilhoAdapter lista = new FilhoAdapter(PerfilActivity.this, response.body()){};
                listaFilhos.setAdapter(lista);
            }

            @Override
            public void onFailure(Call<List<Filho>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG ).show();
            }
        });
    }



}
