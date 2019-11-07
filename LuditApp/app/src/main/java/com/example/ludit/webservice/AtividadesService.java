package com.example.ludit.webservice;

import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AtividadesService {

    @GET("youtube/v3/playlistItems")
    Call<PlaylistItemListResponse> getYouTubeVideos(@Query("key") String apiKey,
                                               @Query("playlistId") String playlistId,
                                               @Query("part") String videoPart);
}
