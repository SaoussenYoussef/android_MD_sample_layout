package com.philippe.android.anotherrecyclerview.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.philippe.android.anotherrecyclerview.room.entity.Favorite;
import com.philippe.android.anotherrecyclerview.service.FavoriteService;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private FavoriteService mFavoriteService;

    private LiveData<List<Favorite>> mAllFavorites;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);

        mFavoriteService = new FavoriteService(application);
        mAllFavorites = mFavoriteService.getAllFavorites();

    }


    public void insert(Favorite favorite) {
        mFavoriteService.insertFavorite(favorite);
    }

    public LiveData<List<Favorite>> getAllFavorites() {
        return mAllFavorites;
    }
}
