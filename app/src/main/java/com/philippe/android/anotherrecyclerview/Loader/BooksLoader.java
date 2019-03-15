package com.philippe.android.anotherrecyclerview.Loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.philippe.android.anotherrecyclerview.NetworkUtils;
import com.philippe.android.anotherrecyclerview.model.BookList;

import java.io.IOException;

public class BooksLoader extends AsyncTaskLoader<BookList> {

    private static final String LOG_TAG = BooksLoader.class.getSimpleName();
    private String mQuery;

    public BooksLoader(@NonNull Context context, String query) {
        super(context);
        mQuery = query;
    }

    @Nullable
    @Override
    public BookList loadInBackground() {

        BookList books = null;

        try {
            String booksAsJSON = NetworkUtils.getBooks(mQuery);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            books = (BookList) mapper.readValue(booksAsJSON, BookList.class);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Parsing failed", e);
        }


        return books;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        super.onStartLoading();
    }
}
