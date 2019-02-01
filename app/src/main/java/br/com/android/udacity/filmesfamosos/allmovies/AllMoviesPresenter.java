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
    private Context mContext;

    @Override
    public boolean statusNetworkInfo(Context context, AllMoviesView view) {
        mContext = context;

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
    public void requestMovie(String url, final AllMoviesView view) {
        try {
            Call<MoviesReceiver> call =
                    new ClientRetrofit(ConstData.URL_BASE).getApiService()
                            .getAllMovies(url, ConstData.API_KEY, ConstData.LANGUAGE_DEFAULT);
            call.enqueue(new Callback<MoviesReceiver>() {
                @Override
                public void onResponse(Call<MoviesReceiver> call, Response<MoviesReceiver> response) {
                    if (response.isSuccessful()) {
                        view.getListMovieReceiver(response.body().getResults());
                    }
                }
                @Override
                public void onFailure(Call<MoviesReceiver> call, Throwable t) {
                    view.showAlert(mContext.getResources().getString(R.string.error_server));
                }
            });

        } catch (Exception e) {
            Log.e(LOG_TAG, "--ERROR-- " + e.getMessage());
        }
    }
}


