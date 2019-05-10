package com.test.arc.polimer.dataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.test.arc.polimer.model.HomeItem;

@Database(entities = {HomeItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DataDao dataDao();
}