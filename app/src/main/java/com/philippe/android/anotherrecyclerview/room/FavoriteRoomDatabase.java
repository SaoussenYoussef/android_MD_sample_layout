package com.philippe.android.anotherrecyclerview.room;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.philippe.android.anotherrecyclerview.room.dao.FavoriteDao;
import com.philippe.android.anotherrecyclerview.room.entity.Favorite;


@Database(entities = {Favorite.class}, version = 1, exportSchema = false)
public abstract class FavoriteRoomDatabase extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();

    private static FavoriteRoomDatabase INSTANCE;

    public static FavoriteRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavoriteRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            FavoriteRoomDatabase.class, "favorite_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
