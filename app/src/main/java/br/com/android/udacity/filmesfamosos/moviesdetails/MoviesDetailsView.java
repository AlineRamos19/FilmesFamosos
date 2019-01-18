package br.com.android.udacity.filmesfamosos.moviesdetails;

import android.graphics.Bitmap;

import br.com.android.udacity.filmesfamosos.models.Result;

public interface MoviesDetailsView {

    void checkValueIntent();
    void setUpValuesDetailsMovies(Result itemMovie);
    String configSubstringDate(String date);
    void getImageSaveNewFavorite();
    void saveFavoriteRepository(Bitmap bitmap);
    void configToolbar();
}
