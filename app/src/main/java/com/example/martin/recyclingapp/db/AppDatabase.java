package com.example.martin.recyclingapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by charlie on 2018-03-18.
 */

@Database(entities =  {Item.class, Place.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";

    private static AppDatabase INSTANCE;

    public abstract PlaceDao placeDao();
    public abstract ItemDao itemDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    "user-database")
                            .build();
        }
        return INSTANCE;
    }

    public void syncUserWithFirebase(String uid) {
        ConstantsAndUtils.FIREBASE_USERS_REFERENCE.child(uid).child("items")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.getChildren() != null) {
                            new Thread(() -> {
                                for(DataSnapshot ds: dataSnapshot.getChildren()) {

                                    Item item = ds.getValue(Item.class);
                                    itemDao().insert(item);
                                }
                            }).start();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, databaseError.toString());
                    }
                });
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
