package com.philippe.android.anotherrecyclerview.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.philippe.android.anotherrecyclerview.Loader.BooksLoader;
import com.philippe.android.anotherrecyclerview.model.Book;
import com.philippe.android.anotherrecyclerview.BookListAdapter;
import com.philippe.android.anotherrecyclerview.R;
import com.philippe.android.anotherrecyclerview.model.BookList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<BookList> {

    private static final String LOG_TAG = BookListActivity.class.getSimpleName();
    private static final String QUERY_STRING = "query.string";
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    List<Book> mBookList = new ArrayList<>();
    private String mQueryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = findViewById(R.id.recyclerview);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });

        mAdapter = new BookListAdapter(mBookList, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent searchIntent = getIntent();

        if (searchIntent != null) {
            handleSearch(searchIntent);
        }


        initData();

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT | ItemTouchHelper.UP,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder origin,
                    @NonNull RecyclerView.ViewHolder target) {

                int fromPosition = origin.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                Collections.swap(mBookList, fromPosition, toPosition);
                mAdapter.notifyItemMoved(fromPosition, toPosition);

                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                mBookList.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

            }
        });

        helper.attachToRecyclerView(mRecyclerView);
    }

    private void handleSearch(Intent searchIntent) {
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {

            mQueryString = searchIntent.getStringExtra(SearchManager.QUERY);
            Log.i(LOG_TAG, "The user searched for " + mQueryString);
        }

    }

    private void initData() {
        Bundle bundle = new Bundle();
        bundle.putString(QUERY_STRING, mQueryString);
        getSupportLoaderManager().initLoader(0, bundle, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.book_list_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_goto_favorites:
                Intent intent = new Intent(this, FavoriteListActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<BookList> onCreateLoader(int i, @Nullable Bundle bundle) {

        return new BooksLoader(this, bundle.getString(QUERY_STRING));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<BookList> loader, BookList books) {

        mBookList.clear();
        if (books.getTotalItems() != 0)
            mBookList.addAll(books.getItems());
        mAdapter.notifyDataSetChanged();


    }


    @Override
    public void onLoaderReset(@NonNull Loader<BookList> loader) {

    }
}
