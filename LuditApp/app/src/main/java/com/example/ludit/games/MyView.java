package com.example.ludit.games;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import androidx.appcompat.app.AlertDialog;

import com.example.ludit.R;
import com.example.ludit.atividades.AtividadesActivity;
import com.example.ludit.atividades.JogosActivity;
import com.example.ludit.webservice.Filho;
import com.example.ludit.webservice.RetrofitConfig;
import com.example.ludit.webservice.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyView extends View {
    public Paint p, outerPaint;
    private Bitmap bitmap;
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

        bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.botao_abaixar);

        outerPaint = new Paint();
        outerPaint.setStyle(Paint.Style.FILL);
        outerPaint.setColor(Color.parseColor("#90caf9"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        centerX = w / 2;
        pinguimX = w/2;
        pinguimY = h /2;
    }

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
            c.drawPaint(outerPaint);
            c.drawBitmap(bitmap, pinguimX, pinguimY, null);
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
