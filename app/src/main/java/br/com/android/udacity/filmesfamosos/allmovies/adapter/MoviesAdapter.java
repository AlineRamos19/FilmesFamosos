package br.com.android.udacity.filmesfamosos.allmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.List;
import br.com.android.udacity.filmesfamosos.R;
import br.com.android.udacity.filmesfamosos.models.Result;
import br.com.android.udacity.filmesfamosos.moviesdetails.MoviesDetailsActivity;
import br.com.android.udacity.filmesfamosos.staticvaluesapi.DataAPI;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieHolder> {

    private List<Result> mListMovies;
    private Context mContext;

    public MoviesAdapter(List<Result> listMovie, Context context) {
        this.mListMovies = listMovie;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_all_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieHolder holder, int position) {
        final Result movie = mListMovies.get(position);
        String pathImage = DataAPI.URL_BASE_IMAGE + movie.getPosterPath();

        if(!pathImage.isEmpty()){
            Glide.with(mContext).load(pathImage).into(holder.imageMovie);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MoviesDetailsActivity.class);
                intent.putExtra("item_movie", movie);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListMovies.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {

        public MovieHolder(View itemView) {
            super(itemView);
        }

        ImageView imageMovie = itemView.findViewById(R.id.image_movie);
    }
}

