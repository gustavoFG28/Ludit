package com.example.ludit.webservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Filho {

    @SerializedName("data")
    @Expose
    protected String data;

    @SerializedName("texto")
    @Expose
    protected String texto;

    @SerializedName("imgPerfil")
    @Expose
    protected String imgPerfil;

    @SerializedName("nome")
    @Expose
    protected String nome;

    @SerializedName("def")
    @Expose
    protected String def;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getImgPerfil() {
        return imgPerfil;
    }

    public void setImgPerfil(String imgPerfil) {
        this.imgPerfil = imgPerfil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public Filho(String data, String texto, String imgPerfil, String nome, String def) {
        setData(data);
        setTexto(texto);
        setDef(def);
        setImgPerfil(imgPerfil);
        setNome(nome);
    }

    public  Filho(){}
}
