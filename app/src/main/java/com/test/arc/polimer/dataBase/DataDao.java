package com.test.arc.polimer.dataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.test.arc.polimer.model.HomeItem;

import java.util.List;

@Dao
public interface DataDao {

    @Query("SELECT * FROM homeitem")
    List<HomeItem> getAll();

    @Query("SELECT * FROM homeitem WHERE id = :id")
    HomeItem getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HomeItem items);

    @Update
    void update(HomeItem items);

    @Delete
    void delete(HomeItem items);

}
