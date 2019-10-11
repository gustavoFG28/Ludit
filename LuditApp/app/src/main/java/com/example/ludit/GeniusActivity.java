package com.example.ludit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ludit.ui.filho.Filho;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeniusActivity extends AppCompatActivity {
    Button btnAzul, btnVermelho, btnAmarelo, btnVerde;
    Button[] btns = new Button[4];
    int[] sequencia;
    int[] cores = new int[8];
    int pontosGenius, qtd, max, click;
    SharedPreferences preferences;
    String email, nomeFilho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genius);

        btnAmarelo = (Button) findViewById(R.id.btnAmarelo);
        btnAzul = (Button) findViewById(R.id.btnAzul);
        btnVerde = (Button) findViewById(R.id.btnVerde);
        btnVermelho = (Button) findViewById(R.id.btnVermelho);

        btns[0] = btnAmarelo;  cores[0] = Color.parseColor("#f57f17");
        btns[1] = btnAzul;     cores[1] = Color.parseColor("#01579b");
        btns[2] = btnVermelho; cores[2] = Color.parseColor("#b71c1c");
        btns[3] = btnVerde;    cores[3] = Color.parseColor("#1b5e20");

        cores[4] = Color.parseColor("#FFFF00");
        cores[5] = Color.parseColor("#03a9f4");
        cores[6] = Color.parseColor("#ef5350");
        cores[7] = Color.parseColor("#c8e6c9");

        pontosGenius = qtd = 0;

        preferences = getApplicationContext().getSharedPreferences("minhaShared",MODE_PRIVATE);

        email = preferences.getString("email", null);
        nomeFilho = preferences.getString("nomeFilho", null);

        email  = "lucas@gmail.com";
        nomeFilho = "Henrique";
        descobrirDificuldade();

        click = 0;

        for(int i = 0; i< btns.length; i++)
        {
            final int id = i;
            btns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verResult(id);
                    atualizarTela();
                }
            });
        }
    }

    public  void  descobrirDificuldade() {
        final AlertDialog dialog;
        AlertDialog.Builder builder =  new AlertDialog.Builder(GeniusActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_dificuldade, null);
        builder.setView(dialogView);
        dialog = builder.create();

        ((Button)dialogView.findViewById(R.id.btnFacil)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                max = 5;
                sequencia = new int[max];
                dialog.dismiss();
                construirGenius();
            }
        });

        ((Button)dialogView.findViewById(R.id.btnDificil)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                max = 10;
                sequencia = new int[max];
                dialog.dismiss();
                construirGenius();
            }
        });

        dialog.setTitle("Escolha da Dificuldade");
        dialog.show();
    }

    public synchronized void  construirGenius() {
        click = 0;
        Random random = new Random();
        int val = random.nextInt(4);
        sequencia[qtd] = val;

        final Handler h = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                final int qual = sequencia[msg.arg1];

                btns[qual].setBackgroundColor(cores[qual]);

                btns[qual].animate().setDuration(4000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        btns[qual].setBackgroundColor(cores[qual + 4]);
                    }
                }).start();
            }
        };
        Runnable r  = new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i <= qtd; i++) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    h.sendEmptyMessage(i);
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public void  atualizarTela() {
        if(qtd < max) {
            qtd++;
            construirGenius();
        }else{
            Toast toast = Toast.makeText(this, pontosGenius, Toast.LENGTH_LONG);
            toast.show();
            /*float pontoFinal = 0.0f;

            if(pontosGenius >= 0 && pontosGenius <=2) pontoFinal = -0.05f;
            else if(pontosGenius == 4 || pontosGenius == 5) pontoFinal = 0.05f;
            else if(pontosGenius  >= 6 || pontosGenius <=8) pontoFinal = 0.1f;
            else if(pontosGenius  == 9 || pontosGenius == 10) pontoFinal = 0.15f;

            UserService service =  RetrofitConfig.getClient().create(UserService.class);

            Call<List<Filho>> ponto = service.skill(email,nomeFilho,"mem", pontoFinal);

            ponto.enqueue(new Callback<List<Filho>>() {
                @Override
                public void onResponse(Call<List<Filho>> call, Response<List<Filho>> response) { }

                @Override
                public void onFailure(Call<List<Filho>> call, Throwable t) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GeniusActivity.this);

                    AlertDialog alerta;

                    builder.setTitle("Erro com a pontuação");

                    builder.setMessage("Não foi possível enviar sua pontuação");

                    builder.setNegativeButton("OK", null);

                    alerta = builder.create();

                    alerta.show();
                }
            });*/

        }
    }

    public void  verResult(int id) {
        if(sequencia[click] == id && click <= qtd) {
            click++;
        }else if(click > qtd) {
            qtd++;
            construirGenius();
        }else {
            qtd = max;
        }
    }
}
