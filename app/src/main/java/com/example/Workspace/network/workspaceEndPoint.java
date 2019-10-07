package com.example.Workspace.network;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface workspaceEndPoint {
    @GET("https://node-hangin.herokuapp.com/api/places")
    Call<List<Workspace>> getplaces();
    @POST("https://node-hangin.herokuapp.com/api/users/login")
    Call<Token> login(
            @Body JsonObject body);

    @GET("https://node-hangin.herokuapp.com/api/users/me")
    Call<Account> getAccount(
            //application/json
            @Header("Content-Type")String Content,
            @Header("x-auth-token")String token
    );

    @POST("https://node-hangin.herokuapp.com/api/users/register")
    Call<Token> register(
            @Body JsonObject body);
}
