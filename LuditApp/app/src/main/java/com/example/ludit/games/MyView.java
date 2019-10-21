package com.example.ludit.games;

import android.app.Activity;
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

import com.example.ludit.R;


public class MyView extends View {
    public Paint p, outerPaint;
    private Bitmap bitmap;
    private static final int RADIUS = 46;

    private int centerX, centerY, speedX, pinguimY;

    public MyView(Context context, int speedX) {
        super(context);
        p = new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setColor(Color.WHITE);

        this.speedX = speedX;

        bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.botao_abaixar);

        outerPaint = new Paint();
        outerPaint.setStyle(Paint.Style.FILL);
        outerPaint.setColor(Color.parseColor("#90caf9"));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        centerX = w / 2;
        pinguimY = h /2;
    }

    protected void onDraw(Canvas c) {
        int w = getWidth();
        centerY = getHeight()/2;
        centerX += speedX;

        int rightLimit = w - RADIUS;

        if (centerX >= rightLimit) {
            centerX = rightLimit;
            speedX *= -1;
        }
        if (centerX <= RADIUS) {
            centerX = RADIUS;
            speedX *= -1;
        }

        c.drawPaint(outerPaint);
        c.drawBitmap(bitmap, w/2, pinguimY, null);
        c.drawCircle(centerX, centerY, RADIUS, p);
        postInvalidateDelayed(200);
    }

    public void mexerPinguim(final int val) {
        pinguimY += val;
    }
}
