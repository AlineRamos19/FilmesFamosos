package br.com.android.udacity.filmesfamosos.room;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.android.udacity.filmesfamosos.favorite.FavoriteModelMovie;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    LiveData<List<FavoriteModelMovie>> loadAllFavorite();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteMovie(FavoriteModelMovie favoriteMovie);

    @Query("DELETE FROM favorite WHERE title = :title")
    void deleteFavoriteMovie(String title);

    @Query("DELETE FROM favorite")
    void deleteAll();

    @Query("SELECT * FROM favorite WHERE title = :title")
    LiveData<FavoriteModelMovie> getMovieByTitle(String title);

}
