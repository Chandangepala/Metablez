package com.basic.metablez.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basic.metablez.R;
import com.basic.metablez.fragments.CartFragment;
import com.basic.metablez.fragments.HomeFragment;
import com.basic.metablez.fragments.WalletFragment;
import com.basic.metablez.map.MapsTrackingActivity;
import com.basic.metablez.models.ExpandedMenuModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class  HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener
, ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {

    BottomNavigationView btmNavigationView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ExpandableListView expandableListViewMenu;
    List<ExpandedMenuModel> listDataHeader = new ArrayList<>();
    HashMap<ExpandedMenuModel, List<String>> listDataChild = new HashMap<>();
    ImageView imgMap;
    TextView txtToolbartitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //To check permissions
        checkPermissions();

        //To initialize all the views
        initMain();

        //To setup navigation drawer
        setupNavigationDrawer();

        imgMap.setOnClickListener(v -> {
                startMapActivity();
        });
    }

    //To initialize all the views...
    public void initMain(){
        btmNavigationView = findViewById(R.id.bottom_navigtion);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_drawer);
        expandableListViewMenu = findViewById(R.id.navigation_menu);
        imgMap = findViewById(R.id.img_map_home);
        txtToolbartitle = findViewById(R.id.toolbar_home_title_text);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        btmNavigationView.setOnNavigationItemSelectedListener(this);
        btmNavigationView.setSelectedItemId(R.id.homeFragment);
        navigationView.setNavigationItemSelectedListener(this);

        String navigPage = "";
        if(getIntent() != null){
            navigPage = getIntent().getStringExtra("navigPage");
        }

        if (navigPage != null){
            if(navigPage.equals("cart")){
                txtToolbartitle.setText("Cart");
                btmNavigationView.setSelectedItemId(R.id.cartFrag);
                loadFragment(new CartFragment(), true);
            }
        }

    }

    //To setup navigation drawer
    public void setupNavigationDrawer(){
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        //loadFragment(new HomeFragment(),true);
        navigationView.setCheckedItem(R.id.nav_my_account);

        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        prepareListData();
        ExpandableListAdapter mMenuAdapter = new com.basic.metablez.adpaters.ExpandableListAdapter(this, listDataHeader,   listDataChild, expandableListViewMenu);

        // setting list adapter
        expandableListViewMenu.setAdapter(mMenuAdapter);
        expandableListViewMenu.deferNotifyDataSetChanged();
    }

    //To load a new fragment add or replace fragment
    public void loadFragment(Fragment fragment, boolean flag){
        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragTransction = fragManager.beginTransaction();

        if(flag){
            fragTransction.add(R.id.container_home, fragment);

        }else {
            fragTransction.replace(R.id.container_home, fragment);
        }
        fragTransction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.homeFragment:
                txtToolbartitle.setText("Home");
                loadFragment(new HomeFragment(),true);
                break;
            case R.id.walletFrag:
                txtToolbartitle.setText("Offers");
                loadFragment(new WalletFragment(), false);
                break;
            case R.id.cartFrag:
                txtToolbartitle.setText("Cart");
                loadFragment(new CartFragment(), false);
                break;
            case R.id.nav_my_account:
                loadFragment(new HomeFragment(), true);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.nav_contact_us:
                loadFragment(new WalletFragment(), false);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.nav_refer:
                loadFragment(new CartFragment(), false);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.nav_zone:
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
        }
        return true;
    }


    //To add data in the menu and submenu of nav_drawer
    private void prepareListData() {
        listDataHeader = new ArrayList<ExpandedMenuModel>();
        listDataChild = new HashMap<ExpandedMenuModel, List<String>>();

        // Adding data header
        listDataHeader.add(new ExpandedMenuModel("Home", R.drawable.ic_baseline_home_));
        listDataHeader.add(new ExpandedMenuModel("My Account", R.drawable.ic_baseline_person_));
        listDataHeader.add(new ExpandedMenuModel("Contact Us", R.drawable.ic_baseline_phone_));
        listDataHeader.add(new ExpandedMenuModel("Refer a friend", R.drawable.ic_baseline_refer));
        listDataHeader.add(new ExpandedMenuModel("Meatablez Zone", R.drawable.app_logo));
        listDataHeader.add(new ExpandedMenuModel("Logout", R.drawable.ic_baseline_logout));

        // Adding child data
        List<String> heading1= new ArrayList<String>();
        heading1.add("My Profile");
        heading1.add("My Rewards");
        heading1.add("My Orders");
        heading1.add("My Address");
        heading1.add("My Wallet");
        heading1.add("My Notification");

        List<String> heading2= new ArrayList<String>();
        heading2.add("Recipes");
        heading2.add("Blog");
        heading2.add("Terms & Conditions");
        heading2.add("FAQ's");
        heading2.add("Privacy Policy");
        heading2.add("Share & Love");

        listDataChild.put(listDataHeader.get(1), heading1);// Header, Child data
        listDataChild.put(listDataHeader.get(4), heading2);

        if(listDataChild == null){
            expandableListViewMenu.setGroupIndicator(null);
        }else {

        }
        expandableListViewMenu.setOnGroupClickListener(this);
        expandableListViewMenu.setOnChildClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_drawer_menu, menu);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_notifications_24);
        toolbar.setOverflowIcon(drawable);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        if(groupPosition == 1){
            switch (childPosition){
                case 0:
                    Intent iMyProfile = new Intent(HomeActivity.this, MyProfileActivity.class);
                    startActivity(iMyProfile);
                    break;
                case 3:
                    Intent iMyAddress = new Intent(HomeActivity.this, ChooseAdressActivity.class);
                    startActivity(iMyAddress);
                    break;
            }
        }
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        switch (groupPosition){
            case 0:
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case 1:
                break;
            case 2:
                Intent icontactUs = new Intent(HomeActivity.this, ContactUsActivity.class);
                startActivity(icontactUs);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case 3:
                Intent iRefer = new Intent(HomeActivity.this, ReferEarnActivity.class);
                startActivity(iRefer);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case 4:
                break;
            case 5:
                finish();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        else {
            super.onBackPressed();
        }
    }

    public void startMapActivity(){
        Intent intentMap = new Intent(HomeActivity.this, MapsTrackingActivity.class);
        startActivity(intentMap);
    }

    // function for permissions
    public void checkPermissions(){
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }
}