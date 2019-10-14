package com.example.ludit.canvas;

import android.content.Context;

public class Obstaculo extends Sprite {

    private final int posicaoInicialY;
    private final int posicaoInicialX;
    private int y;
    private int x;
    private int src;

    public Obstaculo(int x, int y, int src, Context context)
    {
        super(context);
        this.src = src;
        setBmp(src);
        this.x=posicaoInicialX=x;
        this.y=posicaoInicialY=y;
    }

    public void mover()
    {
        x-=5;
        setX(x);
    }

    @Override
    protected int getDrawableId()
    {
        return src;
    }

}
