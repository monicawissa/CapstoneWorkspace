package com.example.Workspace.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class client {
    Retrofit retrofit=null;

    public Retrofit getRetrofit() {
        if(retrofit==null){
            String base_url = "https://node-hangin.herokuapp.com";
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }        return retrofit;
    }
}
