package com.example.ludit.games;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class MatematicaActivity extends AppCompatActivity {

    ConnectionThread thread;
    Handler handler;

    TextView tvVisor;
    Button btnAzul, btnVermelho, btnAmarelo, btnVerde;
    Button[] btns = new Button[4];

    int pontosMat, botaoCerto, qtd, max = 0;
    SharedPreferences preferences;
    String email, nomeFilho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matematica);
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

        MediaPlayer mediaPlayer= MediaPlayer.create(MatematicaActivity.this,R.raw.matematica);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        tvVisor = (TextView) findViewById(R.id.tvConta);

        qtd = 0;

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

        descobrirDificuldade();

        /*for(int i = 0; i< btns.length; i++)
        {
            final int id = i;
            btns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verResult(btns[id].getId());
                    atualizarTela();
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

                switch (dataString.substring(0,1))
                {
                    case "Y":
                        verResult(0);
                        atualizarTela();
                        break;
                    case "B":
                        verResult(1);
                        atualizarTela();
                        break;
                    case "R":
                        verResult(2);
                        atualizarTela();
                        break;
                    case "G":
                        verResult(3);
                        atualizarTela();
                        break;
                }
            }
        };

        thread.setHandler(handler);
    }

    public  void   atualizarTela(){
        if(qtd < 9) {
            qtd++;
            construirConta();
        }else {

            thread.cancel();

            float pontoFinal = 0.0f;

            if(pontosMat >= 0 && pontosMat <= 2) pontoFinal = -0.05f;
            else if(pontosMat == 4 || pontosMat == 5) pontoFinal = 0.05f;
            else if(pontosMat  >= 6 || pontosMat <= 8) pontoFinal = 0.1f;
            else if(pontosMat  == 9 || pontosMat == 10) pontoFinal = 0.15f;

            UserService service =  RetrofitConfig.getClient().create(UserService.class);

            Call<List<Filho>> ponto = service.skill(email,nomeFilho,"mat", pontoFinal);

            ponto.enqueue(new Callback<List<Filho>>() {
                @Override
                public void onResponse(Call<List<Filho>> call, Response<List<Filho>> response) { }

                @Override
                public void onFailure(Call<List<Filho>> call, Throwable t) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MatematicaActivity.this);

                    AlertDialog alerta;

                    builder.setTitle("Erro com a pontuação");

                    builder.setMessage("Não foi possível enviar sua pontuação");

                    builder.setNegativeButton("OK", null);

                    alerta = builder.create();

                    alerta.show();
                }
            });

            tvVisor.setText("Parabéns, sua pontuação foi de "+pontosMat + " ");
        }
    }

    public void verResult(int id){
       if(botaoCerto == id) {
           pontosMat++;
           Log.d("Mensagem: ", "acertou");
           //animação feliz
       }
       else if(pontosMat > 0) {
                Log.d("Mensagem: ", "errou");
               //animacao triste
           }
    }

    public  void  descobrirDificuldade() {
        final AlertDialog dialog;
        AlertDialog.Builder builder =  new AlertDialog.Builder(MatematicaActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_dificuldade, null);
        builder.setView(dialogView);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        ((Button)dialogView.findViewById(R.id.btnFacil)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                max = 5;
                dialog.dismiss();
                construirConta();
            }
        });

        ((Button)dialogView.findViewById(R.id.btnDificil)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                max = 10;
                dialog.dismiss();
                construirConta();
            }
        });


        dialog.setTitle("Escolha da Dificuldade");
        dialog.show();
    }

    public void construirConta(){
        Random random = new Random();
        int result = 0, n1 = -1, n2 = -1;

        if(max > 0)
        {
            n1 = random.nextInt(max + 1);
            n2 = random.nextInt(max + 1);
        }

        int sinal = random.nextInt(2);
        botaoCerto = random.nextInt(4);

        int v;

        switch (sinal){
            case 0:
                result = n1 + n2;
                tvVisor.setText(n1 + " + "+ n2); break;
            case 1:
                result = (n1 < n2)? n2-n1: n1-n2;
                tvVisor.setText((n1 < n2)? n2+ " - "+ n1: n1+ " - "+ n2 ); break;
        }

        for(int i = 0; i < btns.length; i++)
            if(botaoCerto != i && max > 0)
                while ((v =random.nextInt(2*max)) != result)
                {
                    btns[i].setText(v + " ");
                    break;
                }
            else
                btns[i].setText(result + " ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.cancel();
    }
}
