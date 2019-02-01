package br.com.android.udacity.filmesfamosos.allmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import br.com.android.udacity.filmesfamosos.R;
import br.com.android.udacity.filmesfamosos.allmovies.adapter.MoviesAdapter;
import br.com.android.udacity.filmesfamosos.constant.ConstData;
import br.com.android.udacity.filmesfamosos.favorite.FavoriteModelMovie;
import br.com.android.udacity.filmesfamosos.favorite.adapter.MovieFavoriteAdapter;
import br.com.android.udacity.filmesfamosos.models.Result;
import br.com.android.udacity.filmesfamosos.viewmodel.FavoriteMoviesViewModel;

public class AllMoviesActivity extends AppCompatActivity implements AllMoviesView {

    private static final String LAYOUT_STATE = "LAYOUT_STATE";
    private AllMoviesPresenter mPresenter = new AllMoviesPresenter();
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private MoviesAdapter mAdapter;
    private MovieFavoriteAdapter adapter;
    private TextView mTitleTypeMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        mRecyclerView = findViewById(R.id.recycler_all_movies);
        mProgressBar = findViewById(R.id.progressBar);
        configRecyclerView();
        checkConnection();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LAYOUT_STATE, mRecyclerView.getLayoutManager().onSaveInstanceState());
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mGridLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(LAYOUT_STATE));
        }
    }

    private int calculateColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int numberColumns = displayMetrics.widthPixels / widthDivider;
        if (numberColumns < 2) return 2;
        return numberColumns;
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
            mPresenter.requestMovie(ConstData.URL_UP_COMING, this);
        } else {
            showAlert(getString(R.string.error_internet_off));
        }
    }


    @Override
    public void getListMovieReceiver(List<Result> listMovie) {
        hideProgressBar();
        if (listMovie.size() != 0) {
            mAdapter = new MoviesAdapter(listMovie, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            showNotFoundResults();
        }
    }

    @Override
    public void configRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(AllMoviesActivity.this, calculateColumns());
        mRecyclerView.setLayoutManager(mGridLayoutManager);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNotFoundResults() {
        TextView emptyResult = findViewById(R.id.not_found_results);
        emptyResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void initFavoriteView() {
        adapter = new MovieFavoriteAdapter(this);
        FavoriteMoviesViewModel mViewModel = ViewModelProviders.of(this).get(FavoriteMoviesViewModel.class);
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
    public void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_all_movies);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView titleToolbar = findViewById(R.id.title_toolbar);
        titleToolbar.setText(R.string.app_name);
        mTitleTypeMovie = findViewById(R.id.label_type_movies);
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
                    setUpNewScreen(ConstData.URL_UP_COMING, R.string.label_up_coming);
                } else {
                    showAlert(getString(R.string.error_internet_off));
                }
                return true;

            case R.id.action_top_rated:
                if (mPresenter.statusNetworkInfo(this, this)) {
                    setUpNewScreen(ConstData.URL_TOP_RATED, R.string.label_top_rated);
                } else {
                    showAlert(getString(R.string.error_internet_off));
                }
                return true;

            case R.id.action_most_popular:
                if (mPresenter.statusNetworkInfo(this, this)) {
                    setUpNewScreen(ConstData.URL_POPULAR, R.string.label_most_popular);
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

    void setUpNewScreen(String url, int labelType) {
        showProgressBar();
        configRecyclerView();
        mTitleTypeMovie.setText(labelType);
        mPresenter.requestMovie(url, this);
    }


}



