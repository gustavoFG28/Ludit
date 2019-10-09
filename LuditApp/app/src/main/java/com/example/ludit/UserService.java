package com.example.ludit;

import com.example.ludit.ui.filho.Filho;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("cadastrarUsuario")
    Call<List<Usuario>> inserirUsuario(@Body Usuario user);

    @POST("cadastrarFilho/{email}")
    Call<List<Filho>> inserirFilho(@Path("email") String email, @Body Filho filho);

    @POST("loginUsuario")
    Call<List<Usuario>> logar(@Body UserLogin login);

    @GET("buscarFilhos/{email}")
    Call<List<Filho>> buscarFilhos(@Path("email") String email);

    @DELETE("excluiConta/{email}")
    Call<Void> excluirConta(@Path("email") String email);

    @PATCH ("alteraNome/{email}")
    Call<Void> alteraNome(@Path("email") String email, @Body String nome);

    @PATCH ("alteraEmail/{email}")
    Call<Void> alteraEmail(@Path("email") String email, @Body String novoEmail);

    @PATCH ("alteraSenha/{email}")
    Call<Void> alteraSenha(@Path("email") String email, @Body String novaSenha);

    @POST("getUser/{email}")
    Call<List<Usuario>> verificaUser(@Path("email") String email, @Body String senha);
}
