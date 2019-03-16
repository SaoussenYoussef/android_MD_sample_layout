package com.philippe.android.anotherrecyclerview.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.philippe.android.anotherrecyclerview.NetworkUtils;
import com.philippe.android.anotherrecyclerview.R;
import com.philippe.android.anotherrecyclerview.activity.BookDetailsActivity;
import com.philippe.android.anotherrecyclerview.model.Book;
import com.philippe.android.anotherrecyclerview.model.ImageLinks;
import com.philippe.android.anotherrecyclerview.model.VolumeInfo;
import com.philippe.android.anotherrecyclerview.room.entity.Favorite;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private static final String FAVORITE = "favorite";
    private List<Favorite> mFavoriteList;
    private LayoutInflater mInflater;
    private Context mContext;
    Favorite mCurrentFavorite;
    //private Book mCurrentBook;
    private CurrentBookChangeListener listener;


    public FavoriteAdapter(List<Favorite> mFavoriteList, Context context) {
        this.mFavoriteList = mFavoriteList;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View mItemView = mInflater.inflate(
                R.layout.book_item_layout, viewGroup, false);

        return new FavoriteViewHolder(mItemView);
    }

    public void setmFavoriteList(List<Favorite> mFavoriteList) {
        this.mFavoriteList = mFavoriteList;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder favoriteViewHolder, int position) {

        Uri uri = null;
        mCurrentFavorite = mFavoriteList.get(position);

        String bookId = mCurrentFavorite.getIdBook();

        new BookByIdAsyncTask(favoriteViewHolder, position).execute(bookId);

//        if (listener != null)
//            listener.setCurrentBook();

    }

    @Override
    public int getItemCount() {
        return mFavoriteList.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView bookItemTitle;
        //TextView bookItemDescription;
        ImageView bookImage;


        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            bookItemTitle = itemView.findViewById(R.id.book_title);
            //bookItemDescription = itemView.findViewById(R.id.book_short_description);
            bookImage = itemView.findViewById(R.id.book_detail_image);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, BookDetailsActivity.class);
            Favorite favorite = mFavoriteList.get(getAdapterPosition());
            intent.putExtra(FAVORITE, false);

            mContext.startActivity(intent);

        }
    }

    private class BookByIdAsyncTask extends AsyncTask<String, Void, Book> {

        private String LOG_TAG = BookByIdAsyncTask.class.getSimpleName();
        //private Book book = null;
        private FavoriteAdapter favoriteAdapter;
        private int mPosition;
        private WeakReference<FavoriteViewHolder> mFavoriteViewHolder;


        public BookByIdAsyncTask(FavoriteViewHolder favoriteViewHolder, int position) {
            mFavoriteViewHolder = new WeakReference<FavoriteViewHolder>(favoriteViewHolder);
            mPosition = position;

        }

        @Override
        protected Book doInBackground(String... strings) {
            Book book = null;
            try {
                String bookAsJson = NetworkUtils.getBook(strings[0]);
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                book = mapper.readValue(bookAsJson, Book.class);


            } catch (IOException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "Parsing failed", e);
            }

            return book;

        }

        @Override
        protected void onPostExecute(final Book receivedBook) {

            Uri uri = null;


            VolumeInfo bookInfo = receivedBook.getVolumeInfo();
            if (bookInfo != null) {
                mFavoriteViewHolder.get().bookItemTitle.setText(String.valueOf(bookInfo.getTitle() == null ? "" : bookInfo.getTitle()));
                ImageLinks bookImageLinks = bookInfo.getImageLinks();

                if (bookImageLinks != null) {
                    if (bookImageLinks.getThumbnail() != null) {
                        uri = Uri.parse(bookImageLinks.getThumbnail());
                        Glide.with(mContext).load(uri).into(mFavoriteViewHolder.get().bookImage);
                    } else {
                        TypedArray bookImages = mContext.getResources().obtainTypedArray(R.array.book_images);
                        int i = 1;
                        Glide.with(mContext).load(bookImages.getResourceId(i, 0)).into(mFavoriteViewHolder.get().bookImage);
                    }


                }
            }

//            favoriteAdapter.listener = new CurrentBookChangeListener() {
//                @Override
//                public void setCurrentBook() {
//                    favoriteAdapter.mCurrentBook = receivedBook;
//
//
//                }
//            };
        }
    }

    public interface CurrentBookChangeListener {
        void setCurrentBook();
    }
}
