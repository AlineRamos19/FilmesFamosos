package br.com.android.udacity.filmesfamosos.moviesdetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.android.udacity.filmesfamosos.R;
import br.com.android.udacity.filmesfamosos.favorite.FavoriteModelMovie;
import br.com.android.udacity.filmesfamosos.favorite.FavoriteView;
import br.com.android.udacity.filmesfamosos.models.Result;
import br.com.android.udacity.filmesfamosos.staticvaluesapi.DataAPI;
import br.com.android.udacity.filmesfamosos.viewmodel.MoviesViewModel;

public class MoviesDetailsActivity extends AppCompatActivity implements MoviesDetailsView {

    Boolean isCheckedFavorite = true;
    private static final String LOG_TAG = MoviesDetailsActivity.class.getSimpleName();
    private Result itemMovie;
    private String pathImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);

        Toolbar toolbar = findViewById(R.id.toolbar_details_movies);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView titleToolbar = findViewById(R.id.title_toolbar);
        titleToolbar.setText(R.string.details_movies_title);
        checkValueIntent();
    }

    @Override
    public void checkValueIntent() {
        try {
            itemMovie = (Result) getIntent().getSerializableExtra("item_movie");
            setUpValuesDetailsMovies(itemMovie);
        } catch (Exception e) {
            Log.e(LOG_TAG, "--ERROR-- " + e.getMessage());
        }
    }

    @Override
    public void setUpValuesDetailsMovies(Result itemMovie) {

        ImageView mImageMovie = findViewById(R.id.image_movie);
        TextView mTitle = findViewById(R.id.title_movie_favorite);
        TextView mOverview = findViewById(R.id.overview);
        TextView mDateRelease = findViewById(R.id.date_release);
        RatingBar mVoteAverage = findViewById(R.id.vote_average);

        pathImage = itemMovie.getPosterPath();
        configShowImage(pathImage, mImageMovie);
        mTitle.setText(itemMovie.getTitle());
        mOverview.setText(itemMovie.getOverview());
        mDateRelease.setText(configSubstringDate(itemMovie.getReleaseDate()));
        mVoteAverage.setRating(Float.parseFloat(String.valueOf(itemMovie.getVoteAverage())));
    }

    @Override
    public void configShowImage(String pathImage, ImageView imageMovie) {
        try {
            if (!pathImage.isEmpty()) {
                Glide.with(this).load(DataAPI.URL_BASE_IMAGE + pathImage).into(imageMovie);

            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "--ERROR-- " + e.getMessage());
        }
    }

    @Override
    public String configSubstringDate(String date) {
        return date.substring(0, 4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.action_favorite:
                supportInvalidateOptionsMenu();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(isCheckedFavorite){
            menu.getItem(0).setIcon(R.drawable.ic_favorite);
            isCheckedFavorite = false;
            //TODO DELETE FAVORITE MOVIE WITH ID
        } else{
            menu.getItem(0).setIcon(R.drawable.ic_favorite_selected);
            isCheckedFavorite = true;
            saveFavorite();

        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void saveFavorite() {
        Toast.makeText(MoviesDetailsActivity.this, "Clicado", Toast.LENGTH_LONG).show();
    }

}


