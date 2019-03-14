package com.philippe.android.anotherrecyclerview.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.philippe.android.anotherrecyclerview.BookListAdapter;
import com.philippe.android.anotherrecyclerview.R;
import com.philippe.android.anotherrecyclerview.model.Book;
import com.philippe.android.anotherrecyclerview.model.ImageLinks;
import com.philippe.android.anotherrecyclerview.model.VolumeInfo;

public class BookDetailsActivity extends AppCompatActivity {

    TextView textView;
    ImageView mBookImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        textView = findViewById(R.id.book_description);
        mBookImageView = findViewById(R.id.book_detail_image);

        Intent intent = getIntent();

        Uri uri;
        ImageLinks bookImageLinks;
        if (intent != null) {
            Book book = (Book) intent.getSerializableExtra(BookListAdapter.BOOK);

            if (book != null) {

                VolumeInfo bookInfo = book.getVolumeInfo();


                if (bookInfo != null) {
                    textView.setText(String.valueOf(bookInfo.getTitle()));
                    bookImageLinks = bookInfo.getImageLinks();


                    if (bookImageLinks != null) {
                        if (bookImageLinks.getThumbnail() != null) {
                            uri = Uri.parse(bookImageLinks.getThumbnail());
                            Glide.with(this).load(uri).into(mBookImageView);
                        } else {
                            TypedArray bookImages = getResources().obtainTypedArray(R.array.book_images);
                            int i = 1;
                            Glide.with(this).load(bookImages.getResourceId(i, 0)).into(mBookImageView);
                        }


                    }
                }
            }


        }
    }
}
