package com.philippe.android.anotherrecyclerview.model;

import java.io.Serializable;

public class Pdf implements Serializable {
    private boolean isAvailable;

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
