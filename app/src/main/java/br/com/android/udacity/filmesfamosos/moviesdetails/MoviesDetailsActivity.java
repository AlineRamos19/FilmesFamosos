package br.com.android.udacity.filmesfamosos.moviesdetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.ByteArrayOutputStream;
import java.util.List;

import br.com.android.udacity.filmesfamosos.R;
import br.com.android.udacity.filmesfamosos.constant.DataAPI;
import br.com.android.udacity.filmesfamosos.favorite.FavoriteModelMovie;
import br.com.android.udacity.filmesfamosos.models.Result;
import br.com.android.udacity.filmesfamosos.models.ResultReviews;
import br.com.android.udacity.filmesfamosos.models.ResultVideo;
import br.com.android.udacity.filmesfamosos.moviesdetails.adapter.ReviewsAdapter;
import br.com.android.udacity.filmesfamosos.moviesdetails.adapter.VideoAdapter;
import br.com.android.udacity.filmesfamosos.viewmodel.FavoriteMoviesViewModel;

public class MoviesDetailsActivity extends AppCompatActivity implements MoviesDetailsView {

    private MoviesDetailsPresenter mpresenter = new MoviesDetailsPresenter(this);

    Boolean isCheckedFavorite;
    private static final String LOG_TAG = MoviesDetailsActivity.class.getSimpleName();
    private Result mItemMovie;
    private FavoriteMoviesViewModel mFavoriteMoviesViewModel;
    private ConstraintLayout mFrameVideo;
    private TextView mErrorVideoInternet;
    private RecyclerView mRecyclerVideo;
    private RecyclerView mRecyclerReviews;
    private ConstraintLayout mFrameReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);

        configToolbar();
        checkValueIntent();
        mFrameVideo = findViewById(R.id.frame_video);
        mErrorVideoInternet = findViewById(R.id.error_btn_signal);
        mRecyclerVideo = findViewById(R.id.recycler_video);
        mRecyclerReviews = findViewById(R.id.recycler_reviews);
        mFrameReviews = findViewById(R.id.frame_reviews);

        mFavoriteMoviesViewModel = ViewModelProviders.of(this)
                .get(FavoriteMoviesViewModel.class);

        mErrorVideoInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInternet();
            }
        });

        checkInternet();
    }


    @Override
    public void configToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_details_movies);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView titleToolbar = findViewById(R.id.title_toolbar);
        titleToolbar.setText(R.string.details_movies_title);
    }

    @Override
    public void showMessageToast(int message) {
        Snackbar.make(findViewById(R.id.view_details), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void getVideo(String id) {
        mpresenter.getVideoApi(id);
        mpresenter.getReviewsApi(id);
    }

    @Override
    public void setupRecyclerVideo(List<ResultVideo> listVideo) {
        mRecyclerVideo.setVisibility(View.VISIBLE);
        mRecyclerVideo.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerVideo.setLayoutManager(layoutManager);
        mRecyclerVideo.setAdapter(new VideoAdapter(listVideo, this));
    }

    @Override
    public void setupRecycerReviews(List<ResultReviews> listReviews) {
        mRecyclerReviews.setVisibility(View.VISIBLE);
        mRecyclerReviews.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerReviews.setLayoutManager(layoutManager);
        mRecyclerReviews.setAdapter(new ReviewsAdapter(listReviews, this));
    }

    @Override
    public void hideFrameVideo() {
        mFrameVideo.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showFrameVideo() {
        mFrameVideo.setVisibility(View.VISIBLE);
    }

    @Override
    public void checkInternet() {

        mErrorVideoInternet.setVisibility(View.INVISIBLE);
        if(mpresenter.statusNetworkInfo(this)){
            getVideo(String.valueOf(mItemMovie.getId()));
        }else{
            showFrameVideo();
            mErrorVideoInternet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideFrameReviews() {
        mFrameReviews.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showFrameReviews() {
        mFrameReviews.setVisibility(View.VISIBLE);
    }

    @Override
    public void checkValueIntent() {
        try {
            mItemMovie = (Result) getIntent().getSerializableExtra("item_movie");
            setUpValuesDetailsMovies(mItemMovie);
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
        isCheckedFavorite = itemMovie.getFavorite();
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
                item.setChecked(!item.isChecked());
                if (item.isChecked()) {
                    showMessageToast(R.string.favorite_entry);
                    getImageSaveNewFavorite();
                    item.setIcon(R.drawable.ic_favorite_selected);
                    return true;
                } else {
                    showMessageToast(R.string.delete_favorite);
                    mFavoriteMoviesViewModel.delete(mItemMovie.getTitle());
                    item.setIcon(R.drawable.ic_favorite);
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        final MenuItem menuItem = menu.findItem(R.id.action_favorite);

        mFavoriteMoviesViewModel.getMovieFavorite(mItemMovie.getTitle()).observe(this,
                new Observer<FavoriteModelMovie>() {
                    @Override
                    public void onChanged(@Nullable FavoriteModelMovie favoriteModelMovie) {
                        if(favoriteModelMovie != null){
                        menuItem.setChecked(true);
                        menuItem.setIcon(R.drawable.ic_favorite_selected);

                    }}
                });


        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void getImageSaveNewFavorite() {

        Glide.with(MoviesDetailsActivity.this)
                .asBitmap()
                .load(DataAPI.URL_BASE_IMAGE + mItemMovie.getPosterPath())
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
                        mItemMovie.getTitle(),
                        mItemMovie.getOverview(),
                        mItemMovie.getReleaseDate(),
                        mItemMovie.getVoteAverage());

        mFavoriteMoviesViewModel.insert(favoriteModelMovie);

    }
}


