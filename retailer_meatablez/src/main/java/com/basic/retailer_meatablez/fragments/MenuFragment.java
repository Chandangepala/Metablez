package com.basic.retailer_meatablez.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basic.retailer_meatablez.R;
import com.basic.retailer_meatablez.adapters.MenuRecyclerVwAdapter;
import com.basic.retailer_meatablez.adapters.OrderRecyclerVwAdapter;
import com.basic.retailer_meatablez.models.MenuProductModel;

import java.util.ArrayList;


public class MenuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    RecyclerView recyclerViewMenu;
    ArrayList<MenuProductModel> arrMenuProduct = new ArrayList<>();
    MenuRecyclerVwAdapter menuRecyclerVwAdapter;

    public MenuFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menu, container, false);

        initUI();

        addItem("A", "B", R.drawable.egg_img);
        setupRecyclerVw();
        return view;
    }

    //To initialize all the views
    public void initUI(){
        recyclerViewMenu = view.findViewById(R.id.recycler_view_menu);
    }

    //To setup and populate recyclerview
    public void setupRecyclerVw(){
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        menuRecyclerVwAdapter = new MenuRecyclerVwAdapter(getContext(), arrMenuProduct);
        recyclerViewMenu.setAdapter(menuRecyclerVwAdapter);
    }

    //To add item in the menu items array list
    public void addItem(String pName, String pPrice, int pImg){
        MenuProductModel menuProductModel = new MenuProductModel(pName,
                pPrice, "", R.drawable.egg_img, 110);
        arrMenuProduct.add(menuProductModel);
    }
}