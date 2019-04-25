package com.test.arc.polimer.model;

import java.io.Serializable;
import java.util.List;

public class HomeItem implements Serializable {
    private String title;

    private List<SubItem> subItems;

    public HomeItem(String title, List<SubItem> subItems) {
        this.title = title;
        this.subItems = subItems;
    }
    public String getTitle() {
        return title;
    }

    public List<SubItem> getSubItems() {
        return subItems;
    }
}
