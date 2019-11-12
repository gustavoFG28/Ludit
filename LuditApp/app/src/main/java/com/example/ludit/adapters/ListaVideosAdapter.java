package com.example.ludit.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ludit.R;
import com.example.ludit.atividades.Video;

import java.io.InputStream;
import java.util.List;

public class ListaVideosAdapter extends ArrayAdapter {

    private int layout;
    private Context context;
    private List<Video> videos = null;

    public ListaVideosAdapter(Context context,  List<Video> listaImagens) {
        super(context,0 , listaImagens);
        this.context = context;
        this.videos = listaImagens;
    }

    @Override
    public View getView(int position, View viewAtual, ViewGroup parent) {

        if(viewAtual == null)
            viewAtual = LayoutInflater.from(context).inflate(R.layout.layout_videos, null);

        Video qualVideo = videos.get(position);

        ImageView img = viewAtual.findViewById(R.id.imgVideo);
        new DownloadImageTask(img)
                .execute(qualVideo.getUrl());



        TextView txt = viewAtual.findViewById(R.id.playerTitulo);
        txt.setText(qualVideo.getTitulo());

        return  viewAtual;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
