package br.com.android.udacity.filmesfamosos.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.util.List;
import br.com.android.udacity.filmesfamosos.favorite.FavoriteModelMovie;
import br.com.android.udacity.filmesfamosos.room.AppDatabase;
import br.com.android.udacity.filmesfamosos.room.FavoriteDao;

public class MoviesRepository  {

    private FavoriteDao mFavoriteDao;
    private LiveData<List<FavoriteModelMovie>> mAllFavorite;

    public MoviesRepository(Application application){
        AppDatabase mDb = AppDatabase.getsInstance(application);
        mFavoriteDao = mDb.favoriteDao();
        mAllFavorite = mFavoriteDao.loadAllFavorite();
    }

    public LiveData<List<FavoriteModelMovie>> getmAllFavoriteMovies(){
        return mAllFavorite;
    }

    public void insert(FavoriteModelMovie favoriteModelMovie){
        new insertAsyncTask(mFavoriteDao).execute(favoriteModelMovie);
    }

    public void deleteFavorite(String title){
       new deleteByTitle(mFavoriteDao).execute(title);
    }

    public LiveData<FavoriteModelMovie> getMovieByTitle(String title){
        return mFavoriteDao.getMovieByTitle(title);
    }

    private static class insertAsyncTask extends AsyncTask<FavoriteModelMovie, Void, Void> {
        private FavoriteDao mAsyncTaskDao;

        insertAsyncTask(FavoriteDao mFavoriteDao) {
             mAsyncTaskDao = mFavoriteDao;
        }

        @Override
        protected Void doInBackground(FavoriteModelMovie... favoriteModelMovies) {
             mAsyncTaskDao.insertFavoriteMovie(favoriteModelMovies[0]);
            return null;
        }
    }


    private static class deleteByTitle extends AsyncTask<String, Void, Void> {
        private FavoriteDao mAsyncTaskDao;
        deleteByTitle(FavoriteDao mFavoriteDao) {
            mAsyncTaskDao = mFavoriteDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            mAsyncTaskDao.deleteFavoriteMovie(strings[0]);
            return null;
        }
    }
}
