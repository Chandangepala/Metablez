package com.basic.metablez.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.basic.metablez.R;

public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txttoolbartitle;
    ImageView imgBack;
    EditText edtFullName, edtMobile, edtHouse, edtStreet, edtLandMark, edtCity;
    String fullName, mobileNo, houseNo, street, landmark, city;
    Button btnAddAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        //To initialize all the layouts
        initUI();
    }

    //To initialize all the layouts
    public void initUI(){
        txttoolbartitle = findViewById(R.id.toolbar_home_title_text);
        imgBack = findViewById(R.id.back_btn_img);

        edtFullName = findViewById(R.id.edt_full_name);
        edtMobile = findViewById(R.id.edt_mobile_no);
        edtHouse = findViewById(R.id.edt_house_no);
        edtStreet = findViewById(R.id.edt_street);
        edtLandMark = findViewById(R.id.edt_landmark);
        edtCity = findViewById(R.id.edt_city);
        btnAddAddress = findViewById(R.id.btn_add_address);

        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(this);
        txttoolbartitle.setText(R.string.add_new_address);
        btnAddAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case  R.id.back_btn_img:
               finish();
               break;
            case R.id.btn_add_address:
                getEdtData(); //To get edit text data
                addAddressIntent(); //To call Delivery option activity intent
                break;
        }
    }

    //Intent for Delivery Options activity
    public void addAddressIntent(){
        Intent iAddress = new Intent(AddAddressActivity.this, DeliveryOptionsActivity.class);
        iAddress.putExtra("fullName", fullName);
        iAddress.putExtra("mobile", mobileNo);
        iAddress.putExtra("house", houseNo);
        iAddress.putExtra("street", street);
        iAddress.putExtra("landmark", landmark);
        iAddress.putExtra("city", city);
        startActivity(iAddress);
    }

    //To get edit text  data
    public void getEdtData(){
        try {
            fullName = edtFullName.getText().toString().trim();
            mobileNo = edtMobile.getText().toString().trim();
            houseNo = edtHouse.getText().toString().trim();
            street = edtStreet.getText().toString().trim();
            landmark = edtLandMark.getText().toString().trim();
            city = edtCity.getText().toString().trim();
        }catch (Exception e){

        }
    }
}