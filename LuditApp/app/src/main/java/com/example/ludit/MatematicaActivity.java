package com.example.ludit;

import androidx.appcompat.app.AlertDialog;
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
    AlertDialog dialog;
    int pontosMat, botaoCerto, qtd, max = 0;
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

        descobrirDificuldade();

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
            tvVisor.setText(pontosMat + " ");
        }
    }

    public boolean verResult(int id){
       if(btns[botaoCerto].getId() == id)
           pontosMat++;
       else
           if(pontosMat > 0)
                pontosMat--;
        return  false;
    }

    public  void  descobrirDificuldade() {
        AlertDialog.Builder builder =  new AlertDialog.Builder(MatematicaActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_dificuldade, null);
        builder.setView(dialogView);
        dialog = builder.create();
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
}
