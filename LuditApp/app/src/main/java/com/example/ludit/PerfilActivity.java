package com.example.ludit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {


    Button btnAlteraSenha, btnAlteraNome,btnAlteraEmail;
    TextView tvEmail, tvNome;
    ListView lista_filhos;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        btnAlteraEmail =(Button)findViewById(R.id.btnAlteraEmail);
        btnAlteraNome =(Button)findViewById(R.id.btnAlteraNome);
        btnAlteraSenha =(Button)findViewById(R.id.btnAlteraSenha);
        tvEmail = (TextView) findViewById(R.id.tvEmailUser);
        tvNome = (TextView) findViewById(R.id.tvNomeUser);
        lista_filhos = (ListView) findViewById(R.id.lista_filhos);

        sharedPreferences = getApplicationContext().getSharedPreferences("minhaShared",MODE_PRIVATE);

        final String email = sharedPreferences.getString("email", null);
        final String nome = sharedPreferences.getString("nome", null);

        construirLista(email);

        lista_filhos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public  void  construirLista(String email) {
        UserService service = RetrofitConfig.getClient().create(UserService.class);

        Call<List<Filho>> call = service.buscarFilhos(email);

        call.enqueue(new Callback<List<Filho>>() {
            @Override
            public void onResponse(Call<List<Filho>> call, Response<List<Filho>> response) {
                if(!response.isSuccessful()){
                        return;
                }

                ArrayList<String> arrayList = new ArrayList<String>();
            }

                @Override
                public void onFailure(Call<List<Filho>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG ).show();
                }
            });
    }

}
