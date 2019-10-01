package com.example.ludit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("cadastrarUsuario")
    Call<List<Usuario>> inserirUsuario(@Body Usuario user);

    @POST("cadastrarFilho/{email}")
    Call<List<Filho>> inserirFilho(@Path("email") String email, @Body Filho filho);
}
