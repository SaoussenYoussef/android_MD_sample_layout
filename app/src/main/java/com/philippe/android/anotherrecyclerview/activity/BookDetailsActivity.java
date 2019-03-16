package com.philippe.android.anotherrecyclerview.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.philippe.android.anotherrecyclerview.adapter.BookListAdapter;
import com.philippe.android.anotherrecyclerview.R;
import com.philippe.android.anotherrecyclerview.ViewModel.FavoriteViewModel;
import com.philippe.android.anotherrecyclerview.model.Book;
import com.philippe.android.anotherrecyclerview.model.ImageLinks;
import com.philippe.android.anotherrecyclerview.model.RetailPrice;
import com.philippe.android.anotherrecyclerview.model.SaleInfo;
import com.philippe.android.anotherrecyclerview.model.VolumeInfo;
import com.philippe.android.anotherrecyclerview.room.entity.Favorite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookDetailsActivity extends AppCompatActivity {

    TextView mDescriptionTextView;
    ImageView mBookImageView;
    TextView mRatingsCount;
    TextView mTitleAndAuthorTextView;
    TextView mPriceTextView;
    RatingBar mRatingBar;
    Book mCurrentBook;

    private FavoriteViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        mDescriptionTextView = findViewById(R.id.book_description);
        mBookImageView = findViewById(R.id.book_detail_image);
        mRatingsCount = findViewById(R.id.rating_count);
        mTitleAndAuthorTextView = findViewById(R.id.author_and_title);
        mPriceTextView = findViewById(R.id.book_price);
        mRatingBar = findViewById(R.id.rating);

        mViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);

        Intent intent = getIntent();


        Uri uri;
        ImageLinks bookImageLinks;
        if (intent != null) {
            mCurrentBook = (Book) intent.getSerializableExtra(BookListAdapter.BOOK);

            if (mCurrentBook != null) {

                VolumeInfo bookInfo = mCurrentBook.getVolumeInfo();
                SaleInfo saleInfo = mCurrentBook.getSaleInfo();

                if (saleInfo != null) {
                    RetailPrice retailPrice = saleInfo.getRetailPrice();
                    if (retailPrice != null) {
                        mPriceTextView.setText(String.valueOf(retailPrice.getAmount() + " " + retailPrice.getCurrencyCode()));
                    }

                }

                if (bookInfo != null) {
                    mDescriptionTextView.setText(String.valueOf(bookInfo.getDescription()));
                    mRatingsCount.setText(
                            String.valueOf(bookInfo.getRatingsCount() == null ? 0 : bookInfo.getRatingsCount())
                                    + " "
                                    + getString(R.string.reviews_label));
                    Integer averageRating = (int) bookInfo.getAverageRating();
                    if (averageRating != null)
                        mRatingBar.setRating((averageRating == null ? 0 : averageRating));

                    String title = bookInfo.getTitle();

                    StringBuilder builder = new StringBuilder();

                    builder = builder.append(title).append(".");

                    for (String author : (bookInfo.getAuthors() == null ? new ArrayList<String>() : bookInfo.getAuthors())) {
                        builder.append('\n').append(author);
                    }
                    mTitleAndAuthorTextView.setText(String.valueOf(builder.toString()));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.book_details_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
        Date now = new Date();
        String dateAsString = formatter.format(now);

        if (id == R.id.favorite)
            mViewModel.insert(new Favorite(mCurrentBook.getId(), dateAsString, dateAsString));

        return super.onOptionsItemSelected(item);
    }
}
