package br.com.android.udacity.filmesfamosos.allmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import java.util.List;
import br.com.android.udacity.filmesfamosos.R;
import br.com.android.udacity.filmesfamosos.api.ClientRetrofit;
import br.com.android.udacity.filmesfamosos.models.MoviesReceiver;
import br.com.android.udacity.filmesfamosos.models.Result;
import br.com.android.udacity.filmesfamosos.staticvaluesapi.DataAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllMoviesPresenter implements IAllMoviesPresenter {

    private static final String LOG_TAG = AllMoviesPresenter.class.getName();

    @Override
    public boolean statusNetworkInfo(Context context,    AllMoviesView view) {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            NetworkInfo networkInfo = null;
            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();

        } catch (NullPointerException e) {
            view.showAlert(context.getString(R.string.error_server));
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void requestUpComing(final AllMoviesView view) {

        try {
            Call<MoviesReceiver> call = new ClientRetrofit(DataAPI.URL_BASE).getApiService()
                    .getAllMoviesComing(DataAPI.API_KEY, DataAPI.LANGUAGE_DEFAULT);
            call.enqueue(new Callback<MoviesReceiver>() {
                @Override
                public void onResponse(Call<MoviesReceiver> call,
                                       Response<MoviesReceiver> response) {
                    if (response.isSuccessful()) {
                        List<br.com.android.udacity.filmesfamosos.models.Result> dataList =
                                response.body().getResults();
                        view.getListMovieReceiver(dataList);
                    }
                }

                @Override
                public void onFailure(Call<MoviesReceiver> call, Throwable t) {
                    Log.e(LOG_TAG, "--ERROR-- " + t.getMessage());
                }
            });

        } catch (Exception e) {
            Log.e(LOG_TAG, "--ERROR-- " + e.getMessage());
        }


    }


    public void requestTopRated(final AllMoviesView view) {
        try {
            Call<MoviesReceiver> call = new ClientRetrofit(DataAPI.URL_BASE).getApiService()
                    .getAllMoviesTopRated(DataAPI.API_KEY, DataAPI.LANGUAGE_DEFAULT);
            call.enqueue(new Callback<MoviesReceiver>() {
                @Override
                public void onResponse(Call<MoviesReceiver> call, Response<MoviesReceiver> response) {
                    if (response.isSuccessful()) {
                        List<Result> result = response.body().getResults();
                        view.getListMovieReceiver(result);
                    }
                }

                @Override
                public void onFailure(Call<MoviesReceiver> call, Throwable t) {
                    Log.e(LOG_TAG, "--ERROR-- " + t.getMessage());
                }
            });

        } catch (Exception e) {
            Log.e(LOG_TAG, "--ERROR-- " + e.getMessage());
        }


    }

    @Override
    public void requestMostPopular(final AllMoviesView view) {
        try {
            Call<MoviesReceiver> call =
                    new ClientRetrofit(DataAPI.URL_BASE).getApiService()
                            .getAllMoviesPopular(DataAPI.API_KEY, DataAPI.LANGUAGE_DEFAULT);
            call.enqueue(new Callback<MoviesReceiver>() {
                @Override
                public void onResponse(Call<MoviesReceiver> call, Response<MoviesReceiver> response) {
                    if (response.isSuccessful()) {
                        List<Result> data =
                                response.body().getResults();
                        view.getListMovieReceiver(data);
                    }
                }

                @Override
                public void onFailure(Call<MoviesReceiver> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            Log.e(LOG_TAG, "--ERROR-- " + e.getMessage());
        }
    }
}


