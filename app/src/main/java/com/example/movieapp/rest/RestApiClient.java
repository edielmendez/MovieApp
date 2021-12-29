package com.example.movieapp.rest;

import com.example.movieapp.obj.Movie;
import com.example.movieapp.responses.MovieListResponse;
import com.example.movieapp.utils.Constant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestApiClient {
    @GET("list/{id}" + Constant.API_KEY)
    Call<MovieListResponse> getAllMovies(@Path("id") String idList);

    @GET("movie/{movie_id}" + Constant.API_KEY)
    Call<Movie> getMovie(@Path("movie_id") String movieId);
}
