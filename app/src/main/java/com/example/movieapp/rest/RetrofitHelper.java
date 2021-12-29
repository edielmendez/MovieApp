package com.example.movieapp.rest;

import com.example.movieapp.utils.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    public Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(Constant.HOST_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
