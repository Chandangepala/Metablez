package com.basic.delivery_partner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.basic.delivery_partner.activities.HomeActivity;
import com.basic.delivery_partner.activities.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        delayNext(500);
    }

    //To delay any event using handler...
    private void delayNext(int DELAY_TIME){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startLoginActivity();
            }
        }, DELAY_TIME);
    }

    //To call home activity intent
    private void startHomeActivity(){
        Intent iHome = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(iHome);
    }

    //To call login activity intent...
    public void startLoginActivity(){
        Intent iLogin  = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(iLogin);
        finish();
    }
}
