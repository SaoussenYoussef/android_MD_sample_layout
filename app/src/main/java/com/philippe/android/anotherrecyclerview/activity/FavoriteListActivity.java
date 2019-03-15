package com.philippe.android.anotherrecyclerview.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.philippe.android.anotherrecyclerview.R;
import com.philippe.android.anotherrecyclerview.ViewModel.FavoriteAdapter;
import com.philippe.android.anotherrecyclerview.ViewModel.FavoriteViewModel;
import com.philippe.android.anotherrecyclerview.room.entity.Favorite;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListActivity extends AppCompatActivity {

    private RecyclerView mFavoritesRecyclerView;
    List<Favorite> mFavorites = new ArrayList<>();
    private FavoriteViewModel mViewModel;
    private FavoriteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);
        mFavoritesRecyclerView = findViewById(R.id.recyclerview_favorites);
        mAdapter = new FavoriteAdapter(mFavorites, this);
        mFavoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFavoritesRecyclerView.setAdapter(mAdapter);

        mViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        mViewModel.getAllFavorites().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                mAdapter.setmFavoriteList(favorites);
                mAdapter.notifyDataSetChanged();

            }
        });

    }
}
