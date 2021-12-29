package com.example.movieapp.interfaces;

import com.example.movieapp.obj.Movie;

import java.util.List;
import java.util.Map;

public interface LocationViewInterface {
    void showLocations(Map<String, Object> locations);
    void invalidResponse(String message);
}
