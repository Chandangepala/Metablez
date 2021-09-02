package com.basic.retailer_meatablez.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.basic.retailer_meatablez.R;
import com.mukesh.OtpView;

import java.util.Locale;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnRegister, btnVerifyOTP;
    OtpView otpViewRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To change or set the language
        String langCode = getIntent().getStringExtra("langCode");
        changeLanguage(langCode);

        setContentView(R.layout.activity_register_user);

        initMain();

    }

    public void initMain(){
        btnRegister = findViewById(R.id.register_btn);
        btnVerifyOTP = findViewById(R.id.btn_verify_otp);
        otpViewRegister = findViewById(R.id.edt_otp_register);

        btnRegister.setOnClickListener(this);
        btnVerifyOTP.setOnClickListener(this);
    }
    //To change the language of the app using Locale
    public void changeLanguage(String langCode){
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        //here language changed successfully you can handle it with shared preference
    }

    //Intent to call setup shop activity
    public void startSetupShopActivity(){
        Intent iSetupShop = new Intent(RegisterUserActivity.this, SetupShopActivity.class);
        //iSetupShop.putExtra("langCode", "");
        startActivity(iSetupShop);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
                btnRegister.setVisibility(View.GONE);
                otpViewRegister.setVisibility(View.VISIBLE);
                btnVerifyOTP.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_verify_otp:
                startSetupShopActivity();
                break;
        }
    }
}