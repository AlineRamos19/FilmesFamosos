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
     private FavoriteModelMovie favoriteMovie;

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

    public void deleteFavorite(int id){
        new deleteAsyncTask(mFavoriteDao).execute(id);
    }

    public FavoriteModelMovie getMovieById(String title){
          new getIdMovieAsyncTask(mFavoriteDao).execute(title);
          return favoriteMovie;

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

    private static class deleteAsyncTask extends AsyncTask<Integer,Void, Void>{

        private FavoriteDao mFavoriteDao;
        public deleteAsyncTask(FavoriteDao favoriteDao) {
            mFavoriteDao =  favoriteDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mFavoriteDao.deleteFavoriteMovie(integers[0]);
            return null;
        }
    }

    private  class getIdMovieAsyncTask extends AsyncTask<String, Void, FavoriteModelMovie> {
        private FavoriteDao mFavoriteDao;

        public getIdMovieAsyncTask(FavoriteDao favoriteDao) {
            mFavoriteDao = favoriteDao;
        }

        @Override
        protected FavoriteModelMovie doInBackground(String... strings) {
          MoviesRepository.this.favoriteMovie = mFavoriteDao.getMovieById(strings[0]);
          return favoriteMovie;
        }
    }

}
