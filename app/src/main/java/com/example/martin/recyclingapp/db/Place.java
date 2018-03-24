package com.example.martin.recyclingapp.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kamai on 24-Mar-18.
 */

public class Place {

    @PrimaryKey
    @NonNull
    private String uid;
    @ColumnInfo(name = "langitude")
    private double langitude;
    @ColumnInfo(name = "longitude")
    private double longitude;
    @ColumnInfo(name = "type")
    private int type;

    public Place(@NonNull String uid,
                double langitude,
                double longitude,
                int type) {
        this.uid = uid;
        this.langitude = langitude;
        this.longitude = longitude;
        this.type = type;
    }

    public Place() {

    }

    @NonNull
    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }

    public double getLangitude() {
        return langitude;
    }

    public void setLangitude(double langitude) {
        this.langitude = langitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getType() {
        return type;
    }

    public void setLongitude(int type) {
        this.type = type;
    }

    public LatLng getLangLng(){
        return new LatLng(this.langitude, this.longitude);
    }
}
