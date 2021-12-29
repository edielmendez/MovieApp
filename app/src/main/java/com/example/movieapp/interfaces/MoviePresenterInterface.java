package com.example.movieapp.interfaces;

import com.example.movieapp.obj.Movie;

import java.util.List;

public interface MoviePresenterInterface {
    void getAllMovies();
    void invalidResponse(String message);
    void showMovies(List<Movie> movies);
}
