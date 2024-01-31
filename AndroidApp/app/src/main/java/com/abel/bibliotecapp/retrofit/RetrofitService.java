package com.abel.bibliotecapp.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private Retrofit retrofit;

    public RetrofitService(){
        inicializeRetrofit();
    }

    private void inicializeRetrofit() {
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.3.158:9000")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }
}
