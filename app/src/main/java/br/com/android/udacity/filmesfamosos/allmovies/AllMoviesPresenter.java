package br.com.android.udacity.filmesfamosos.allmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import br.com.android.udacity.filmesfamosos.R;
import br.com.android.udacity.filmesfamosos.api.ClientRetrofit;
import br.com.android.udacity.filmesfamosos.constant.ConstData;
import br.com.android.udacity.filmesfamosos.models.MoviesReceiver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllMoviesPresenter implements IAllMoviesPresenter {

    private static final String LOG_TAG = AllMoviesPresenter.class.getName();

    @Override
    public boolean statusNetworkInfo(Context context, AllMoviesView view) {

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
            Call<MoviesReceiver> call = new ClientRetrofit(ConstData.URL_BASE).getApiService()
                    .getAllMovies(ConstData.URL_UP_COMING, ConstData.API_KEY, ConstData.LANGUAGE_DEFAULT);
            call.enqueue(new Callback<MoviesReceiver>() {
                @Override
                public void onResponse(Call<MoviesReceiver> call,
                                       Response<MoviesReceiver> response) {
                    if (response.isSuccessful()) {
                        view.getListMovieReceiver(response.body().getResults());
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
            Call<MoviesReceiver> call = new ClientRetrofit(ConstData.URL_BASE).getApiService()
                    .getAllMovies(ConstData.URL_TOP_RATED, ConstData.API_KEY, ConstData.LANGUAGE_DEFAULT);
            call.enqueue(new Callback<MoviesReceiver>() {
                @Override
                public void onResponse(Call<MoviesReceiver> call, Response<MoviesReceiver> response) {
                    if (response.isSuccessful()) {
                        view.getListMovieReceiver(response.body().getResults());
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
                    new ClientRetrofit(ConstData.URL_BASE).getApiService()
                            .getAllMovies(ConstData.URL_POPULAR, ConstData.API_KEY, ConstData.LANGUAGE_DEFAULT);
            call.enqueue(new Callback<MoviesReceiver>() {
                @Override
                public void onResponse(Call<MoviesReceiver> call, Response<MoviesReceiver> response) {
                    if (response.isSuccessful()) {

                        view.getListMovieReceiver(response.body().getResults());
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


