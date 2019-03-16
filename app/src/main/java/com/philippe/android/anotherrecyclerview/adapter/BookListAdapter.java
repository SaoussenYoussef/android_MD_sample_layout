package com.philippe.android.anotherrecyclerview.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.philippe.android.anotherrecyclerview.R;
import com.philippe.android.anotherrecyclerview.activity.BookDetailsActivity;
import com.philippe.android.anotherrecyclerview.model.Book;
import com.philippe.android.anotherrecyclerview.model.ImageLinks;
import com.philippe.android.anotherrecyclerview.model.VolumeInfo;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookItemViewHolder> {

    public static final String BOOK = "book";
    public static final String BOOK_IMAGE = "book.image";
    private List<Book> mBookList;
    private LayoutInflater mInflater;
    private Context mContext;
    Book mCurrentBook;


    public BookListAdapter(List<Book> mBookList, Context context) {
        this.mBookList = mBookList;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }


    @NonNull
    @Override
    public BookItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {


        View mItemView = mInflater.inflate(
                R.layout.book_item_layout, viewGroup, false);

        return new BookItemViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookItemViewHolder bookItemViewHolder, int position) {

        Uri uri = null;
        mCurrentBook = mBookList.get(position);
        VolumeInfo bookInfo = mCurrentBook.getVolumeInfo();
        if (bookInfo != null) {
            bookItemViewHolder.bookItemTitle.setText(String.valueOf(bookInfo.getTitle() == null ? "" : bookInfo.getTitle()));
            ImageLinks bookImageLinks = bookInfo.getImageLinks();

            if (bookImageLinks != null) {
                if (bookImageLinks.getThumbnail() != null) {
                    uri = Uri.parse(bookImageLinks.getThumbnail());
                    Glide.with(mContext).load(uri).into(bookItemViewHolder.bookImage);
                } else {
                    TypedArray bookImages = mContext.getResources().obtainTypedArray(R.array.book_images);
                    int i = 1;
                    Glide.with(mContext).load(bookImages.getResourceId(i, 0)).into(bookItemViewHolder.bookImage);
                }


            }
        }


    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }


    class BookItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView bookItemTitle;
        //TextView bookItemDescription;
        ImageView bookImage;


        public BookItemViewHolder(@NonNull View itemView) {
            super(itemView);

            bookItemTitle = itemView.findViewById(R.id.book_title);
            //bookItemDescription = itemView.findViewById(R.id.book_short_description);
            bookImage = itemView.findViewById(R.id.book_detail_image);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, BookDetailsActivity.class);
            Book book = mBookList.get(getAdapterPosition());
            intent.putExtra(BOOK, book);

            mContext.startActivity(intent);

        }
    }
}
