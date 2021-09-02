package com.basic.retailer_meatablez.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.basic.retailer_meatablez.R;

public class LanguageSelectActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtHindi, txtEnglish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_select);

        initMain();
    }

    //To initialize all the views
    public void initMain(){
        txtHindi = findViewById(R.id.hindi_txt);
        txtEnglish = findViewById(R.id.english_txt);

        txtEnglish.setOnClickListener(this);
        txtHindi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.english_txt:
                startRegisterUserActivity("en");
                break;
            case R.id.hindi_txt:
                startRegisterUserActivity("hi");
                break;
        }

    }

    //Intent with language code to open register user activity
    public void startRegisterUserActivity(String langCode){
        Intent iregUser = new Intent(LanguageSelectActivity.this, RegisterUserActivity.class);
        iregUser.putExtra("langCode", langCode);
        startActivity(iregUser);
    }
}