package com.example.movieapp.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.movieapp.interfaces.LocationInteractorInterface;
import com.example.movieapp.interfaces.LocationPresenterInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LocationInteractor implements LocationInteractorInterface {
    private LocationPresenterInterface presenter;
    //Firebase
    private FirebaseFirestore db;
    public LocationInteractor(LocationPresenterInterface presenter){
        this.presenter = presenter;
        db = FirebaseFirestore.getInstance();
    }
    @Override
    public void getAllLocations() {
        db.collection("locations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                presenter.showLocations(document.getData());

                            }
                        } else {
                            presenter.invalidResponse(task.getException().getMessage());
                        }
                    }
                });
    }
}
