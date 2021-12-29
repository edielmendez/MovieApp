package com.example.movieapp.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.databinding.LocationItemBinding;
import com.example.movieapp.databinding.MovieItemBinding;
import com.example.movieapp.obj.Movie;
import com.example.movieapp.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class RecyclerLocation extends  RecyclerView.Adapter<RecyclerLocation.ViewHolder> {
    public final String TAG = "RecyclerMovie";
    private Map<String, Object> locations;
    //private final OnItemClickListener listener;

    public RecyclerLocation(Map<String, Object> locations) {
        this.locations = locations;
        //this.listener = listener;
        //this.context = context;
        //Picasso
        //client = new OkHttpClient.Builder().addInterceptor(new UserAgentInterceptor()).build();
        //picasso = new Picasso.Builder(context).downloader(new OkHttp3Downloader(client)).build();
    }

    @Override
    public RecyclerLocation.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new RecyclerLocation.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerLocation.ViewHolder holder, final int position) {
        //holder.binding.latitude.setText(locations.get)
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
    /*
    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }*/

    public  class ViewHolder extends RecyclerView.ViewHolder{

        private LocationItemBinding binding;


        //public Button btn_ver;
        public ViewHolder(View view) {
            super(view);
            binding = LocationItemBinding.bind(view);

        }
        /*
        public void bind(final Movie tour, final RecyclerMovie.OnItemClickListener listener) {
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(tour);
                }
            });
        }*/


    }
}
