package com.example.ludit.games;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

public class FormasActivity extends AppCompatActivity {

    ConnectionThread thread;
    private Handler handler;

    ImageView img;
    final int qtdFormas = 24;
    int[] imagens;
    int[] respostas;

    int btnCerto, pontosForma, qtd;
    ImageView btnAzul, btnVermelho, btnAmarelo, btnVerde;
    ImageView[] btns = new ImageView[4];

    String nomeFilho, email, jogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formas);
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

        img = (ImageView) findViewById(R.id.imgConjunto);

        btnAmarelo = (ImageView) findViewById(R.id.btnAmarelo);
        btnAzul = (ImageView) findViewById(R.id.btnAzul);
        btnVerde = (ImageView) findViewById(R.id.btnVerde);
        btnVermelho = (ImageView) findViewById(R.id.btnVermelho);

        btns[0] = btnAmarelo;
        btns[1] = btnAzul;
        btns[2] = btnVermelho;
        btns[3] = btnVerde;

        email = getApplicationContext().getSharedPreferences("minhaShared", MODE_PRIVATE).getString("email", "");
        nomeFilho = getApplicationContext().getSharedPreferences("filhoShared", MODE_PRIVATE).getString("nome", "");

        imagens = new int[qtdFormas];
        respostas = new int[qtdFormas];

        for(int i = 0; i< imagens.length; i++)
        {
            imagens[i] = getResources().getIdentifier("per_"+(i+1), "drawable", getPackageName());
            respostas[i] = getResources().getIdentifier("res_"+(i+1), "drawable", getPackageName());
        }

        construirJogo();

        /*for(int i = 0; i< btns.length; i++)
        {
            final int id = i;
            btns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verResult(id);
                }
            });
        }*/

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

                switch(dataString.substring(0,1))
                {
                    case "Y":
                        verResult(0);
                        break;
                    case "B":
                        verResult(1);
                        break;
                    case "G":
                        verResult(2);
                        break;
                    case "R":
                        verResult(3);
                        break;
                }
            }
        };

        thread.setHandler(handler);
    }

    public  void  construirJogo() {
        Random random = new Random();
        int val = random.nextInt(qtdFormas);

        img.setImageResource(imagens[val]);

        btnCerto = random.nextInt(4);

        int v;

        for(int i = 0; i < btns.length; i++)
            if(btnCerto != i)
                while ((v =random.nextInt(qtdFormas)) != val)
                {
                    btns[i].setBackground( ContextCompat.getDrawable(FormasActivity.this, respostas[v]));
                    break;
                }
            else
                btns[i].setBackground( ContextCompat.getDrawable(FormasActivity.this, respostas[val]));
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

            thread.cancel();

            float pontoFinal = 0.0f;

            if(pontosForma >= 0 && pontosForma <= 2) pontoFinal = -0.05f;
            else if(pontosForma == 3 || pontosForma == 4)  pontoFinal = 0f;
            else if(pontosForma >= 5 && pontosForma <= 7) pontoFinal = 0.05f;
            else pontoFinal = 0.1f;

            UserService service =  RetrofitConfig.getClient().create(UserService.class);

            Call<List<Filho>> ponto = service.skill(email,nomeFilho,"rac", pontoFinal);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.cancel();
    }
}
