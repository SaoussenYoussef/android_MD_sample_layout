package com.philippe.android.anotherrecyclerview.service;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.philippe.android.anotherrecyclerview.room.FavoriteRoomDatabase;
import com.philippe.android.anotherrecyclerview.room.dao.FavoriteDao;
import com.philippe.android.anotherrecyclerview.room.entity.Favorite;

import java.util.List;

public class FavoriteService {

    private FavoriteDao mFavoriteDao;
    private LiveData<List<Favorite>> mFavoriteList;

    public FavoriteService(Application application) {

        FavoriteRoomDatabase roomDatabase = FavoriteRoomDatabase.getDatabase(application);
        mFavoriteDao = roomDatabase.favoriteDao();
    }

    public LiveData<List<Favorite>> getAllFavorites() {
        return mFavoriteDao.getAll();
    }

    public void insertFavorite(Favorite favorite) {
        new InsertAsynctask(mFavoriteDao).execute(favorite);
    }

    private static class InsertAsynctask extends AsyncTask<Favorite, Void, Void> {

        private FavoriteDao mAsyncFavoriteDao;

        private InsertAsynctask(FavoriteDao mAsyncFavoriteDao) {
            this.mAsyncFavoriteDao = mAsyncFavoriteDao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            mAsyncFavoriteDao.insert(favorites[0]);

            return null;
        }
    }


}
