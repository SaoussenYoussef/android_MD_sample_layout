package com.philippe.android.anotherrecyclerview.model;

import java.io.Serializable;

public class Epub implements Serializable {

    private boolean isAvailable;

    private boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
