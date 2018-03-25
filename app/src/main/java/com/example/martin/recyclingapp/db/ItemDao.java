package com.example.martin.recyclingapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by charlie on 2018-03-21.
 */

@Dao
public interface ItemDao {

    @Query("SELECT * FROM item WHERE uid LIKE :uid LIMIT 1")
    Item findByUid(String uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Item item);

    @Query("SELECT * FROM item")
    List<Item> getItems();

}
