package com.basic.metablez.map;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.basic.metablez.R;
import com.basic.metablez.constants.CommonKeys;
import com.basic.metablez.map.Models.MyLocation;
import com.basic.metablez.services.MyLocationReceiver;
import com.example.easywaylocation.Listener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartlocation.SmartLocation;

public class MapsTrackingActivity extends FragmentActivity implements OnMapReadyCallback, Listener, ValueEventListener {

    private GoogleMap mMap;
    LatLng latLngHere;
    private Polyline runningPathPolyline;
    private ArrayList<LatLng> routePoints = new ArrayList<>();


    private FusedLocationProviderClient fusedLocationClient;
    DatabaseReference trackingLocationRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_tracking);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //To initialize all the views...
        initUI();

        //to initialize firebase variables
        initFirebase();

        findLocation();
    }

    //To initialize all the views
    public void initUI(){

        fusedLocationClient =  LocationServices.getFusedLocationProviderClient(this);

    }

    //To initialize firebase variables
    public void initFirebase(){
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if(mUser != null){
            trackingLocationRef = FirebaseDatabase.getInstance().getReference(CommonKeys.PUBLIC_LOCATION).child(mUser.getUid());
            trackingLocationRef.addValueEventListener(this);
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }




    //function for finding current location via GPS
    public void findLocation(){

        //continuesly checking current location using smart location
        SmartLocation.with(MapsTrackingActivity.this).location()
                .continuous().start(location -> {
                    Toast.makeText(MapsTrackingActivity.this, "Map Error Found!", Toast.LENGTH_SHORT).show();
                    //lat lng of current location
                    latLngHere = new LatLng(location.getLatitude(), location.getLongitude());
                    //Toast.makeText(getContext(), "" + latLngHere, Toast.LENGTH_SHORT).show();
                    //flag to check that the map is ready so that app doesn't crash

           /* //GPS Marker
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLngHere).title("You're here!")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
            //to move focus to the current lat lng in the map
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngHere));
            //to zoom the camera at current lat lng
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngHere,18));*/

            //function for parsing json data and marking on map with parsed lat lng
            //parseJSONData();
            //spinnerSelectFunc();
        });

        SmartLocation.with(this).location()
                .oneFix()
                .start(location -> {

                    latLngHere = new LatLng(location.getLatitude(), location.getLongitude());
                    //Toast.makeText(getContext(), "" + latLngHere, Toast.LENGTH_SHORT).show();
                    //flag to check that the map is ready so that app doesn't crash

                    //GPS Marker
                    mMap.clear();

                    //To add latLng value to array list
                    routePoints.add(latLngHere);

                    mMap.addMarker(new MarkerOptions().position(latLngHere).title("You're here!")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bike_map)));
                    //to move focus to the current lat lng in the map
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngHere));
                    //to zoom the camera at current lat lng
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngHere,18));

                    mMap.addCircle(new CircleOptions().center(latLngHere).radius(50).strokeColor(R.color.red).fillColor(R.color.liteGray));

                    //To draw a line over tracked map
                    addPolyline();

                });

    }

    //to add marker on parsed Restrau Lat Lng
    public void addMarkerOnMap(Double lat, Double lng) {
        LatLng latLngHereRestru = new LatLng(lat, lng);

        if (true) {
            //GPS Marker
            //mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLngHereRestru).title("Here")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }
    }

    @Override
    public void locationOn() {
        Toast.makeText(this, "location on", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void currentLocation(Location location) {
        latLngHere = new LatLng(location.getLatitude(), location.getLongitude());
        //Toast.makeText(getContext(), "" + latLngHere, Toast.LENGTH_SHORT).show();
        //flag to check that the map is ready so that app doesn't crash

        //GPS Marker
        mMap.clear();

        //adding latLng value to array list
        routePoints.add(latLngHere);

        mMap.addMarker(new MarkerOptions().position(latLngHere).title("You're here!")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.chicken_a)));
        //to move focus to the current lat lng in the map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngHere));
        //to zoom the camera at current lat lng
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngHere,18));

        mMap.addCircle(new CircleOptions().center(latLngHere).radius(50).strokeColor(R.color.red).fillColor(R.color.liteGray));

        //To add line on the tracked map
        addPolyline();
    }

    @Override
    public void locationCancelled() {

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

        if(snapshot.getValue() != null){
            MyLocation location = snapshot.getValue(MyLocation.class);

            //Add marker
            LatLng userMarker = new LatLng(location.getLatitude(), location.getLongitude());

            routePoints.add(userMarker);

            mMap.addMarker(new MarkerOptions().position(userMarker)
                .title(mUser.getEmail())

                .icon(BitmapDescriptorFactory.fromResource(R.drawable.bike_map)));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userMarker, 18));

            addPolyline();

        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    @Override
    protected void onResume() {
        trackingLocationRef.addValueEventListener(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        trackingLocationRef.removeEventListener(this);
        super.onStop();
    }

    //To add line on the tracked map
    private void addPolyline() {

        //array liste of latLng
        ArrayList<LatLng> locationList = routePoints;


        //if more than 2 points stored
        if (locationList.size() >= 2) {

            LatLng fromLocation = locationList.get(locationList.size() - 2); //previous point
            LatLng toLocation = locationList.get(locationList.size() - 1);//current point latLng

            LatLng from = new LatLng(((fromLocation.latitude)),
                    ((fromLocation.longitude)));

            LatLng to = new LatLng(((toLocation.latitude)),
                    ((toLocation.longitude)));

            //creating line between 2 coordinates
            this.runningPathPolyline = mMap.addPolyline(new PolylineOptions()
                    .add(from, to)
                    .color(Color.parseColor("#801B60FE")).geodesic(true));

            //Toast.makeText(this, "Draw polyline", Toast.LENGTH_SHORT).show();

        } else if (locationList.size() > 2) {
            LatLng toLocation = locationList.get(locationList.size() - 1);
            LatLng to = new LatLng(((toLocation.latitude)),
                    ((toLocation.longitude)));

            List<LatLng> points = runningPathPolyline.getPoints();
            points.add(to);

            runningPathPolyline.setPoints(points);
        }
    }
}