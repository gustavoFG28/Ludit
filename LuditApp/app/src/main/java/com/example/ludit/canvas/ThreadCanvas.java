package com.example.ludit.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.ludit.R;

public class ThreadCanvas extends Thread {
    private Personagem personagem;
    private Botao botaoPular;
    private Botao botaoAbaixar;
    private Obstaculo obstaculoVoador;
    private Obstaculo obstaculoTerrestre;

    private Handler handler;
    private SurfaceHolder holder;
    boolean running;
    private Canvas c;
    private Context context;

    public void setHolder(SurfaceHolder holder)
    {
        this.holder = holder;
    }

    public ThreadCanvas(SurfaceHolder holder, Context context, Handler handler)
    {
        running = false;
        this.context = context;
        this.holder = holder;
        this.handler = handler;

        personagem = new Personagem(10, 100, R.drawable.personagem_jogo, R.drawable.personagem_jogo_abaixado, context);
        botaoPular = new Botao(10, 10, R.drawable.botao_pular, context);
        botaoAbaixar = new Botao(10, 20, R.drawable.botao_abaixar, context);
        obstaculoVoador = new Obstaculo(100, 50, R.drawable.obstaculo_voador, context);
        obstaculoTerrestre = new Obstaculo(100, 100, R.drawable.obstaculo_terrestre, context);
    }

    public void setRunning(boolean run)
    {
        running = run;
    }

    public void doStart()
    {
        synchronized(holder){}
    }

    public void onTouch(MotionEvent event)
    {
        int x = (int) (event.getX());
        int y = (int) (event.getY());
        if(botaoPular.houveColisao(new Botao(x,y,R.drawable.botao_pular,context)))
        {
            personagem.setInicioPulo(true);
        }
	    else if(botaoAbaixar.houveColisao(new Botao(x,y,R.drawable.botao_abaixar,context)))
        {
            personagem.abaixar();
        }
    }

    @Override
    public void run()
    {
        while(running)
        {
            try{
                initializeCanvas();
                update();
                draw(c);
                pausar();
            } finally {

                if(c != null)
                    holder.unlockCanvasAndPost(c);
            }
        }
    }

    private void initializeCanvas()
    {
        c = holder.lockCanvas();
    }

    private void update()
    {
        if(personagem.devePular())
            personagem.pular();
        if(personagem.houveColisao(obstaculoVoador) || personagem.houveColisao(obstaculoTerrestre))
        {}//perdeu
        obstaculoVoador.mover();
        obstaculoTerrestre.mover();
    }

    private void draw(Canvas c)
    {
        if(c!=null) {
            c.drawColor(Color.WHITE);
            personagem.draw(c);
            botaoPular.draw(c);
            botaoAbaixar.draw(c);
            obstaculoVoador.draw(c);
            obstaculoTerrestre.draw(c);
        }
    }

    private void pausar()
    {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e){ e.printStackTrace(); }
    }
}
