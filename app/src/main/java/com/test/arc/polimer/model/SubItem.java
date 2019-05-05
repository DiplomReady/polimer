package com.test.arc.polimer.model;

import android.text.Html;
import android.text.Spanned;

import java.io.Serializable;

public class SubItem implements Serializable {
    private String title;
    private String imageUrl;
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

    public String getImage() {
        return imageUrl;
    }

    public void setImage(String image) {
        this.imageUrl = image;
    }

    public Spanned getDescription() {
        return Html.fromHtml(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
