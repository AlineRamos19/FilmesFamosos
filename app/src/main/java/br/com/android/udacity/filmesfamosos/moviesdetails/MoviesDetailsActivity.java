package br.com.android.udacity.filmesfamosos.moviesdetails;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.ByteArrayOutputStream;

import br.com.android.udacity.filmesfamosos.R;
import br.com.android.udacity.filmesfamosos.favorite.FavoriteModelMovie;
import br.com.android.udacity.filmesfamosos.models.Result;
import br.com.android.udacity.filmesfamosos.staticvaluesapi.DataAPI;
import br.com.android.udacity.filmesfamosos.viewmodel.FavoriteMoviesViewModel;

public class MoviesDetailsActivity extends AppCompatActivity implements MoviesDetailsView {

    Boolean isCheckedFavorite = true;
    private static final String LOG_TAG = MoviesDetailsActivity.class.getSimpleName();
    private Result itemMovie;
    private FavoriteMoviesViewModel mFavoriteMoviesViewModel;


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

        mFavoriteMoviesViewModel = ViewModelProviders.of(this).get(FavoriteMoviesViewModel.class);
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

        Glide.with(this).load(DataAPI.URL_BASE_IMAGE + itemMovie.getPosterPath()).into(mImageMovie);
        mTitle.setText(itemMovie.getTitle());
        mOverview.setText(itemMovie.getOverview());
        mDateRelease.setText(configSubstringDate(itemMovie.getReleaseDate()));
        mVoteAverage.setRating(Float.parseFloat(String.valueOf(itemMovie.getVoteAverage())));
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
        if (isCheckedFavorite) {
            menu.getItem(0).setIcon(R.drawable.ic_favorite);
            isCheckedFavorite = false;
            mFavoriteMoviesViewModel.delete(itemMovie.getId());
        } else {
            menu.getItem(0).setIcon(R.drawable.ic_favorite_selected);
            isCheckedFavorite = true;
            getImageSaveNewFavorite();

        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void getImageSaveNewFavorite() {

        Glide.with(MoviesDetailsActivity.this)
                .asBitmap()
                .load(DataAPI.URL_BASE_IMAGE + itemMovie.getPosterPath())
                .into(new Target<Bitmap>() {
                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {

                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        saveFavoriteRepository(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void getSize(@NonNull SizeReadyCallback cb) {
                        cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                    }

                    @Override
                    public void removeCallback(@NonNull SizeReadyCallback cb) {

                    }

                    @Override
                    public void setRequest(@Nullable Request request) {

                    }

                    @Nullable
                    @Override
                    public Request getRequest() {
                        return null;
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onStop() {

                    }

                    @Override
                    public void onDestroy() {

                    }
                });


    }



    @Override
    public void saveFavoriteRepository(Bitmap bitmap) {
        ByteArrayOutputStream byteImage = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteImage);
        byte[] bitmapByte = byteImage.toByteArray();

        FavoriteModelMovie favoriteModelMovie =
                new FavoriteModelMovie(
                        bitmapByte,
                        itemMovie.getTitle(),
                        itemMovie.getOverview(),
                        itemMovie.getReleaseDate(),
                        itemMovie.getVoteAverage());

        mFavoriteMoviesViewModel.insert(favoriteModelMovie);

    }

}


