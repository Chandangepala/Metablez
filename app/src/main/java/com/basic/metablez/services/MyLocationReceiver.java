package com.basic.metablez.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.basic.metablez.constants.CommonKeys;
import com.google.android.gms.location.LocationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyLocationReceiver extends BroadcastReceiver {
    public static final String ACTION = "com.basic.metablez.services.UPDATE_LOCATION";

    DatabaseReference publicLocation;
    String uid;

    public MyLocationReceiver() {
        publicLocation = FirebaseDatabase.getInstance().getReference(CommonKeys.PUBLIC_LOCATION);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(FirebaseAuth.getInstance().getCurrentUser()!= null)
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (intent != null){
            final String action = intent.getAction();
            if (action.equals(ACTION)){
                LocationResult result = LocationResult.extractResult(intent);
                if (result != null){
                        Location location = result.getLastLocation();
                         if (FirebaseAuth.getInstance().getCurrentUser() != null) //App is in foreground{
                            publicLocation.child(uid).setValue(location);
                         else
                             publicLocation.child(uid).setValue(location);
                }
            }
        }
    }

}
