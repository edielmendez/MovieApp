package com.example.movieapp.interfaces;

import com.example.movieapp.obj.Movie;

public interface DetailsMoviePresenterInterface {
    void getMovie(String movieId);
    void showMovie(Movie movie);
    void invalidResponse(String message);
}
