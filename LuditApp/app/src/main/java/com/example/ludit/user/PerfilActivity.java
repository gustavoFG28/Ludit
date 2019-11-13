package com.example.ludit.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.R;
import com.example.ludit.adapters.FilhoAdapter;
import com.example.ludit.webservice.Filho;
import com.example.ludit.webservice.RetrofitConfig;
import com.example.ludit.webservice.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {

    TextView tvTituloPerfil;
    Button btnConfiguracoes, btnSair, btnAdicionar;
    ListView listaFilhos;

    ArrayList<Filho> filhos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        btnSair = findViewById(R.id.btnSair);
        btnConfiguracoes = findViewById(R.id.btnConfiguracoes);
        btnAdicionar = findViewById(R.id.btnAdicionar);

        tvTituloPerfil = findViewById(R.id.tvTituloPerfil);

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();

                Intent i = new Intent(PerfilActivity.this, MenuActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnConfiguracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PerfilActivity.this, ConfiguracoesActivity.class);
                startActivity(i);
            }
        });

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PerfilActivity.this, FilhoCadastroActivity.class);
                startActivity(i);
            }
        });

        listaFilhos = findViewById(R.id.lista_filhos);
        listaFilhos.setDivider(null);
        construirLista();

        listaFilhos.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("filhoShared", MODE_PRIVATE).edit();
                editor.putString("nome", filhos.get(position).getNome());
                editor.putString("imagem", filhos.get(position).getImgPerfil());
                editor.commit();

                Intent i = new Intent(PerfilActivity.this, FilhoActivity.class);
                startActivity(i);
            }});
    }

    public void construirLista() {
        UserService service = RetrofitConfig.getClient().create(UserService.class);


        Call<List<Filho>> call = service.buscarFilhos(getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE).getString("email", ""));

        call.enqueue(new Callback<List<Filho>>() {
            @Override
            public void onResponse(Call<List<Filho>> call, Response<List<Filho>> response) {
                if(!response.isSuccessful()){
                    return;
                }

                filhos = (ArrayList<Filho>) response.body();
                FilhoAdapter listaAdapter = new FilhoAdapter(PerfilActivity.this, response.body()){};
                listaFilhos.setAdapter(listaAdapter);
            }

            @Override
            public void onFailure(Call<List<Filho>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG ).show();
            }
        });
    }

    public  void  construir(){
        tvTituloPerfil.setText("Bem vindo(a),\n" + getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE).getString("nome", ""));
    }

    @Override
    protected void onResume() {
        super.onResume();
        construirLista();
        construir();
    }
}
