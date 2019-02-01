package br.com.android.udacity.filmesfamosos.allmovies;

import android.content.Context;


public interface IAllMoviesPresenter {

    boolean statusNetworkInfo(Context context, AllMoviesView view);
    void requestMovie(String url, AllMoviesView view);

}
