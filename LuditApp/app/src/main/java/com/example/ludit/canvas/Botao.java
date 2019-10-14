package com.example.ludit.canvas;

import android.content.Context;

public class Botao extends Sprite {
    private int src;

    public Botao(int x, int y, int src, Context context)
    {
        super(context);
        setX(x);
        setY(y);
        this.src=src;
        setBmp(src);
    }


    @Override
    protected int getDrawableId()
    {
        return src;
    }
}
