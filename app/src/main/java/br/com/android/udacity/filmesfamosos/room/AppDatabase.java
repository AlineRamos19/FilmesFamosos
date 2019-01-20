package br.com.android.udacity.filmesfamosos.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import br.com.android.udacity.filmesfamosos.favorite.FavoriteModelMovie;

@Database(entities = {FavoriteModelMovie.class}, version = 2 , exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;
    private static final String DATABASE_NAME = "favoritemovie";
    private static final Object LOCK = new Object();

    public static AppDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract FavoriteDao favoriteDao();
}
