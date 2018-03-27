package com.example.martin.recyclingapp.db;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by charlie on 2018-03-11.
 */

//TODO Divide these into packages as Utils and Constants
public class ConstantsAndUtils {

    public static final String FIREBASE_USERS = "https://recycling-app-hkr.firebaseio.com/Users";
    public static final String FIREBASE_ITEMS = "https://recycling-app-hkr.firebaseio.com/Items";

    public static final String USER_UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public static final FirebaseDatabase FIREBASE_DATABASE = FirebaseDatabase.getInstance();
    public static final String FIREBASE_PLACES = "https://recycling-app-hkr.firebaseio.com/Places";
    public static final DatabaseReference FIREBASE_USERS_REFERENCE =
            ConstantsAndUtils.FIREBASE_DATABASE.getReferenceFromUrl(ConstantsAndUtils.FIREBASE_USERS);
    public static final DatabaseReference FIREBASE_PLACES_REFERENCE =
            ConstantsAndUtils.FIREBASE_DATABASE.getReferenceFromUrl(ConstantsAndUtils.FIREBASE_PLACES);
    public static final DatabaseReference FIREBASE_ITEMS_REFERENCE =
            ConstantsAndUtils.FIREBASE_DATABASE.getReferenceFromUrl(ConstantsAndUtils.FIREBASE_ITEMS);

    public static void triggerRebirth(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }
}
