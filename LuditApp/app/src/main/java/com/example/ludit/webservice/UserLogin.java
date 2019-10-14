package com.example.ludit.webservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLogin {
    @SerializedName("login")
    @Expose
    protected String login;

    @SerializedName("senha")
    @Expose
    protected String senha;

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public  UserLogin(String login, String senha){
            setLogin(login);
            setSenha(senha);
        }

        public  UserLogin(){}
}
