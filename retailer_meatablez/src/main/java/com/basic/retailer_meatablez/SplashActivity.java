package com.basic.retailer_meatablez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.basic.retailer_meatablez.activities.LanguageSelectActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startLanguageSelectActivity();
            }
        },1200);
    }

    //To call intent of the next activity...
    public void startLanguageSelectActivity(){
        Intent iLang = new Intent(SplashActivity.this, LanguageSelectActivity.class);
        startActivity(iLang);
        finish();
    }
}