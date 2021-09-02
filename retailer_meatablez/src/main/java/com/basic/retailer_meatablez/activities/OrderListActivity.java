package com.basic.retailer_meatablez.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.basic.retailer_meatablez.R;
import com.basic.retailer_meatablez.adapters.OrderRecyclerVwAdapter;
import com.basic.retailer_meatablez.models.OrderModel;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {

    RecyclerView recyclerViewOrder;
    ArrayList<OrderModel> arrOrders = new ArrayList<>();
    OrderRecyclerVwAdapter orderRecyclerVwAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        initUI();

        addOrder("A", "B", "C");
        setupRecyclerVw();
    }

    //To initialize all the views
    public void initUI(){
        recyclerViewOrder = findViewById(R.id.recycler_view_orderlist);
    }

    //To setup and populate recyclerview
    public void setupRecyclerVw(){
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(this));
        orderRecyclerVwAdapter = new OrderRecyclerVwAdapter(this, arrOrders);
        recyclerViewOrder.setAdapter(orderRecyclerVwAdapter);
    }

    public void addOrder(String orderId, String dateTime, String productName){
        OrderModel orderModel = new OrderModel(orderId, dateTime, productName);
        arrOrders.add(orderModel);
    }
}