package com.beHealthyforLife.app;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SenopatiApi {

    @Headers({
            "Content-Type: application/json"
    })
    @POST("https://senopati-elysia.vercel.app/api/chat")
    Call<SenopatiResponse> sendChat(@Body SenopatiRequest request);
}
