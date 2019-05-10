package com.test.arc.polimer.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.test.arc.polimer.dataBase.SubItemConverter;

import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class HomeItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public long id;

    private String title;

    @TypeConverters(SubItemConverter.class)
    private ArrayList<SubItem> subItems;

    public HomeItem(String title, ArrayList<SubItem> subItems) {
        this.title = title;
        this.subItems = subItems;
    }
    public String getTitle() {
        return title;
    }

    public ArrayList<SubItem> getSubItems() {
        return subItems;
    }
}
