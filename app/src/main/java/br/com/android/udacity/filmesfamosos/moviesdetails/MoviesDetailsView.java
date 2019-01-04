package br.com.android.udacity.filmesfamosos.moviesdetails;

import android.widget.ImageView;
import br.com.android.udacity.filmesfamosos.models.Result;

public interface MoviesDetailsView {

    void checkValueIntent();
    void setUpValuesDetailsMovies(Result itemMovie);
    void configShowImage(String pathImage, ImageView imageMovie);
    String configSubstringDate(String date);
}
