package com.example.martin.recyclingapp.nearby;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.martin.recyclingapp.R;
import com.example.martin.recyclingapp.db.ConstantsAndUtils;
import com.example.martin.recyclingapp.db.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by charlie on 2018-02-02.
 */

public class NearbyFragment extends Fragment implements OnMapReadyCallback {

    public static ArrayList<Place> places = new ArrayList<Place>();
    private float nearRadiusInMeters = 20f;
    private MapView mapView;
    private GoogleMap map;
    private DatabaseReference firebaseReference;
    private DatabaseReference placesReference;
    private CharSequence[] categories = new CharSequence[]{
            "Paper",
            "Plastic",
            "Burnable",
            "Batteries",
            "Lightbulbs",
            "Cans/Metal",
            "Cooking oil",
            "Non-recyclables"
    };
    public static boolean[] filter;
    private final double EARTH_RADIUS = 6378100.0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby, container, false);
        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        placesReference = FirebaseDatabase
                .getInstance()
                .getReferenceFromUrl(ConstantsAndUtils.FIREBASE_PLACES);

        placesReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                Place place = dataSnapshot.getValue(Place.class);
                place.setUid(key);
                places.add(place);
                drawAllMarkers();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Place place = dataSnapshot.getValue(Place.class);
                String key = dataSnapshot.getKey();
                place.setUid(key);
                Place oldPlace = places.get(places.indexOf(place));
                oldPlace.setLatitude(place.getLatitude());
                oldPlace.setLongitude(place.getLongitude());
                oldPlace.setType(place.getType());
                drawAllMarkers();
            }



            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Place place = dataSnapshot.getValue(Place.class);
                String key = dataSnapshot.getKey();
                place.setUid(key);
                removeMarker(place);
                drawAllMarkers();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Place place = dataSnapshot.getValue(Place.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASE", databaseError.toString());
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.add_marker);
        fab.setOnClickListener(v -> {
            onAddPressed();
        });

        filter = new boolean[categories.length];
        for (int i=0; i<filter.length; i++){
            filter[i] = true;
        }

        FloatingActionButton filter = view.findViewById(R.id.filter_marker);
        filter.setOnClickListener(v -> {
            onFilterPressed();
        });
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(true);

        if (ActivityCompat.checkSelfPermission(this.getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            return;
        }

        map.setMyLocationEnabled(true);
        MapsInitializer.initialize(this.getActivity());
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPos(), 17));
        drawAllMarkers();
    }

    private LatLng curPos() {
        LocationManager lm = (LocationManager) this.getActivity()
                .getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this.getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getActivity(),
                                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this.getActivity(),
                            new String[]
                                    { Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION },
                            0);

            return new LatLng(0.0, 0.0);
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return new LatLng(location.getLatitude(),location.getLongitude());
    }

    private ArrayList<Place> placesInRadius(float radius){
        ArrayList<Place> results = new ArrayList<Place>();
        LatLng cur = curPos();
        for (Place place : places){
            if (Math.abs(latLngDist(cur, place.getLangLng())) <= radius){
                results.add(place);
            }
        }
        return results;
    }

    private LatLng coordInCircle(double lat,
                                 double lon,
                                 double radius,
                                 double part,
                                 double total){

        lat = Math.toRadians(lat);
        lon = Math.toRadians(lon);
        double d = radius / EARTH_RADIUS;
        double r = Math.toRadians((part / total) * 360.0);

        double newLat = Math.asin(Math.sin(lat) *
                Math.cos(d) +
                Math.cos(lat) *
                        Math.sin(d) *
                        Math.cos(r));

        double newLon = lon + Math.atan2(Math.sin(r) * Math.sin(d) * Math.cos(lat),
                Math.cos(d) - Math.sin(lat) * Math.sin(newLat));

        return new LatLng(Math.toDegrees(newLat), Math.toDegrees(newLon));
    }

    private boolean isFiltered(Place.PLACE_TYPE type){
        return filter[catToIndex(typeToCat(type))];
    }

    private Place.PLACE_TYPE catToType(CharSequence cat){
        if (cat.equals("Paper")){
            return Place.PLACE_TYPE.PAPER;
        }
        else if (cat.equals("Plastic")){
            return Place.PLACE_TYPE.PLASTIC;
        }
        else if (cat.equals("Burnable")){
            return Place.PLACE_TYPE.BURNABLE;
        }
        else if (cat.equals("Batteries")){
            return Place.PLACE_TYPE.BATTERIES;
        }
        else if (cat.equals("Lightbulbs")){
            return Place.PLACE_TYPE.LIGHTBULBS;
        }
        else if (cat.equals("Cans/Metal")){
            return Place.PLACE_TYPE.CAN;
        }
        else if (cat.equals("Cooking oil")){
            return Place.PLACE_TYPE.OIL;
        }
        return Place.PLACE_TYPE.OTHER;
    }

    private CharSequence typeToCat(Place.PLACE_TYPE type){
        switch(type){
            case PAPER:
                return "Paper";
            case PLASTIC:
                return "Plastic";
            case BURNABLE:
                return "Burnable";
            case BATTERIES:
                return "Batteries";
            case OIL:
                return "Cooking oil";
            case LIGHTBULBS:
                return "Lightbulbs";
            case CAN:
                return "Cans/Metal";
            default:
                break;
        }
        return "Non-recyclables";
    }

    private int catToIndex (CharSequence cat){
        for (int i=0; i<categories.length; i++){
            if (cat.equals(categories[i])) {
                return i;
            }
        }
        return -1;
    }

    private float latLngDist(LatLng a, LatLng b) {
        float[] results = new float[3];
        Location.distanceBetween(a.latitude, a.longitude, b.latitude, b.longitude, results);
        return results[0];
    }

    public void onAddPressed(){
        this.onCreateDialog().show();
    }

    public void onFilterPressed() {
        this.onFilterDialog().show();
    }

    public Dialog onFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(R.string.places_filter_title)
            .setMultiChoiceItems(categories, filter, (dialogInterface, i, b) -> {
                filter[i] = b;
            })
            .setPositiveButton(R.string.add_button, (dialogInterface, i) -> {
                drawAllMarkers();
            })
            .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                drawAllMarkers();
            });
        return builder.create();
    }

    public Dialog onCreateDialog() {
        boolean[] checked = new boolean[categories.length];
        boolean modifying = false;
        final LatLng cur = curPos();
        ArrayList selected = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        ArrayList<Place> nearPlaces = placesInRadius(nearRadiusInMeters);
        for (Place place : nearPlaces){
            int index = catToIndex(typeToCat(place.getType()));
            checked[index] = true;
            selected.add(index);
            modifying = true;
        }
        final boolean isModifying = modifying;
        int title = R.string.places_add_title;
        if (isModifying) {
            title = R.string.places_modify_title;
        }
        builder.setTitle(title)
            .setMultiChoiceItems(categories, checked, (dialogInterface, i, b) -> {
                if (b) {
                    selected.add(i);
                }
                else if (selected.contains(i)){
                    selected.remove(Integer.valueOf(i));
                }
            })
            .setPositiveButton(R.string.add_button, (dialogInterface, i) -> {
                final double total = selected.size();
                Place.PLACE_TYPE type;
                if (isModifying) {
                    for (Place place : nearPlaces) {
                        placesReference.child(place.getUid()).removeValue();
                    }
                }
                int it = 0;
                for (Object _index : selected){
                    int index = (int) _index;
                    type = catToType(categories[index]);
                    addMarker(
                            coordInCircle(
                                    cur.latitude,
                                    cur.longitude,
                                    nearRadiusInMeters / 2f,
                                    ++it,
                                    total),
                            type);
                }
                drawAllMarkers();
            })
            .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                //Don't do anything.
            });
        return builder.create();
    }

    public void clearAllMarkers() {
        map.clear();
    }

    public void removeMarker(Place place) {
        ListIterator it = places.listIterator();
        Place curPlace = (Place) it.next();
        while (curPlace != null) {
            if (place.getUid().equals(curPlace.getUid())){
                it.remove();
                break;
            }
            if (!it.hasNext()) {
                break;
            }
            curPlace = (Place) it.next();
        }
    }

    public void drawAllMarkers() {
        clearAllMarkers();
        for (Place place : places){
            if (isFiltered(place.getType())) {
                this.drawMarker(place);
            }
        }
    }

    private BitmapDescriptor generateIcon(@DrawableRes int vectorResource){

        Drawable background = ContextCompat.getDrawable(
                this.getActivity(),
                R.drawable.ic_marker_background_48dp);

        background.setTint(getResources().getColor(R.color.colorPrimary));
        background.setBounds(0,
                0,
                background.getIntrinsicWidth(),
                background.getIntrinsicHeight());

        Drawable vectorDrawable = ContextCompat
                .getDrawable(this.getActivity(), vectorResource);

        vectorDrawable.setBounds(48,
                48,
                background.getIntrinsicWidth() - 48,
                background.getIntrinsicHeight() - 48);

        Bitmap bitmap = Bitmap
                .createBitmap(background.getIntrinsicWidth(),
                        background.getIntrinsicHeight(),
                        Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void drawMarker(Place place) {
        BitmapDescriptor icon = generateIcon(R.drawable.ic_rubbish);
        switch(place.getType()){
            case PAPER:
                icon = generateIcon(R.drawable.ic_paper);
                break;
            case PLASTIC:
                icon = generateIcon(R.drawable.ic_bottle);
                break;
            case BURNABLE:
                icon = generateIcon(R.drawable.ic_burnables);
                break;
            case BATTERIES:
                icon = generateIcon(R.drawable.ic_battery);
                break;
            case OIL:
                icon = generateIcon(R.drawable.ic_oil);
                break;
            case LIGHTBULBS:
                icon = generateIcon(R.drawable.ic_bulb);
                break;
            case CAN:
                icon = generateIcon(R.drawable.ic_can);
                break;
            case OTHER:
                icon = generateIcon(R.drawable.ic_rubbish);
                break;
        }
        map.addMarker(new MarkerOptions()
                .position(place.getLangLng())
                .icon(icon)
                .draggable(false));
    }

    public Place addMarker(LatLng position, Place.PLACE_TYPE type){
        Place place = new Place(position.latitude, position.longitude, type);
        placesReference.push().setValue(place);
        //places.add(place);
        return place;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
