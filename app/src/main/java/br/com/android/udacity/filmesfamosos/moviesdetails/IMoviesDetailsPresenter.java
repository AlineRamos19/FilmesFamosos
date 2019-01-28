package br.com.android.udacity.filmesfamosos.moviesdetails;

import android.content.Context;

public interface IMoviesDetailsPresenter {

       boolean statusNetworkInfo(Context context);
       void getVideoApi(String id);
       void getReviewsApi(String id);
}
