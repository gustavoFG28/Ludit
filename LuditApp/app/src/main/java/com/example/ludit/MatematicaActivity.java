package com.example.ludit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MatematicaActivity extends AppCompatActivity {
    TextView tvVisor;
    Button btnAzul, btnVermelho, btnAmarelo, btnVerde;
    Button[] btns = new Button[4];
    int pontosMat, botaoCerto, qtd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matematica);

        tvVisor = (TextView) findViewById(R.id.tvConta);
        btnAmarelo = (Button) findViewById(R.id.btnAmarelo);
        btnAzul = (Button) findViewById(R.id.btnAzul);
        btnVerde = (Button) findViewById(R.id.btnVerde);
        btnVermelho = (Button) findViewById(R.id.btnVermelho);
        qtd = 0;

        btns[0] = btnAmarelo;
        btns[1] = btnAzul;
        btns[2] = btnVermelho;
        btns[3] = btnVerde;

        construirConta();

        btnVermelho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verResult(btnVermelho.getId());
                atualizarTela();
            }
        });

        btnVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verResult(btnVerde.getId());
                atualizarTela();
            }
        });

        btnAzul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verResult(btnAzul.getId());
                atualizarTela();
            }
        });

        btnAmarelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verResult(btnAmarelo.getId());
                atualizarTela();
            }
        });
    }

    public  void   atualizarTela(){
        if(qtd < 10)
        {
            qtd++;
            construirConta();
        }else
        {
            //mostrarResultado e jogar pontos no BD
        }
    }

    public boolean verResult(int id){
       if(btns[botaoCerto].getId() == id)
           pontosMat++;
       else
           pontosMat--;
        return  false;
    }

    public void construirConta(){
        Random random = new Random();
        int result = 0;

        int n1 = random.nextInt(11);
        int n2 = random.nextInt(11);

        int sinal = random.nextInt(2);
        botaoCerto = random.nextInt(5);

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
            if(botaoCerto != i)
                while ((v =random.nextInt(21)) != result)
                {
                    btns[i].setText(v + " ");
                    break;
                }
            else
                btns[i].setText(result + " ");
    }


}
