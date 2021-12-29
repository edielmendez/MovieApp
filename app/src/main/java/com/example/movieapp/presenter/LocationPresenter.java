package com.example.movieapp.presenter;

import com.example.movieapp.interfaces.LocationInteractorInterface;
import com.example.movieapp.interfaces.LocationPresenterInterface;
import com.example.movieapp.interfaces.LocationViewInterface;
import com.example.movieapp.model.LocationInteractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationPresenter implements LocationPresenterInterface {
    private LocationViewInterface view;
    private LocationInteractorInterface interactor;
    public LocationPresenter(LocationViewInterface view){
        this.view = view;
        interactor = new LocationInteractor(this);
    }
    @Override
    public void getAllLocations() {
        interactor.getAllLocations();
    }

    @Override
    public void showLocations(Map<String, Object> locations) {

        view.showLocations(locations);
    }

    @Override
    public void invalidResponse(String message) {
        view.invalidResponse(message);
    }
}
