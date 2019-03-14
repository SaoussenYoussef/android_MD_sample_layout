package com.philippe.android.anotherrecyclerview.model;

import java.io.Serializable;

public class Dimensions implements Serializable {

    private String height;
    private String width;
    private String thickness;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }
}
