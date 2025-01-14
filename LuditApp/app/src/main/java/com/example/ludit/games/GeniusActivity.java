package com.example.ludit.games;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
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

public class GeniusActivity extends AppCompatActivity {
    Handler handler;
    ConnectionThread thread;

    ImageView btn;
    int[] sequencia;
    int[] cores = new int[4];
    int qtd, max, click;
    float pontosGenius;
    SharedPreferences preferences;
    String email, nomeFilho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genius);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        MediaPlayer mediaPlayer= MediaPlayer.create(GeniusActivity.this,R.raw.genius);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        cores[0] = R.drawable.genius_vermelho;
        cores[1] = R.drawable.genius_azul;
        cores[2] = R.drawable.genius_verde;
        cores[3] = R.drawable.genius_amarelo;

        pontosGenius = qtd = 0;
        btn = findViewById(R.id.genius);
        preferences = getApplicationContext().getSharedPreferences("minhaShared",MODE_PRIVATE);

        email = preferences.getString("email", null);
        nomeFilho = preferences.getString("nomeFilho", null);

        email  = "sasa";
        nomeFilho = "Henrique";
        descobrirDificuldade();

        click = 0;

        /* Definição da thread de conexão como cliente.
            Aqui, você deve incluir o endereço MAC do seu módulo Bluetooth.
            O app iniciará e vai automaticamente buscar por esse endereço.
            Caso não encontre, dirá que houve um erro de conexão.
         */
        thread = new ConnectionThread("98:D3:31:FD:40:2A");
        thread.start();

        /* Um descanso rápido, para evitar bugs esquisitos.*/
        try {
            Thread.sleep(1000);
        } catch (Exception E) {
            E.printStackTrace();
        }

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                Bundle bundle = msg.getData();
                byte[] data = bundle.getByteArray("data");
                String dataString= new String(data);

                Log.d("Mensagem: ", dataString.substring(0,1));

                switch (dataString.substring(0,1))
                {
                    case "R":
                        verResult(0);
                        break;
                    case "B":
                        verResult(1);
                        break;
                    case "G":
                        verResult(2);
                        break;
                    case "Y":
                        verResult(3);
                        break;
                }
            }
        };

        thread.setHandler(handler);
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

                btn.setImageResource(cores[qual]);

                btn.animate().setDuration(1500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        btn.setImageResource(R.drawable.genius_padrao);
                    }
                }).start();
            }
        };

        Runnable r  = new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i <= qtd; i++) {
                    try {
                        Message msg = new Message();
                        msg.arg1 = i;
                        Thread.sleep(2000);
                        h.sendMessage(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public void  atualizarTela() {
        if(qtd < max-1) {
            qtd++;
            if(max == 5)
                pontosGenius++;
            else
                pontosGenius += 0.5f;
            construirGenius();
        }else{

            if(qtd < max)
            {
                if(max == 5)
                    pontosGenius++;
                else
                    pontosGenius += 0.5f;
            }

            thread.cancel();

            float pontoFinal = 0.0f;

            if(pontosGenius == 0) pontoFinal = -0.05f;
            else if(pontosGenius == 1) pontoFinal = 0f;
            else if(pontosGenius == 3|| pontosGenius == 2) pontoFinal = 0.05f;
            else if(pontosGenius == 4 || pontosGenius == 5) pontoFinal = 0.1f;


            AlertDialog.Builder builder = new AlertDialog.Builder(GeniusActivity.this);
            AlertDialog alerta;
            builder.setTitle("Sua pontuação foi de "+ pontosGenius);
            builder.setMessage("PARABÉNS, DEU CERTO");
            builder.setNegativeButton("OK", null);
            alerta = builder.create();
            alerta.show();

            UserService service =  RetrofitConfig.getClient().create(UserService.class);
            Call<List<Filho>> ponto = service.skill(email,nomeFilho,"mem", pontoFinal);
            ponto.enqueue(new Callback<List<Filho>>() {
                @Override
                public void onResponse(Call<List<Filho>> call, Response<List<Filho>> response) {

                }

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
            });
        }
    }

    public void  verResult(int id) {

        final Handler h = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                final int q = msg.arg1;

                btn.setImageResource(cores[q]);
                btn.animate().setDuration(400).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        btn.setImageResource(R.drawable.genius_padrao);
                    }
                }).start();
            }
        };

        Message msg = new Message();
        msg.arg1 = id;
        h.sendMessage(msg);

        if(sequencia[click] == id && click <= qtd) {
            click++;
        }
        else if(sequencia[click] != id){
            qtd = max;
            atualizarTela();
        }

        if(click > qtd)
            atualizarTela();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.cancel();
    }
}
