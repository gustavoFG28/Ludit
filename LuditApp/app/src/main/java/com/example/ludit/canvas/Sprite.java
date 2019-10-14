package com.example.ludit.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public abstract class Sprite {

    private int x;
    private int y;

    private Context context;
    private Bitmap bmp;

    public Sprite(Context context)
    {
        this.context = context;
        //bmp = getBitmapFromResource(getDrawableId());
    }

    protected abstract int getDrawableId();

    protected Bitmap getBitmapFromResource(int src) {
        return BitmapFactory.decodeResource(context.getResources(), src);
    }

    public void setBmp(Bitmap bmp)
    {
        this.bmp = bmp;
    }

    public void setBmp(int src)
    {
        this.bmp = getBitmapFromResource(src);
    }

    public int getWidth()
    {
        return bmp.getWidth();
    }

    public int getHeight()
    {
        return bmp.getHeight();
    }

    public void draw(Canvas c)
    {
        c.drawBitmap(bmp, x, y, null);
    }

    public int getAreaX()
    {
        return x + getWidth();
    }

    public int getAreaY()
    {
        return y + getHeight();
    }

    public boolean houveColisao(Sprite sprite)
    {
        if(houveColisaoX(sprite)&&houveColisaoY(sprite))
            return true;
        return false;
    }

    private boolean houveColisaoX(Sprite sprite)
    {
        if(sprite.getX() < this.getAreaX() && sprite.getAreaX() > this.getX())
            return true;
        return false;
    }

    public boolean houveColisaoY(Sprite sprite)
    {
        if(sprite.getY() < this.getAreaY() && sprite.getAreaY() > this.getY())
            return true;
        return false;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public void setX(int x) {  this.x = x; }

    public void setY(int y) { this.y = y; }
}
