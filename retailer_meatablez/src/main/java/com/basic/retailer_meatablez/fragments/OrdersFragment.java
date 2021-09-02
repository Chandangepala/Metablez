package com.basic.retailer_meatablez.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basic.retailer_meatablez.R;
import com.basic.retailer_meatablez.adapters.OrderRecyclerVwAdapter;
import com.basic.retailer_meatablez.models.OrderModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    RecyclerView recyclerViewOrder;
    ArrayList<OrderModel> arrOrders = new ArrayList<>();
    OrderRecyclerVwAdapter orderRecyclerVwAdapter;

    public OrdersFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
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
        view = inflater.inflate(R.layout.fragment_orders, container, false);

        initUI();

        addOrder("A", "B", "C");
        setupRecyclerVw();

        return view;
    }

    //To initialize all the views
    public void initUI(){
        recyclerViewOrder = view.findViewById(R.id.recycler_view_orderlist);
    }

    //To setup and populate recyclerview
    public void setupRecyclerVw(){
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        orderRecyclerVwAdapter = new OrderRecyclerVwAdapter(getContext(), arrOrders);
        recyclerViewOrder.setAdapter(orderRecyclerVwAdapter);
    }

    public void addOrder(String orderId, String dateTime, String productName){
        OrderModel orderModel = new OrderModel(orderId, dateTime, productName);
        arrOrders.add(orderModel);
    }
}