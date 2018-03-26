package com.example.martin.recyclingapp.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.UUID;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kamai on 24-Mar-18.
 */
@Entity
public class Place {

    @PrimaryKey
    @NonNull
    private String uid = "";
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo(name = "longitude")
    private double longitude;
    @ColumnInfo(name = "type")
    private int type;

    public Place(double latitude,
                 double longitude,
                 PLACE_TYPE type) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type.ordinal();
    }

    public Place() {
    }

    @NonNull
    public String getUid() {
        return this.uid;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getType(){
        return this.type;
    }

    public PLACE_TYPE getEnumType() {
        return PLACE_TYPE.values()[this.getType()];
    }

    public void setType(int type) {
        this.type = type;
    }

    public LatLng getLangLng(){
        return new LatLng(this.getLatitude(), this.getLongitude());
    }
}
