package com.example.movieapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.movieapp.R;
import com.example.movieapp.databinding.ActivityMainBinding;
import com.example.movieapp.fragments.DetailsMovieFragment;
import com.example.movieapp.interfaces.MoviePresenterInterface;
import com.example.movieapp.interfaces.MovieViewInterface;
import com.example.movieapp.obj.Movie;
import com.example.movieapp.obj.Ubication;
import com.example.movieapp.presenter.MoviePresenter;
import com.example.movieapp.recycler.RecyclerMovie;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MovieViewInterface {
    public final String TAG = "MainActivity";
    private final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 9002;
    public static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9003;
    private ActivityMainBinding binding;
    private MoviePresenterInterface presenter;
    private RecyclerMovie adapter;
    private AlertDialog alertNoGPS;
    private Toolbar toolbar;
    //Location
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private Location currentLocation;//
    //Firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        presenter = new MoviePresenter(this);
        //setContentView(R.layout.activity_main);
        //Location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                Location location = locationResult.getLocations().get(0);
                if(location != null){
                    currentLocation = location;
                    Log.d(TAG,"LocationCallback Location found = Latitud :  "+location.getLatitude() + "  Longitud : " + location.getLongitude());
                    createNotification(location.getLatitude(), location.getLongitude());
                    writeLocation(location.getLatitude(), location.getLongitude());
                    loadMovies();
                    //calculateProximity();
                    fusedLocationClient.removeLocationUpdates(locationCallback);
                }



            }
        };
        /*locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Log.d(TAG,"locationCallbck");
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    Log.d(TAG,"locationCallbck ssss");
                    // Update UI with location data
                    // ...
                }
            }
        };*/
        //End Location

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d(TAG_TOURS,"SE INVOCO ON RESUME");
        checkPermissions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_goto_locations) {
            startActivity(new Intent(MainActivity.this, LocationActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMovies(){
        presenter.getAllMovies();
    }

    @Override
    public void showMovies(List<Movie> movies) {
        //Se muestra el recyclerview
        adapter =  new RecyclerMovie(movies, new RecyclerMovie.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                showDetailsMovie(movie.getId());
            }
        });
        RecyclerView.LayoutManager linearVertical = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        binding.moviesRecyclerView.setHasFixedSize(true);

        binding.moviesRecyclerView.setLayoutManager(linearVertical);
        binding.moviesRecyclerView.setItemAnimator( new DefaultItemAnimator());
        binding.moviesRecyclerView.setAdapter(adapter);
    }

    @Override
    public void invalidResponse(String message) {

    }

    private void showDetailsMovie(int movieId) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialogMovie");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.

        DialogFragment details = new DetailsMovieFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("movie_id", String.valueOf(movieId));
        //bundle1.putString("id_restaurant", String.valueOf(user.getIdRestaurant()));
        details.setArguments(bundle1);
        details.show(getSupportFragmentManager(),"dialogMovie");

    }

    private void checkPermissions(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                showExplanation();
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted

            checkMapServices();
        }
    }

    private boolean checkMapServices(){

        if(isGPSEnabled()){
            if(alertNoGPS != null){
                alertNoGPS.dismiss();
            }
            getLastKnownLocation();
            return true;
        }

        return false;
    }

    public boolean isGPSEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Esta aplicación necesita que actives la Ubicación para trabajar correctamente\n ¿Habilitar Localización ?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        alertNoGPS.dismiss();
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);

                    }
                });
        alertNoGPS = builder.create();
        alertNoGPS.show();
    }

    private void showExplanation() {
        new AlertDialog.Builder(this)
                .setTitle("Autorización")
                .setMessage("La aplicación necesita permiso para acceder a la ubicación de tu dispositivo.")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_ACCESS_FINE_LOCATION);
                        }

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Mensaje acción cancelada
                        showMessageCancel();
                    }
                })
                .show();
    }

    public void showMessageCancel(){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Ubicación no habilitada")
                .setMessage("Haz rechazado la petición, por favor considere en aceptarla.\n La aplicación no puede trabajar de manera correcta.")
                .setCancelable(false)
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                    }
                })
                .show();
    }

    private void getLastKnownLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.

                        if (location != null) {
                            Log.d(TAG,"Location found = Latitud :  "+location.getLatitude() + "  Longitud : " + location.getLongitude());
                            currentLocation = location;
                            createNotification(location.getLatitude(), location.getLongitude());
                            writeLocation(location.getLatitude(), location.getLongitude());
                            loadMovies();
                            //calculateProximity();
                            //loadPlace();
                        }else{
                            startLocationUpdates();
                        }
                    }
                });

    }

    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    private void createNotification(double latitude, double longitude){
        Log.d(TAG, "createNotification");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "MovieApp0101")
                .setSmallIcon(R.drawable.small_icon)
                .setContentTitle("Ubicación actual guardada")
                .setContentText("La ubicación actual " + latitude + " , "+ longitude + " se guardo")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("MovieApp0101", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);


        notificationManager.notify(0, builder.build());
    }

    private void writeLocation(double latitude, double longitude){
        Log.d(TAG, "writeLocation" );
        Ubication ubication = new Ubication(latitude , longitude );
        Map<String, Ubication> currentUbication = new HashMap<>();
        currentUbication.put(String.valueOf(System.currentTimeMillis() / 1000), ubication);




        db.collection("locations")
                .add(ubication)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

}