package com.philippe.android.anotherrecyclerview;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;

public class NetworkUtils {

    private static final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";

    private static final String BOOK_BASE_BYID_URL = "https://www.googleapis.com/books/v1/volumes/";

    private static final String QUERY_PARAM = "q";

    private static final String MAX_RESULTS = "maxResults";

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();


    public static String getBooks(String query) {

        String booksAsJson = "";
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {

            if (query == null) {
                Random random = new Random(100);
                query = String.valueOf(random.nextInt());
            }
            Uri uri = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, query)
                    .appendQueryParameter(MAX_RESULTS, String.valueOf(30))
                    .build();

            URL url = new URL(uri.toString());

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            //runs the request
            connection.connect();

            InputStream inputStream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }

            if (builder.length() == 0) {
                return null;
            }

            booksAsJson = builder.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (connection != null)
                connection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.i(LOG_TAG, booksAsJson);

        return booksAsJson;
    }

    public static String getBook(String id) {


        String bookAsJson = "";
        HttpURLConnection connection = null;
        BufferedReader reader = null;


        try {

            Uri uri = Uri.parse(BOOK_BASE_BYID_URL + id).buildUpon()
                    .build();

            URL url = null;

            url = new URL(uri.toString());

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            //runs the request
            connection.connect();

            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }

            if (builder.length() == 0) {
                return null;
            }

            bookAsJson = builder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookAsJson;


    }
}
