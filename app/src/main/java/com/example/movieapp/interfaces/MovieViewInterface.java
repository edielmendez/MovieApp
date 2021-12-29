package com.example.movieapp.interfaces;

import com.example.movieapp.obj.Movie;

import java.util.List;

public interface MovieViewInterface {
    void showMovies(List<Movie> movies);
    void invalidResponse(String message);
}
