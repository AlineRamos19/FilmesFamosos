package br.com.android.udacity.filmesfamosos.moviesdetails;

import android.graphics.Bitmap;

import java.util.List;

import br.com.android.udacity.filmesfamosos.models.Result;
import br.com.android.udacity.filmesfamosos.models.ResultVideo;

public interface MoviesDetailsView {

    void checkValueIntent();
    void setUpValuesDetailsMovies(Result itemMovie);
    String configSubstringDate(String date);
    void getImageSaveNewFavorite();
    void saveFavoriteRepository(Bitmap bitmap);
    void configToolbar();
    void showMessageToast(int message);
    void getVideo(String id);
    void setupRecyclerVideo(List<ResultVideo> listVideo);
    void hideViewVideo();
    void showViewVideo();
    void checkInternet();

}
