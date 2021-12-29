package com.example.movieapp.presenter;

import com.example.movieapp.interfaces.DetailsMovieInteractorInterface;
import com.example.movieapp.interfaces.DetailsMoviePresenterInterface;
import com.example.movieapp.interfaces.DetailsMovieViewInterface;
import com.example.movieapp.model.DetailMovieInteractor;
import com.example.movieapp.obj.Movie;

public class DetailsMoviePresenter implements DetailsMoviePresenterInterface {
    private DetailsMovieViewInterface view;
    private DetailsMovieInteractorInterface interactor;
    public DetailsMoviePresenter(DetailsMovieViewInterface view){
        this.view = view;
        interactor = new DetailMovieInteractor(this);
    }
    @Override
    public void getMovie(String movieId) {
        interactor.getMovie(movieId);
    }

    @Override
    public void showMovie(Movie movie) {
        view.showMovie(movie);
    }

    @Override
    public void invalidResponse(String message) {
        view.invalidResponse(message);
    }
}
