package br.com.android.udacity.filmesfamosos.allmovies;


import java.util.List;

import br.com.android.udacity.filmesfamosos.models.Result;

public interface AllMoviesView {

    void showAlert(String message);
    void checkConnection();
    void getListMovieReceiver(List<Result> listUpComing);
    void configRecyclerView();
    void hideProgressBar();
    void showProgressBar();
    void showNotFoundResults();
    void initFavoriteView();
    void initToolbar();


}
