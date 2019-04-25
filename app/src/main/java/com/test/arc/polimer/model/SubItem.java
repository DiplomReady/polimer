package com.test.arc.polimer.model;

import androidx.annotation.DrawableRes;

import java.io.Serializable;

public class SubItem implements Serializable {
    private String title;
    @DrawableRes int image;
    private String description;

    public SubItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
