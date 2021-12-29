package com.example.movieapp.responses;

import com.example.movieapp.obj.Movie;

import java.util.List;

public class MovieListResponse {
    private List<Movie> items;

    public List<Movie> getItems() {
        return items;
    }

    public void setItems(List<Movie> items) {
        this.items = items;
    }
}
