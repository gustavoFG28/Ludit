package com.example.ludit.webservice;

import com.example.ludit.atividades.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AtividadesService {

    @GET("videos")
    Call<List<Video>> buscarVideos();
}
