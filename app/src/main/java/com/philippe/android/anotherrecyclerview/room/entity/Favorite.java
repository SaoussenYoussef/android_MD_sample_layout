package com.philippe.android.anotherrecyclerview.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "favorite_table")
public class Favorite {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "favorite")
    private String idBook;

    @NonNull
    @ColumnInfo(name = "last_accessed_date")
    private String lastAccessDate;


    @NonNull
    @ColumnInfo(name = "mark_as_favorite_date")
    private String markAsFavorite;

    public Favorite(@NonNull String idBook, @NonNull String lastAccessDate, @NonNull String markAsFavorite) {
        this.idBook = idBook;
        this.lastAccessDate = lastAccessDate;
        this.markAsFavorite = markAsFavorite;
    }

    @NonNull
    public String getIdBook() {
        return idBook;
    }

    public void setIdBook(@NonNull String idBook) {
        this.idBook = idBook;
    }

    @NonNull
    public String getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(@NonNull String lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    @NonNull
    public String getMarkAsFavorite() {
        return markAsFavorite;
    }

    public void setMarkAsFavorite(@NonNull String markAsFavorite) {
        this.markAsFavorite = markAsFavorite;
    }
}
