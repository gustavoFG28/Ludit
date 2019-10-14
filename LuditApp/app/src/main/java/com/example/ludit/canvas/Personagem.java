package com.example.ludit.canvas;

import android.content.Context;

public class Personagem extends Sprite {

    private final int posicaoInicialY;
    private final int posicaoInicialX;
    private int y;
    private int x;
    private int src;
    private int srcAbaixado;
    private boolean emPe;
    private boolean inicioPulo;

    public Personagem(int x, int y, int src, int srcAbaixado, Context context)
    {
        super(context);
        this.x=posicaoInicialX=x;
        this.y=posicaoInicialY=y;
        this.src=src;
        setBmp(src);
        this.srcAbaixado=srcAbaixado;
        this.emPe=true;
    }

    public void setInicioPulo(boolean pulo)
    {
        this.inicioPulo = true;
    }

    public boolean devePular()
    {
        if(y<20 && inicioPulo)
            return true;
        else
        {
            inicioPulo=false;
            if(y-5 < posicaoInicialY)
                return false;
            return true;
        }
    }

    public void pular()
    {
        emPe=true;
        if(inicioPulo)
        {
            y+=5;
            setY(y);
        }
        else
        {
            y-=5;
            setY(y);
        }
    }

    public void abaixar()
    {
        emPe=false;
    }

    public void ficarDePe()
    {
        emPe=true;
    }

    @Override
    protected int getDrawableId()
    {
        if(emPe)
            return src;

        return srcAbaixado;
    }
}
