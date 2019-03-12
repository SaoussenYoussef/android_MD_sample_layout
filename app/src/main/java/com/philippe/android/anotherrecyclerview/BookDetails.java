package com.philippe.android.anotherrecyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class BookDetails extends AppCompatActivity {

    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        textView = findViewById(R.id.book_description);
        imageView = findViewById(R.id.book_detail_image);
        Intent intent = getIntent();
        if (intent != null) {
            String stringExtra = intent.getStringExtra(BookListAdapter.BOOK_DESCRIPTION);
            int imageResource = intent.getIntExtra(BookListAdapter.BOOK_IMAGE, 0);
            if (stringExtra != null && imageResource != 0) {
                Glide.with(this).load(imageResource).into(imageView);
                textView.setText(String.valueOf(stringExtra));

            }


        }
    }
}
