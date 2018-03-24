package com.example.martin.recyclingapp.db;

import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

/**
 * Created by kamai on 24-Mar-18.
 */

public interface PlaceDao {

    @Query("SELECT * FROM place WHERE uid LIKE :uid LIMIT 1")
    Place findByUid(String uid);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Place place);

}
