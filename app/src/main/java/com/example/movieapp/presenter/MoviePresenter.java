package com.example.movieapp.presenter;

import com.example.movieapp.interfaces.MovieInteractorInterface;
import com.example.movieapp.interfaces.MoviePresenterInterface;
import com.example.movieapp.interfaces.MovieViewInterface;
import com.example.movieapp.model.MovieInteractor;
import com.example.movieapp.obj.Movie;

import java.util.List;

public class MoviePresenter implements MoviePresenterInterface {
    private MovieViewInterface view;
    private MovieInteractorInterface interactor;
    public MoviePresenter(MovieViewInterface viewInterface) {
        this.view = viewInterface;
        interactor = new MovieInteractor(this);
    }

    @Override
    public void getAllMovies() {
        interactor.getAllMovies();
    }

    @Override
    public void invalidResponse(String message) {
        view.invalidResponse(message);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        view.showMovies(movies);
    }
}
