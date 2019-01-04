package br.com.android.udacity.filmesfamosos.allmovies;

import android.content.Context;


public interface IAllMoviesPresenter {

    boolean statusNetworkInfo(Context context, AllMoviesView view);
    void requestUpComing(AllMoviesView view);
    void requestTopRated(AllMoviesView view);
    void requestMostPopular(AllMoviesView view);

}
