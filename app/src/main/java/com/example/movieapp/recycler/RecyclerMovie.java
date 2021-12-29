package com.example.movieapp.recycler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.databinding.MovieItemBinding;
import com.example.movieapp.obj.Movie;
import com.example.movieapp.utils.Constant;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerMovie extends  RecyclerView.Adapter<RecyclerMovie.ViewHolder>{
    public final String TAG = "RecyclerMovie";
    private List<Movie> movies;
    private final OnItemClickListener listener;

    public RecyclerMovie(List<Movie> movies, OnItemClickListener listener) {
        this.movies = movies;
        this.listener = listener;
        //this.context = context;
        //Picasso
        //client = new OkHttpClient.Builder().addInterceptor(new UserAgentInterceptor()).build();
        //picasso = new Picasso.Builder(context).downloader(new OkHttp3Downloader(client)).build();
    }

    @Override
    public RecyclerMovie.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new RecyclerMovie.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerMovie.ViewHolder holder, final int position) {
        holder.binding.movieTitle.setText(movies.get(position).getTitle());

        Picasso.get().load(Constant.BASE_URL_IMAGES + movies.get(position).getBackdrop_path()).into(holder.binding.movieImage);
        holder.binding.movieVoteAverage.setText(String.valueOf(movies.get(position).getVote_average()));
        holder.binding.movieDate.setText(movies.get(position).getRelease_date());
        //Formateo de fecha
        /*String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date date = simpleDateFormat.parse(movies.get(position).getRelease_date());
            String formattedDate = simpleDateFormat.format(date);
            holder.binding.movieDate.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        holder.bind(movies.get(position), listener);
    }

    @Override
    public int getItemCount() {

        return movies.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        private MovieItemBinding binding;


        //public Button btn_ver;
        public ViewHolder(View view) {
            super(view);
            binding = MovieItemBinding.bind(view);

        }

        public void bind(final Movie tour, final RecyclerMovie.OnItemClickListener listener) {
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(tour);
                }
            });
        }


    }
}
