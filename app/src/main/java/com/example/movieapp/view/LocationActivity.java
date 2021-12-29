package com.example.movieapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.movieapp.R;
import com.example.movieapp.databinding.ActivityLocationBinding;
import com.example.movieapp.databinding.ActivityMainBinding;
import com.example.movieapp.interfaces.LocationPresenterInterface;
import com.example.movieapp.interfaces.LocationViewInterface;
import com.example.movieapp.obj.Movie;
import com.example.movieapp.obj.Ubication;
import com.example.movieapp.presenter.LocationPresenter;
import com.example.movieapp.recycler.RecyclerMovie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationActivity extends AppCompatActivity implements LocationViewInterface {
    public final String TAG = "LocationActivity";
    private Toolbar toolbar;
    private ActivityLocationBinding binding;
    private LocationPresenterInterface presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        presenter = new LocationPresenter(this);
        presenter.getAllLocations();
    }

    @Override
    public void showLocations(Map<String, Object> locations) {

    }

    @Override
    public void invalidResponse(String message) {

    }
}