package br.com.android.udacity.filmesfamosos.room;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;
import br.com.android.udacity.filmesfamosos.favorite.FavoriteModelMovie;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    LiveData<List<FavoriteModelMovie>> loadAllFavorite();

    @Insert
    void insertFavoriteMovie(FavoriteModelMovie favoriteMovie);

    @Delete
    void deleteFavoriteMovie(FavoriteModelMovie favoriteMovie);

    @Query("DELETE FROM favorite")
    void deleteAll();


}
