package com.example.martin.recyclingapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

/**
 * Created by charlie on 2018-03-18.
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE uid LIKE :uid LIMIT 1")
    User findByUid(String uid);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(User user);

}
