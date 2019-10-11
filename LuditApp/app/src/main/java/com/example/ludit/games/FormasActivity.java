package com.example.ludit.games;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ludit.R;
import com.example.ludit.webservice.RetrofitConfig;
import com.example.ludit.webservice.UserService;
import com.example.ludit.webservice.Filho;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormasActivity extends AppCompatActivity {
    ImageView img;
    int[] imagens = {};
    int[] respostas = {};

    int btnCerto, pontosForma, qtd;
    Button btnAzul, btnVermelho, btnAmarelo, btnVerde;
    Button[] btns = new Button[4];

    SharedPreferences preferences;
    String nomeFilho, email, jogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formas);

        img = (ImageView) findViewById(R.id.imgConjunto);

        btnAmarelo = (Button) findViewById(R.id.btnAmarelo);
        btnAzul = (Button) findViewById(R.id.btnAzul);
        btnVerde = (Button) findViewById(R.id.btnVerde);
        btnVermelho = (Button) findViewById(R.id.btnVermelho);

        btns[0] = btnAmarelo;
        btns[1] = btnAzul;
        btns[2] = btnVermelho;
        btns[3] = btnVerde;

        preferences = getApplicationContext().getSharedPreferences("minhaShared",MODE_PRIVATE);

        email = preferences.getString("email", null);
        nomeFilho = preferences.getString("nomeFilho", null);

        email  = "sasa";
        nomeFilho = "Henrique";

        construirJogo();

        for(int i = 0; i< btns.length; i++)
        {
            final int id = i;
            btns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verResult(btns[id].getId());
                }
            });
        }
    }

    public  void  construirJogo() {
        Random random = new Random();
        int val = random.nextInt(imagens.length);

        img.setImageResource(imagens[val]);

        //colocar imagens nos botões
    }
    public  void  verResult(int id) {
        if(id == btnCerto)
            pontosForma++;
        atualizar();
    }
    public  void  atualizar(){
        if(qtd < 9) {
            qtd++;
            construirJogo();
        }else {
            float pontoFinal = 0.0f;

            if(pontosForma >= 0 && pontosForma <= 2) pontoFinal = -0.05f;
            else if(pontosForma == 3 || pontosForma == 4)  pontoFinal = 0f;
            else if(pontosForma >= 5 && pontosForma <= 7) pontoFinal = 0.05f;
            else pontoFinal = 0.1f;

            UserService service =  RetrofitConfig.getClient().create(UserService.class);

            Call<List<Filho>> ponto = service.skill(email,nomeFilho,"rec", pontoFinal);

            ponto.enqueue(new Callback<List<Filho>>() {
                @Override
                public void onResponse(Call<List<Filho>> call, Response<List<Filho>> response) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormasActivity.this);

                    builder.setTitle("Sua pontuação foi de "+ pontosForma);

                    builder.setMessage("PARABÉNS, DEU CERTO");

                    builder.setNegativeButton("OK", null);

                    builder.create().show();
                }

                @Override
                public void onFailure(Call<List<Filho>> call, Throwable t) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormasActivity.this);

                    AlertDialog alerta;

                    builder.setTitle("Erro com a pontuação");

                    builder.setMessage("Não foi possível enviar sua pontuação");

                    builder.setNegativeButton("OK", null);

                    alerta = builder.create();

                    alerta.show();
                }
            });
        }
    }
}
