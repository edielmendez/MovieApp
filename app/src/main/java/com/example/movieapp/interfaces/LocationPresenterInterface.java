package com.example.movieapp.interfaces;

import java.util.Map;

public interface LocationPresenterInterface {
    void getAllLocations();
    void showLocations(Map<String, Object> locations);
    void invalidResponse(String message);
}
