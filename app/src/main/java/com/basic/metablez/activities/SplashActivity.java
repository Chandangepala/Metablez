package com.basic.metablez.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.basic.metablez.LoginActivity;
import com.basic.metablez.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //To call next activity intent
                startNextActivity();
            }
        },1200);
    }

    //To call next activity intent
    public void startNextActivity(){
        Intent iNext = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(iNext);
        finish();
    }
}
