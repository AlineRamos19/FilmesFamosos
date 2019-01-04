package br.com.android.udacity.filmesfamosos.favorite;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.List;

import br.com.android.udacity.filmesfamosos.R;
import br.com.android.udacity.filmesfamosos.favorite.adapter.MovieFavoriteAdapter;
import br.com.android.udacity.filmesfamosos.viewmodel.MoviesViewModel;

public class FavoriteMovie extends AppCompatActivity implements FavoriteView {

    private MoviesViewModel mViewModel;
    private MovieFavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);

        Toolbar toolbar = findViewById(R.id.toolbar_favorite_movies);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView titleToolbar = findViewById(R.id.title_toolbar);
        titleToolbar.setText(R.string.toolbar_favorite);

        configRecycler();
        adapter = new MovieFavoriteAdapter(this);

        mViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        mViewModel.getMoviesFavorite().observe(this, new Observer<List<FavoriteModelMovie>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteModelMovie> favoriteModelMovies) {
                adapter.setListMovie(favoriteModelMovies);
            }
        });

    }

    @Override
    public void configRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recycler_favorite);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}
