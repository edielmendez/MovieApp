package com.example.movieapp.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.movieapp.R;
import com.example.movieapp.databinding.ActivityMainBinding;
import com.example.movieapp.databinding.FragmentDetailsMovieBinding;
import com.example.movieapp.interfaces.DetailsMoviePresenterInterface;
import com.example.movieapp.interfaces.DetailsMovieViewInterface;
import com.example.movieapp.obj.Movie;
import com.example.movieapp.presenter.DetailsMoviePresenter;
import com.example.movieapp.utils.Constant;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsMovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsMovieFragment extends DialogFragment implements DetailsMovieViewInterface {
    public final String TAG = "DetailsMovieFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "movie_id";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String movieId;
    //private String mParam2;

    public DetailsMovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsMovieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsMovieFragment newInstance(String param1, String param2) {
        DetailsMovieFragment fragment = new DetailsMovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private FragmentDetailsMovieBinding binding;
    private DetailsMoviePresenterInterface presenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailsMovieBinding.inflate(inflater, container, false);
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        presenter = new DetailsMoviePresenter(this);
        presenter.getMovie(movieId);
        binding.loading.setVisibility(View.VISIBLE);
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_details_movie, container, false);
    }

    @Override
    public void showMovie(Movie movie) {
        binding.loading.setVisibility(View.GONE);
        Picasso.get().load(Constant.BASE_URL_IMAGES + movie.getPoster_path()).into(binding.movieImage);
        binding.movieTitle.setText(movie.getTitle());
        binding.movieOverview.setText(movie.getOverview());
        binding.moviePopularity.setText(movie.getVote_average() + "");
    }

    @Override
    public void invalidResponse(String message) {

    }
}