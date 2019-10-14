package com.example.ludit.games;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;


public class MyView extends View {
    public Paint p, outerPaint;

    private static final int RADIUS = 46;

    private int centerX;
    private int centerY;
    private int speedX = 50;

    public MyView(Context context) {
        super(context);
        p = new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setColor(Color.WHITE);

        outerPaint = new Paint();
        outerPaint.setStyle(Paint.Style.FILL);
        outerPaint.setColor(Color.parseColor("#90caf9"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        centerX = w / 2;
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
        c.drawCircle(centerX, centerY, RADIUS, p);
        postInvalidateDelayed(200);
    }
}
