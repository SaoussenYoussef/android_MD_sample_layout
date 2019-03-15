package com.philippe.android.anotherrecyclerview.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.philippe.android.anotherrecyclerview.room.entity.Favorite;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Favorite favorite);

    @Query("SELECT * FROM  favorite_table ORDER BY favorite ")
    LiveData<List<Favorite>> getAll();
}
