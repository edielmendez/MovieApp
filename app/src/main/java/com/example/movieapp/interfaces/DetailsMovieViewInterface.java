package com.example.movieapp.interfaces;

import com.example.movieapp.obj.Movie;



public interface DetailsMovieViewInterface {
    void showMovie(Movie movie);
    void invalidResponse(String message);
}
