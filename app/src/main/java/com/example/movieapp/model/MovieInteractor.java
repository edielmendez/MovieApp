package com.example.movieapp.model;

import com.example.movieapp.interfaces.MovieInteractorInterface;
import com.example.movieapp.interfaces.MoviePresenterInterface;
import com.example.movieapp.obj.Movie;
import com.example.movieapp.responses.MovieListResponse;
import com.example.movieapp.rest.RestApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieInteractor implements MovieInteractorInterface {
    private MoviePresenterInterface presenter;
    private RestApiService service;
    public MovieInteractor(MoviePresenterInterface presenter) {
        this.presenter = presenter;
        this.service = new RestApiService();
    }

    @Override
    public void getAllMovies() {
        service.callAllMovies().enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                presenter.showMovies(response.body().getItems());
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                presenter.invalidResponse(t.getMessage());
            }
        });
    }
}
