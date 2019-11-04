package com.example.ludit.atividades;

public class Video {

    public Video(String id, String titulo, String url) {
        this.id = id;
        this.titulo = titulo;
        this.url = url;
    }

    String id;
    String titulo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String url;


}
