package com.example.martin.recyclingapp.db;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;

/**
 * Created by kamai on 24-Mar-18.
 */

public class Place {

    @Exclude
    private String uid = "";
    private double latitude;


    private double longitude;
    private PLACE_TYPE type;

    public Place(double latitude,
                 double longitude,
                 PLACE_TYPE type) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    public Place() {
    }

    @Exclude
    public String getUid() {
        return this.uid;
    }

    @Exclude
    public void setUid(String uid) {
        this.uid = uid;
    }

    public PLACE_TYPE getType() {
        return this.type;
    }

    public void setType(PLACE_TYPE type) {
        this.type = type;
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

    @Exclude
    public LatLng getLangLng(){
        return new LatLng(this.latitude, this.longitude);
    }
}
