package br.com.android.udacity.filmesfamosos.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import java.util.List;
import br.com.android.udacity.filmesfamosos.favorite.FavoriteModelMovie;
import br.com.android.udacity.filmesfamosos.repository.MoviesRepository;

public class FavoriteMoviesViewModel extends AndroidViewModel {

    private MoviesRepository moviesRepository;
    private LiveData<List<FavoriteModelMovie>> moviesFavorite ;

    public FavoriteMoviesViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MoviesRepository(application);
        moviesFavorite = moviesRepository.getmAllFavoriteMovies();
    }

    public LiveData<List<FavoriteModelMovie>> getMoviesFavorite() {
        return moviesFavorite;
    }

     public void insert(FavoriteModelMovie favoriteModelMovie){
        moviesRepository.insert(favoriteModelMovie);
     }

     public void delete(int id){
         moviesRepository.deleteFavorite(id);
     }

     public FavoriteModelMovie getMovieById(String title){
        return moviesRepository.getMovieById(title);
     }

}
