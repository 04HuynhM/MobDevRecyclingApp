package com.example.martin.recyclingapp.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charlie on 2018-03-18.
 */

@Database(entities =  {Item.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    static final Migration MIGRATION_1_2 = new Migration(1, 2){
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE place;");
        }
    };

    private static final String TAG = "AppDatabase";

    private static AppDatabase INSTANCE;

    public abstract ItemDao itemDao();

    private Item item;

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    "user-database")
                            .addMigrations(MIGRATION_1_2)
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

    public Item getItemFromFirebase(String uid) {
        ConstantsAndUtils.FIREBASE_ITEMS_REFERENCE.child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildren() != null) {
                            item = dataSnapshot.getValue(Item.class);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, databaseError.toString());
                    }
                });
        return item;
    }

    public void addItemToFirebaseUser(String uid, Item item) {

        Map<String, String> map = new HashMap<>();
        map.put("barcodeNumber", item.getBarcodeNumber());
        map.put("productName", item.getProductName());
        map.put("productMaterial", item.getProductMaterial());
        map.put("productCategory", item.getCategory());
        map.put("dateScanned", item.getDateScanned());
        map.put("uid", item.getBarcodeNumber());
//        map.put("imageUrl", item.getImageUrl());

        ConstantsAndUtils.FIREBASE_USERS_REFERENCE
                .child(uid)
                .child("items")
                .child(item.getBarcodeNumber())
                .setValue(map);

        syncUserWithFirebase(uid);

    }

    public void syncPlaceWithFirebase(String uid) {

    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
