package com.example.ludit;

import com.example.ludit.ui.filho.Filho;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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
}
