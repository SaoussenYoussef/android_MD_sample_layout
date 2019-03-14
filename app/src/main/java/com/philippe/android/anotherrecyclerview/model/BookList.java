package com.philippe.android.anotherrecyclerview.model;

import java.io.Serializable;
import java.util.List;

public class BookList implements Serializable {

    private String kind;
    private int totalItems;
    private List<Book> items;


    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<Book> getItems() {
        return items;
    }

    public void setItems(List<Book> items) {
        this.items = items;
    }
}
