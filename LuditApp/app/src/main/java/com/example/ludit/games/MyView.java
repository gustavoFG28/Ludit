package com.example.ludit.games;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Message;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.example.ludit.R;
import com.example.ludit.atividades.AtividadesActivity;
import com.example.ludit.bluetooth.ConnectionThread;
import com.example.ludit.webservice.Filho;
import com.example.ludit.webservice.RetrofitConfig;
import com.example.ludit.webservice.UserService;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyView extends View {

    private ConnectionThread thread;
    private Handler handler;

    public Paint p;
    private Bitmap bitmap, fundo;
    Context cnt;
    int i, pontos, min;
    private static final int RADIUS = 46;
    String nomeFilho, email;
    boolean primeiro;

    private int centerX, centerY, speedX, pinguimY, pinguimX;

    public MyView(Context context, int speedX, String nomeFilho, String email) {
        super(context);
        p = new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setColor(Color.WHITE);

        this.speedX = speedX;
        this.email= email;
        primeiro = true;
        this.nomeFilho = nomeFilho;
        cnt = context;
        i = 0;

        fundo = BitmapFactory.decodeResource(context.getResources(), R.drawable.fundo_pinguim);
        bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.pinguim);

        thread =  new ConnectionThread("98:D3:31:FD:40:2A");
        thread.start();
        /* Um descanso rápido, para evitar bugs esquisitos.*/
        try {
            Thread.sleep(1000);
        } catch (Exception E) {
            E.printStackTrace();
        }

        handler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {

                Bundle bundle = msg.getData();
                byte[] data = bundle.getByteArray("data");
                String dataString= new String(data);

                Log.d("Mensagem: ", dataString.substring(0,1));

                if(dataString.substring(0,1).equals("R")) {
                    mexerPinguim(-100);
                    Log.d("Mensagem: ", "pular");
                }
            }
        };

        thread.setHandler(handler);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        centerX = w/2;
        pinguimX = w/2;
        pinguimY = h /2;
    }

    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas c) {
        centerY = getHeight()/2;
        centerX += speedX;

        if(i < 6 && pinguimY != getHeight()/2)
           i++;
        else if(i == 6) {
            i = 0;
            pinguimY = getHeight()/2;
        }

        int rightLimit = getWidth() - RADIUS;

        if (centerX >= rightLimit) {
            centerX = rightLimit;
            speedX *= -1;
        }
        if (centerX <= RADIUS) {
            centerX = RADIUS;
            speedX *= -1;
        }

        if(!primeiro)
        {
            if(min < 10 )
                min++;
            else {
                pontos++;
                min = 0;
            }
        }

        if(pinguimY == centerY && !primeiro && centerX == 646)
            perdeu();
        else
        {
            c.drawBitmap(fundo,null, new RectF(0, 0, getWidth(), getHeight()), null);

            int largura = Math.round(getWidth() * 20 / 100);
            int altura =  Math.round(getHeight() * 40 / 100);
            c.drawBitmap(Bitmap.createScaledBitmap(bitmap, largura, altura, false), pinguimX - largura / 2, pinguimY - altura / 2, null);
            c.drawCircle(centerX, centerY, RADIUS, p);
            postInvalidateDelayed(200);
        }
    }

    public void mexerPinguim(final int val) {
        if(i == 0) {
            pinguimY += val;
            i++;
        }
        primeiro = false;
    }

    public void  perdeu() {

        thread.cancel();

        float pontoFinal = 0.0f;

        if(pontos >= 0 && pontos <=2) pontoFinal = -0.05f;
        else if(pontos >= 4 || pontos <= 6) pontoFinal = 0.05f;
        else if(pontos  >=  7|| pontos <= 10) pontoFinal = 0.1f;
        else if(pontos  >= 10) pontoFinal = 0.15f;

        final  AlertDialog.Builder builder = new AlertDialog.Builder(cnt);

        UserService service =  RetrofitConfig.getClient().create(UserService.class);

        final Call<List<Filho>> ponto = service.skill(email,nomeFilho,"ref", pontoFinal);

        ponto.enqueue(new Callback<List<Filho>>() {
            @Override
            public void onResponse(Call<List<Filho>> call, Response<List<Filho>> response) {
                builder.setTitle("O jogo acabou");

                final AlertDialog dialog;

                builder.setMessage("Sua pontuação foi de "+ pontos + ", parabéns");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(cnt, AtividadesActivity.class);
                        cnt.startActivity(i);
                        ((Activity)cnt).finish();
                    }
                });

                dialog =  builder.create();
                dialog.show();
            }

            @Override
            public void onFailure(Call<List<Filho>> call, Throwable t) {
                builder.setTitle("Erro com a pontuação");

                builder.setMessage("Não foi possível enviar sua pontuação");

                builder.setNegativeButton("OK", null);

                builder.create().show();
            }
        });
    }
}
