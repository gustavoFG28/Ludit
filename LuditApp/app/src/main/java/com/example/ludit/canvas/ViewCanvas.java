package com.example.ludit.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ViewCanvas extends SurfaceView implements SurfaceHolder.Callback {

    Context context;
    ThreadCanvas thread;

    public ViewCanvas(Context context) {

        super(context);

        setAlpha(0);

        this.context = context;

        getHolder().addCallback(this);

        thread = new ThreadCanvas(getHolder(), context, new Handler(){

            @Override
            public void handleMessage(Message m){

                if(m.what == 1) //perdeu
                {
                    //avisar que perdeu
                }

            }

        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        //if(surfaceHolder.equals(getHolder()))
            //Log.d("é igual", "AAAAAAA");

        Canvas ca = surfaceHolder.lockCanvas();
        thread.setHolder(surfaceHolder);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) { }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        thread.setRunning(false);
        while(retry)
        {
            try{
                thread.join();
                retry = false;

            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        try{
            thread.onTouch(event);

        } catch(Exception e){ e.printStackTrace(); }

        return true;
    }
}
