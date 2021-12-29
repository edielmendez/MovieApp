package com.example.movieapp.model;

import com.example.movieapp.interfaces.DetailsMovieInteractorInterface;
import com.example.movieapp.interfaces.DetailsMoviePresenterInterface;
import com.example.movieapp.obj.Movie;
import com.example.movieapp.rest.RestApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovieInteractor implements DetailsMovieInteractorInterface {
    private DetailsMoviePresenterInterface presenter;
    private RestApiService service;

    public DetailMovieInteractor(DetailsMoviePresenterInterface presenter){
        this.presenter = presenter;
        this.service = new RestApiService();
    }

    @Override
    public void getMovie(String movieId) {
        service.callDetailsMovie(movieId).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                presenter.showMovie(response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                presenter.invalidResponse(t.getMessage());
            }
        });
    }
}
