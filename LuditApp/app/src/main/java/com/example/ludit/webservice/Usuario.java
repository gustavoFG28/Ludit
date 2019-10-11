package com.example.ludit.webservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuario {
    @SerializedName("email")
    @Expose
    protected String email;

    @SerializedName("nome")
    @Expose
    protected String nome;

    @SerializedName("senha")
    @Expose
    protected String senha;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public  Usuario(String email, String nome, String senha){
        setEmail(email);
        setNome(nome);
        setSenha(senha);
    }

    public  Usuario(){}
}
