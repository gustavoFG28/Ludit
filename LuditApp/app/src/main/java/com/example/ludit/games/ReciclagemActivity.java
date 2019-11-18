package com.example.ludit.games;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ludit.R;
import com.example.ludit.bluetooth.ConnectionThread;
import com.example.ludit.webservice.Filho;
import com.example.ludit.webservice.RetrofitConfig;
import com.example.ludit.webservice.UserService;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReciclagemActivity extends AppCompatActivity {

    ImageView img;

    int[] imagens = {R.drawable.glass_1, R.drawable.glass_2, R.drawable.glass_3, R.drawable.glass_4,
                    R.drawable.metal_1, R.drawable.metal_2, R.drawable.metal_3,
                    R.drawable.paper_1, R.drawable.paper_2, R.drawable.papel_3,
                    R.drawable.plastic_1, R.drawable.plastic_2, R.drawable.plastic_3};

    char btnCerto;
    int pontosReciclagem, qtd;
    ConnectionThread thread;
    private Handler handler;

    SharedPreferences preferences;
    String nomeFilho, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciclagem);
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
        img = (ImageView) findViewById(R.id.imgLixo);

        Intent intent = getIntent();
        thread =(ConnectionThread)intent.getSerializableExtra("ConnectionThread");

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                Bundle bundle = msg.getData();
                byte[] data = bundle.getByteArray("data");
                String dataString= new String(data);

                if(dataString.equals(btnCerto))
                    pontosReciclagem++;
                atualizar();
            }
        };

        thread.setHandler(handler);
    }

    public  void  construirJogo() {
        Random random = new Random();
        int val = random.nextInt(imagens.length);

        img.setImageResource(imagens[val]);

        if(val >= 0 && val <= 3)
            btnCerto = 'G';
        else if(val >= 4 && val <= 6)
            btnCerto = 'Y';
        else  if(val >= 7 && val <= 9)
            btnCerto = 'B';
        else btnCerto = 'R';
    }
    /*public void  verResult(char id) {
        if(id == btnCerto)
            pontosReciclagem++;
        atualizar();
    }*/
    public  void  atualizar(){
        if(qtd < 9) {
            qtd++;
            construirJogo();
        }else {
            float pontoFinal = 0.0f;

            if(pontosReciclagem >= 0 && pontosReciclagem <= 2) pontoFinal = -0.05f;
            else if(pontosReciclagem == 3 || pontosReciclagem == 4)  pontoFinal = 0f;
            else if(pontosReciclagem >= 5 && pontosReciclagem <= 7) pontoFinal = 0.05f;
            else pontoFinal = 0.1f;

            UserService service =  RetrofitConfig.getClient().create(UserService.class);

            Call<List<Filho>> ponto = service.skill(email,nomeFilho,"rec", pontoFinal);

            ponto.enqueue(new Callback<List<Filho>>() {
                @Override
                public void onResponse(Call<List<Filho>> call, Response<List<Filho>> response) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReciclagemActivity.this);

                    builder.setTitle("Sua pontuação foi de "+ pontosReciclagem);

                    builder.setMessage("PARABÉNS, DEU CERTO");

                    builder.setNegativeButton("OK", null);

                    builder.create().show();
                }

                @Override
                public void onFailure(Call<List<Filho>> call, Throwable t) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReciclagemActivity.this);

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
