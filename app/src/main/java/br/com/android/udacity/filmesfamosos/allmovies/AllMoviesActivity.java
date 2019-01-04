package br.com.android.udacity.filmesfamosos.allmovies;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import br.com.android.udacity.filmesfamosos.R;
import br.com.android.udacity.filmesfamosos.adapter.MoviesAdapter;
import br.com.android.udacity.filmesfamosos.models.Result;

public class AllMoviesActivity extends AppCompatActivity implements AllMoviesView {

    private AllMoviesPresenter mPresenter = new AllMoviesPresenter();
    private FrameLayout mFrameLoading;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_all_movies);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView titleToolbar = findViewById(R.id.title_toolbar);
        titleToolbar.setText(R.string.app_name);

        mRecyclerView = findViewById(R.id.recycler_all_movies);
        mFrameLoading = findViewById(R.id.frame_progress);
        checkConnection();
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
            configRecyclerView();
            MoviesAdapter adapter = new MoviesAdapter(listMovie, this);
            mRecyclerView.setAdapter(adapter);
        } else {
            showNotFoundResults();
        }
    }

    @Override
    public void configRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(AllMoviesActivity.this, 2));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_by, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_up_coming:
                if(mPresenter.statusNetworkInfo(this, this)){
                    showProgressBar();
                    mPresenter.requestUpComing(this);
                }else{
                    showAlert(getString(R.string.error_internet_off));
                }

                return true;

            case R.id.action_top_rated:
                if(mPresenter.statusNetworkInfo(this, this)){
                    showProgressBar();
                    mPresenter.requestTopRated(this);
                }else{
                    showAlert(getString(R.string.error_internet_off));
                }

                return true;

            case R.id.action_most_popular:
                if(mPresenter.statusNetworkInfo(this, this)){
                    showProgressBar();
                   mPresenter.requestMostPopular(this);
                }else{
                    showAlert(getString(R.string.error_internet_off));
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
