package com.example.movieapp.rest;

import com.example.movieapp.obj.Movie;
import com.example.movieapp.responses.MovieListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestApiService {
    private Retrofit retrofit;
    private RetrofitHelper helper;
    private RestApiClient client;
    public RestApiService(){
        helper = new RetrofitHelper();
        retrofit = helper.getRetrofit();
        client = retrofit.create(RestApiClient.class);
    }
    public Call<MovieListResponse> callAllMovies() {
        return client.getAllMovies("8172931");
    }

    public Call<Movie> callDetailsMovie(String movieId) {
        return client.getMovie(movieId);
    }
}
