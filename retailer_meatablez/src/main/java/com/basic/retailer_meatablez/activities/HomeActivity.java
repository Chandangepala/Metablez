package com.basic.retailer_meatablez.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.basic.retailer_meatablez.R;
import com.basic.retailer_meatablez.adapters.HomeRecyclVwAdapter;
import com.basic.retailer_meatablez.fragments.InsightsFragment;
import com.basic.retailer_meatablez.fragments.MenuFragment;
import com.basic.retailer_meatablez.fragments.OrdersFragment;
import com.basic.retailer_meatablez.models.HomeModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerViewHome;
    HomeRecyclVwAdapter homeRecyclVwAdapter;
    BottomNavigationView bottomNavigationVwHome;
    TextView txtTitleToolbar;
    DrawerLayout drawerLayoutHome;
    NavigationView navigViewHome;
    Toolbar toolbarHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initMain();

        //To setup navigation drawer
        setupNavigDrawer();

        setupRecyclerView();
    }

    public void initMain(){
        recyclerViewHome = findViewById(R.id.recycler_view_home);
        bottomNavigationVwHome = findViewById(R.id.btm_navi_home_retailer);
        txtTitleToolbar = findViewById(R.id.toolbar_home_title_text);
        drawerLayoutHome = findViewById(R.id.drawer_layout_home);
        navigViewHome = findViewById(R.id.navigation_drawer_home);
        toolbarHome = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarHome);

        bottomNavigationVwHome.setOnNavigationItemSelectedListener(this);
        bottomNavigationVwHome.setSelectedItemId(R.id.orders);
        loadFragment(new OrdersFragment(), true);

        navigViewHome.setNavigationItemSelectedListener(this);
    }

    //To setup home recycler view
    public void setupRecyclerView(){
        ArrayList<HomeModel> arrHomercv = new ArrayList<>();
        //addItems(arrHomercv, "Farm Chicken", R.drawable.farm_chicken_img);
        addItems(arrHomercv, "Chicken", R.drawable.chicken_a);
        addItems(arrHomercv, "Mutton", R.drawable.mutton_img);
        addItems(arrHomercv, "Fish & Sea Food", R.drawable.sea_food_img);
        addItems(arrHomercv, "Egg", R.drawable.egg_img_a);
        addItems(arrHomercv, "Ready To Eat", R.drawable.ready_to_eat_img_a);
        addItems(arrHomercv, "Ready To Cook", R.drawable.ready_to_cook_img);
        addItems(arrHomercv, "Cold cut", R.drawable.cold_cut_img);
        addItems(arrHomercv, "Utility Services", R.drawable.utility_services_img);
        addItems(arrHomercv, "Profit Zone", R.drawable.profit_zone_img);

        recyclerViewHome.setLayoutManager(new GridLayoutManager(this,3));
        homeRecyclVwAdapter = new HomeRecyclVwAdapter(this, arrHomercv);
        recyclerViewHome.setAdapter(homeRecyclVwAdapter);
    }

    //To add items in arrlist
    public void addItems(ArrayList<HomeModel> arrayList, String name, int img){
        HomeModel homeModel = new HomeModel(name, img);
        arrayList.add(homeModel);
    }

    //To listen bottom navigation item click
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()){
            case R.id.orders:
                loadFragment(new OrdersFragment(), false);
                txtTitleToolbar.setText(getResources().getString(R.string.orders));
                break;
            case R.id.menu:
                loadFragment(new MenuFragment(), false);
                txtTitleToolbar.setText(getResources().getString(R.string.menu));
                break;
            case R.id.insights:
                loadFragment(new InsightsFragment(), false);
                txtTitleToolbar.setText(getResources().getString(R.string.insights));
                break;
        }
        return false;
    }

    //To load fragment in the fragment container frame layout
    public void loadFragment(Fragment fragment, boolean addReplace){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(addReplace){
            ft.add(R.id.fragment_container_home, fragment);
        }else {
            ft.replace(R.id.fragment_container_home, fragment);
        }
        ft.commit();
    }

    //To setup navigation drawer...
    public void setupNavigDrawer(){
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayoutHome, toolbarHome,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayoutHome.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        //drawerLayoutHome.closeDrawer(Gravity.RIGHT);
    }
}