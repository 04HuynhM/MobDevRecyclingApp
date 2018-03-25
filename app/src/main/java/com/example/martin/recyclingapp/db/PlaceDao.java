package com.example.martin.recyclingapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by kamai on 24-Mar-18.
 */
@Dao
public interface PlaceDao {

    @Query("SELECT * FROM place WHERE uid LIKE :uid LIMIT 1")
    Place findByUid(String uid);

    @Query("SELECT * FROM place")
    List<Place> findAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Place place);

    @Delete
    void deletePlace(Place place);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Place place);

}
