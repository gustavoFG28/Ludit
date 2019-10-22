package com.example.ludit.webservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Habilidade {
    @SerializedName("nome")
    @Expose
    protected String nome;

    @SerializedName("porcentagem")
    @Expose
    protected float porcentagem;

    public String getNome() {
        return nome;
    }

    public float getPorcentagem(){return porcentagem;}

    public void setNome(String nome){this.nome = nome;}

    public void setPorcentagem(float porcentagem){this.porcentagem = porcentagem;}

    public Habilidade(){}

    public Habilidade(String nome, float porcentagem)
    {
        this.nome = nome;
        this.porcentagem = porcentagem;
    }
}
