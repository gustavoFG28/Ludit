package com.example.ludit;

import com.google.gson.annotations.SerializedName;

public class Song {
    @SerializedName("id")
    protected  long id;

    @SerializedName("artista")
    protected  String artista;

    @SerializedName("titulo")
    protected  String titulo;

    @SerializedName("link")
    protected  String link;

    public Song(long songID, String songTitle, String songArtist, String link) {
        id =songID;
        titulo =songTitle;
        artista =songArtist;
        this.link = link;
    }

    public  String getLink() {
        return  link;
    }


    public long getId() {
        return id;
    }

    public String getArtista() {
        return artista;
    }

    public String getTitulo() {
        return titulo;
    }
}
