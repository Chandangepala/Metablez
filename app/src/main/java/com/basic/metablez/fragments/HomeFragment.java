package com.basic.metablez.fragments;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.basic.metablez.R;
import com.basic.metablez.adpaters.HomeRecyclVwAdapter;
import com.basic.metablez.adpaters.WalletViewPagerAdapter;
import com.basic.metablez.constants.CommonKeys;
import com.basic.metablez.models.HomeModel;
import com.basic.metablez.services.MyLocationReceiver;
import com.basic.metablez.transformers.ZoomOutPagerTransformer;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;

import javax.xml.transform.Transformer;

import pl.pzienowicz.autoscrollviewpager.AutoScrollViewPager;


public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    AutoScrollViewPager autoScrollVPagerHome;
    WormDotsIndicator wormDotsIndicator;
    RecyclerView recyclerViewHome;
    HomeRecyclVwAdapter homeRecyclVwAdapter;


    DatabaseReference publicLocation;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initMain();

        //To initialize firebase variables
        initFirebase();


        setupViewPager();

        setupRecyclerView();

        return view;
    }

    public void initMain() {
        autoScrollVPagerHome = view.findViewById(R.id.auto_viewpager_home);
        recyclerViewHome = view.findViewById(R.id.recycler_view_home);
        wormDotsIndicator = view.findViewById(R.id.worm_dots_indicator);

    }

    //To initialize firebase variables
    public void initFirebase() {
        publicLocation = FirebaseDatabase.getInstance().getReference(CommonKeys.PUBLIC_LOCATION);
        updateLocation();
    }

    //To setup wallet view pager
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setupViewPager() {
        ArrayList<Integer> arrOffersHome = new ArrayList<>();
        addOffers(arrOffersHome);
        WalletViewPagerAdapter walletVPagerAdapter = new WalletViewPagerAdapter(arrOffersHome, getContext());
        autoScrollVPagerHome.setAdapter(walletVPagerAdapter);
        autoScrollVPagerHome.startAutoScroll(3000);
        autoScrollVPagerHome.setInterval(3000);
        autoScrollVPagerHome.setBorderAnimation(true);
        wormDotsIndicator.setViewPager(autoScrollVPagerHome);
        autoScrollVPagerHome.setSlideBorderMode(AutoScrollViewPager.SlideBorderMode.TO_PARENT);
        //autoScrollVPagerHome.setPageTransformer(false, Pa);
    }

    //To add valus to ArrayList arrOffers
    public void addOffers(ArrayList<Integer> arrOffers) {
        arrOffers.add(R.drawable.slide_img_a);
        arrOffers.add(R.drawable.slide_img_b);
        arrOffers.add(R.drawable.slide_img_c);
        arrOffers.add(R.drawable.slide_img_a);
        arrOffers.add(R.drawable.slide_img_b);
    }

    //To setup home recycler view
    public void setupRecyclerView() {
        ArrayList<HomeModel> arrHomercv = new ArrayList<>();
        //addItems(arrHomercv, "Farm Chicken", R.drawable.farm_chicken_img);
        addItems(arrHomercv, "Chicken", R.drawable.chicken_x);
        addItems(arrHomercv, "Mutton", R.drawable.mutton_img_x);
        addItems(arrHomercv, "Fish & Sea Food", R.drawable.sea_food_img_x);
        addItems(arrHomercv, "Egg", R.drawable.egg_img_x);
        addItems(arrHomercv, "Ready To Eat", R.drawable.ready_to_eat_img_x);
        addItems(arrHomercv, "Ready To Cook", R.drawable.ready_to_cook_img_y);
        addItems(arrHomercv, "Cold cut", R.drawable.cold_cut_img_x);
        addItems(arrHomercv, "Utility Services", R.drawable.utility_img_y);
        addItems(arrHomercv, "Profit Zone", R.drawable.profit_zone_img_x);

        recyclerViewHome.setLayoutManager(new GridLayoutManager(getContext(), 3));
        homeRecyclVwAdapter = new HomeRecyclVwAdapter(getContext(), arrHomercv);
        recyclerViewHome.setAdapter(homeRecyclVwAdapter);
    }

    //To add items in arrlist
    public void addItems(ArrayList<HomeModel> arrayList, String name, int img) {
        HomeModel homeModel = new HomeModel(name, img);
        arrayList.add(homeModel);
    }

    //To update location on firebase realtime database
    public void updateLocation() {
        buildLocationRequest();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext() , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, getPendingIntent());

        //Toast.makeText(getContext(), "Location Request"+ locationRequest, Toast.LENGTH_SHORT).show();
    }

    private PendingIntent getPendingIntent() {

        //Toast.makeText(getContext(), "Location Pending Intent", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), MyLocationReceiver.class);
        intent.setAction(MyLocationReceiver.ACTION);
        return PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


    }

    //Location request to get location
    public void buildLocationRequest(){
        locationRequest = new LocationRequest();
        locationRequest.setSmallestDisplacement(10f);
        locationRequest.setFastestInterval(30000);
        locationRequest.setInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
}