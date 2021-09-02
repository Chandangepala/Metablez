package com.basic.metablez.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.basic.metablez.R;
import com.basic.metablez.models.AddressModel;

public class EditAddressActivity extends AppCompatActivity {

    AddressModel addressModelEdit;
    EditText edtFullName, edtMobile, edtHouse, edtStreet, edtLandmark, edtCity;
    Button btnUpdate;
    String fullName, mobileNo, houseNo, street, landmark, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        //To initialize all the layouts
        initUI();

        //To get data coming along with the intent...
        getIntentData();

        //To set edit text data which got received from intent...
        setEdtData();

        //To listen the button click...
        btnUpdate.setOnClickListener(v -> {
            getEdtData();
            startIntentDelOpt();
        });

    }

    //To initialize all the layouts
    public void initUI(){
        edtFullName = findViewById(R.id.edt_full_name);
        edtMobile = findViewById(R.id.edt_mobile_no);
        edtHouse = findViewById(R.id.edt_house_no);
        edtStreet = findViewById(R.id.edt_street);
        edtLandmark = findViewById(R.id.edt_landmark);
        edtCity = findViewById(R.id.edt_city);

        btnUpdate = findViewById(R.id.btn_update_address);
    }

    //To receive data coming with the intent...
    public void getIntentData(){
        if(getIntent().hasExtra("addressEdit")){
            addressModelEdit = getIntent().getParcelableExtra("addressEdit");
            //Toast.makeText(this, "" + addressModelEdit, Toast.LENGTH_SHORT).show();
        }
    }

    //To set edit text data which got received from intent...
    public void setEdtData(){
        edtFullName.setText(addressModelEdit.fullName);
        edtMobile.setText(addressModelEdit.mobileNo);
        edtHouse.setText(addressModelEdit.houseNo);
        edtStreet.setText(addressModelEdit.street);
        edtLandmark.setText(addressModelEdit.landmark);
        edtCity.setText(addressModelEdit.city);
    }

    //To get data form edit text...
    public void getEdtData(){

        fullName = edtFullName.getText().toString().trim();
        mobileNo = edtMobile.getText().toString().trim();
        houseNo = edtHouse.getText().toString().trim();
        street = edtStreet.getText().toString().trim();
        landmark = edtLandmark.getText().toString().trim();
        city = edtCity.getText().toString().trim();
    }

    //To start intent for Delivery Options Activity...
    public void startIntentDelOpt(){
        Intent iDelOpt = new Intent(EditAddressActivity.this, DeliveryOptionsActivity.class);
        iDelOpt.putExtra("fullName", fullName);
        iDelOpt.putExtra("mobile", mobileNo);
        iDelOpt.putExtra("house", houseNo);
        iDelOpt.putExtra("street", street);
        iDelOpt.putExtra("landmark", landmark);
        iDelOpt.putExtra("city", city);
        startActivity(iDelOpt);
        finish();
    }
}