package br.com.android.udacity.filmesfamosos.favorite.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.android.udacity.filmesfamosos.R;
import br.com.android.udacity.filmesfamosos.favorite.FavoriteModelMovie;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.FavoriteHolder> {


    private List<FavoriteModelMovie> listMovie;
    private final LayoutInflater mInflate;

    public MovieFavoriteAdapter(Context context){
        mInflate = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = mInflate.inflate(R.layout.row_favorite_item, parent, false);
        return new FavoriteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder favoriteHolder, int position) {

        if(listMovie != null){
            FavoriteModelMovie currentMovie = listMovie.get(position);
            Bitmap bitmap = BitmapFactory.decodeByteArray(currentMovie.getImage(), 0, currentMovie.getImage().length);

            favoriteHolder.imageMovie.setImageBitmap(bitmap);
            favoriteHolder.title.setText(currentMovie.getTitle());
            favoriteHolder.date.setText(currentMovie.getDateRelease());
            favoriteHolder.vote.setText(String.valueOf(currentMovie.getVoteAverage()));
            favoriteHolder.overview.setText(currentMovie.getDescription());

        }
    }

    public void setListMovie(List<FavoriteModelMovie> listMovie) {
        this.listMovie = listMovie;
    }

    @Override
    public int getItemCount() {
        if(listMovie != null){
            return listMovie.size();
        } else return 0;
    }



    public class FavoriteHolder  extends RecyclerView.ViewHolder{

        private final ImageView imageMovie;
        private final TextView title;
        private final TextView date;
        private final TextView vote;
        private final TextView overview;


        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);

            imageMovie = itemView.findViewById(R.id.image_favorite);
            title = itemView.findViewById(R.id.title_movie_favorite);
            date = itemView.findViewById(R.id.date_movie_favorite);
            vote = itemView.findViewById(R.id.vote_movie_favorite);
            overview = itemView.findViewById(R.id.favorite_overview);

        }
    }

}
