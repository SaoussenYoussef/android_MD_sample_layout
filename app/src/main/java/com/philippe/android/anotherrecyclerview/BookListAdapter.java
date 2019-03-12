package com.philippe.android.anotherrecyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookItemViewHolder> {

    public static final String BOOK_DESCRIPTION = "book.description";
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


        mCurrentBook = mBookList.get(position);


        bookItemViewHolder.bookItemTitle.setText(String.valueOf(mCurrentBook.getTitle()));
        bookItemViewHolder.bookItemDescription.setText(String.valueOf(mCurrentBook.getDescription()));
        Glide.with(mContext).load(mCurrentBook.getImageResource()).into(bookItemViewHolder.bookImage);


    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }


    class BookItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView bookItemTitle;
        TextView bookItemDescription;
        ImageView bookImage;


        public BookItemViewHolder(@NonNull View itemView) {
            super(itemView);

            bookItemTitle = itemView.findViewById(R.id.book_title);
            bookItemDescription = itemView.findViewById(R.id.book_short_description);
            bookImage = itemView.findViewById(R.id.book_detail_image);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, BookDetails.class);
            Book book = mBookList.get(getAdapterPosition());
            intent.putExtra(BOOK_DESCRIPTION, book.getDescription());
            intent.putExtra(BOOK_IMAGE,book.getImageResource());
            mContext.startActivity(intent);

        }
    }
}
