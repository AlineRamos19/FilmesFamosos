package br.com.android.udacity.filmesfamosos.allmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import br.com.android.udacity.filmesfamosos.R;
import br.com.android.udacity.filmesfamosos.allmovies.adapter.MoviesAdapter;
import br.com.android.udacity.filmesfamosos.favorite.FavoriteModelMovie;
import br.com.android.udacity.filmesfamosos.favorite.adapter.MovieFavoriteAdapter;
import br.com.android.udacity.filmesfamosos.models.Result;
import br.com.android.udacity.filmesfamosos.viewmodel.FavoriteMoviesViewModel;

public class AllMoviesActivity extends AppCompatActivity implements AllMoviesView {

    private static final String LAYOUT_STATE = "LAYOUT_STATE";
    private AllMoviesPresenter mPresenter = new AllMoviesPresenter();
    private FrameLayout mFrameLoading;
    private RecyclerView mRecyclerView;
    private List<Result> mListResult;
    private Parcelable mListState;
    private GridLayoutManager mGridLayoutManager;
    private MoviesAdapter mAdapter;
    private FavoriteMoviesViewModel mViewModel;
    private MovieFavoriteAdapter adapter;
    private TextView mTitleTypeMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar_all_movies);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView titleToolbar = findViewById(R.id.title_toolbar);
        titleToolbar.setText(R.string.app_name);
        mTitleTypeMovie = findViewById(R.id.label_type_movies);

        mRecyclerView = findViewById(R.id.recycler_all_movies);
        mFrameLoading = findViewById(R.id.frame_progress);
        configRecyclerView();
        checkConnection();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mListState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(LAYOUT_STATE, mListState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mGridLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(LAYOUT_STATE));
        }
    }

    @Override
    public void showAlert(String message) {
        hideProgressBar();
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_try_again, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkConnection();
                    }
                }).show();
    }

    @Override
    public void checkConnection() {
        if (mPresenter.statusNetworkInfo(this, this)) {
            showProgressBar();
            mPresenter.requestUpComing(this);
        } else {
            showAlert(getString(R.string.error_internet_off));
        }
    }


    @Override
    public void getListMovieReceiver(List<Result> listMovie) {
        hideProgressBar();
        if (listMovie.size() != 0) {
            mListResult = listMovie;
            mAdapter = new MoviesAdapter(mListResult, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            showNotFoundResults();
        }
    }

    @Override
    public void configRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(AllMoviesActivity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
    }

    @Override
    public void hideProgressBar() {
        mFrameLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showProgressBar() {
        mFrameLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNotFoundResults() {
        TextView emptyResult = findViewById(R.id.not_found_results);
        emptyResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void initFavoriteView() {
        adapter = new MovieFavoriteAdapter(this);

        mViewModel = ViewModelProviders.of(this).get(FavoriteMoviesViewModel.class);
        mViewModel.getMoviesFavorite().observe(this, new Observer<List<FavoriteModelMovie>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteModelMovie> favoriteModelMovies) {
                adapter.setListMovie(favoriteModelMovies);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(AllMoviesActivity.this));
                mRecyclerView.setAdapter(adapter);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_by, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_up_coming:
                if (mPresenter.statusNetworkInfo(this, this)) {
                    showProgressBar();
                    configRecyclerView();
                    mTitleTypeMovie.setText(R.string.label_up_coming);
                    mPresenter.requestUpComing(this);
                } else {
                    showAlert(getString(R.string.error_internet_off));
                }

                return true;

            case R.id.action_top_rated:
                if (mPresenter.statusNetworkInfo(this, this)) {
                    showProgressBar();
                    configRecyclerView();
                    mTitleTypeMovie.setText(R.string.label_top_rated);
                    mPresenter.requestTopRated(this);
                } else {
                    showAlert(getString(R.string.error_internet_off));
                }

                return true;

            case R.id.action_most_popular:
                if (mPresenter.statusNetworkInfo(this, this)) {
                    showProgressBar();
                    configRecyclerView();
                    mTitleTypeMovie.setText(R.string.label_most_popular);
                    mPresenter.requestMostPopular(this);
                } else {
                    showAlert(getString(R.string.error_internet_off));
                }
                return true;

            case R.id.action_favorite:
                if (mPresenter.statusNetworkInfo(this, this)) {
                    mTitleTypeMovie.setText(R.string.label_favorite);
                    initFavoriteView();
                } else {
                    showAlert(getString(R.string.error_internet_off));
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}



