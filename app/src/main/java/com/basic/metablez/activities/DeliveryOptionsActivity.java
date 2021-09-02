package com.basic.metablez.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.basic.metablez.R;
import com.basic.metablez.adpaters.AddressListRecyclerVwAdapter;
import com.basic.metablez.clickListeners.AddressListeners;
import com.basic.metablez.models.AddressModel;
import com.basic.metablez.models.ProductModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DeliveryOptionsActivity extends AppCompatActivity implements View.OnClickListener, AddressListeners {

    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
    TextView txtContinueBtn, txtEditBtn, txtAddNewAddress, toolbarTitleTxt,
                txtTimingA, txtTimingB, txtTimingC, txtTimingD, txtTimingE, txtTimingF;
    String fullName, mobileNo, houseNo, street, landmark, city;
    RecyclerView recyclerVwAddressList;
    ArrayList<AddressModel> arrAddressList = new ArrayList<>();
    AddressListRecyclerVwAdapter addressAdapter;
    ImageView imgBack;
    Boolean selectedA, selectedB, selectedC, selectedD, selectedE, selectedF = false;
    String deliveryTime, deliveryDate = "";
    RadioButton radioButtonToday, radioButtonTomorrow;
    ArrayList<ProductModel> arrProductCart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_options);

        //To initialize all the views in the layout
        initMain();

        //To add static address data in the array list
        addAddress("Meet", "B-211", "Sector 11 Malviya Nager", "Jaipur", "943210022");

        //To setup and inflate recycler view with address array list data
        setupAddressRecyclerVw();

    }

    //To initialize all the views in layout
    public void initMain(){

        recyclerVwAddressList = findViewById(R.id.recycler_vw_address_list);
        txtContinueBtn = findViewById(R.id.txt_continue_btn);
        txtEditBtn = findViewById(R.id.edit_txt_btn_deliv_opt);
        txtAddNewAddress = findViewById(R.id.txt_add_new_address);
        toolbarTitleTxt = findViewById(R.id.toolbar_home_title_text);
        imgBack = findViewById(R.id.back_btn_img);

        txtTimingA = findViewById(R.id.txt_timing_a);
        txtTimingB = findViewById(R.id.txt_timing_b);
        txtTimingC = findViewById(R.id.txt_timing_c);
        txtTimingD = findViewById(R.id.txt_timing_d);
        txtTimingE = findViewById(R.id.txt_timing_e);
        txtTimingF = findViewById(R.id.txt_timing_f);

        radioButtonToday = findViewById(R.id.radio_btn_today);
        radioButtonTomorrow = findViewById(R.id.radio_btn_tomorrow);

        txtContinueBtn.setOnClickListener(this);
        txtEditBtn.setOnClickListener(this);
        txtAddNewAddress.setOnClickListener(this);

        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(this);

        txtTimingA.setOnClickListener(this);
        txtTimingB.setOnClickListener(this);
        txtTimingC.setOnClickListener(this);
        txtTimingD.setOnClickListener(this);
        txtTimingE.setOnClickListener(this);
        txtTimingF.setOnClickListener(this);


        toolbarTitleTxt.setText(R.string.delivery_options);
        //To get intent data
        getIntentData();

        radioButtonToday.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                deliveryDate = formatter.format(date);
            }
        });

        radioButtonTomorrow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                Date date = new Date();
                Date dateNxt = new Date(date.getTime() + MILLIS_IN_A_DAY);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                deliveryDate = formatter.format(dateNxt);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_txt_btn_deliv_opt:
                Log.d("Address", "Edit Address");
                Toast.makeText(this, "Edit Address", Toast.LENGTH_SHORT).show();
                break;
            case R.id.txt_continue_btn:
                Intent iPayment = new Intent(this, PaymentMehodActivity.class);
                iPayment.putExtra("deliveryDate", deliveryDate);
                iPayment.putExtra("deliveryTime", deliveryTime);
                iPayment.putExtra("arrProductCart", arrProductCart);
                startActivity(iPayment);
                break;
            case R.id.txt_add_new_address:
                Intent intent = new Intent(this, AddAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.back_btn_img:
                finish();
                break;
            case R.id.txt_timing_a:
                selectedA = true;
                selectedB = selectedC = selectedD = selectedE = selectedF = false;
                timingSelected();
                deliveryTime = "9 AM - 11 AM";
                break;
            case R.id.txt_timing_b:
                selectedB = true;
                selectedA = selectedC = selectedD = selectedE = selectedF = false;
                timingSelected();
                deliveryTime = "11 AM - 1 PM";
                break;
            case R.id.txt_timing_c:
                selectedC = true;
                selectedA = selectedB = selectedD = selectedE = selectedF = false;
                timingSelected();
                deliveryTime = "1 PM - 3 PM";
                break;
            case R.id.txt_timing_d:
                selectedD = true;
                selectedA = selectedB = selectedC = selectedE = selectedF = false;
                timingSelected();
                deliveryTime = "3 PM - 5 PM";
                break;
            case R.id.txt_timing_e:
                selectedE = true;
                selectedA = selectedB = selectedC = selectedD = selectedF = false;
                timingSelected();
                deliveryTime = "5 PM - 7 PM";
                break;
            case R.id.txt_timing_f:
                selectedF = true;
                selectedA = selectedB = selectedC = selectedD = selectedE = false;
                timingSelected();
                deliveryTime = "7 PM - 9 PM";
                break;
        }
    }

    //To get data which comes with intent
    public void getIntentData(){
        if(getIntent() != null  && getIntent().hasExtra("fullName")){
            fullName = getIntent().getStringExtra("fullName");
            mobileNo = getIntent().getStringExtra("mobile");
            houseNo = getIntent().getStringExtra("house");
            street = getIntent().getStringExtra("street");
            landmark = getIntent().getStringExtra("landmark");
            city = getIntent().getStringExtra("city");

            String fStreet = street + ", " + landmark;
            addAddress(fullName, houseNo, fStreet, city, mobileNo);
            Log.d("NewAddressData", fullName + "\n" + mobileNo + "\n" +houseNo + "\n" + street + "\n" + landmark  + "\n" + city );
        }

        if(getIntent().hasExtra("arrProductCart")){
            arrProductCart = (ArrayList<ProductModel>) getIntent().getSerializableExtra("arrProductCart");
            Toast.makeText(this, ""+ arrProductCart.get(0), Toast.LENGTH_SHORT).show();
        }
    }

    //To setup address recycler view
    public void setupAddressRecyclerVw(){
        recyclerVwAddressList.setLayoutManager(new LinearLayoutManager(this));
        recyclerVwAddressList.setHasFixedSize(true);
        addressAdapter = new AddressListRecyclerVwAdapter(this, arrAddressList, this);
        recyclerVwAddressList.setAdapter(addressAdapter);
    }

    //To add address in array list address-list
    public void addAddress(String fullName, String houseNo, String street, String city, String mobileNo){
        AddressModel addressModel = new AddressModel(fullName, mobileNo, houseNo, street, "", city,"");
        arrAddressList.add(addressModel);
    }

    @Override
    public void updateAddress(int position, AddressModel addressModel) {
        Intent iUpdate = new Intent(DeliveryOptionsActivity.this, EditAddressActivity.class);
        iUpdate.putExtra("addressEdit", addressModel);
        startActivityForResult(iUpdate, 109);
    }

    @Override
    public void deleteAddress(int position) {
        arrAddressList.remove(position);
        addressAdapter.notifyItemChanged(position);
    }

    //To read or listen results coming with the activity...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Change background when a particular time is selected.
    public void timingSelected(){
        if(selectedA){
            txtTimingA.setBackground(getResources().getDrawable(R.drawable.selected_box_background));
            txtTimingB.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingC.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingD.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingE.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingF.setBackground(getResources().getDrawable(R.drawable.search_box_background));
        }else if(selectedB){
            txtTimingA.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingB.setBackground(getResources().getDrawable(R.drawable.selected_box_background));
            txtTimingC.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingD.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingE.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingF.setBackground(getResources().getDrawable(R.drawable.search_box_background));
        }else if(selectedC){
            txtTimingA.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingB.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingC.setBackground(getResources().getDrawable(R.drawable.selected_box_background));
            txtTimingD.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingE.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingF.setBackground(getResources().getDrawable(R.drawable.search_box_background));
        }else if(selectedD){
            txtTimingA.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingB.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingC.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingD.setBackground(getResources().getDrawable(R.drawable.selected_box_background));
            txtTimingE.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingF.setBackground(getResources().getDrawable(R.drawable.search_box_background));
        }else if(selectedE){
            txtTimingA.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingB.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingC.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingD.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingE.setBackground(getResources().getDrawable(R.drawable.selected_box_background));
            txtTimingF.setBackground(getResources().getDrawable(R.drawable.search_box_background));
        }else if(selectedF){
            txtTimingA.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingB.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingC.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingD.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingE.setBackground(getResources().getDrawable(R.drawable.search_box_background));
            txtTimingF.setBackground(getResources().getDrawable(R.drawable.selected_box_background));
        }
    }
}