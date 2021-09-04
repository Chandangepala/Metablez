package com.basic.metablez.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.basic.metablez.R;
import com.mukesh.OtpView;

import org.json.JSONObject;

public class VerifyOTPActivity extends AppCompatActivity {

    Button verifyOTP;
    OtpView edtOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);

        initUI();

    }

    //To initialize all the view in the layout
    private void initUI(){
        verifyOTP = findViewById(R.id.btn_verify_otp);
        edtOTP = findViewById(R.id.edt_otp);

        verifyOTP.setOnClickListener(v -> {
            String userId = "32";
            String otp = "123456";//edtOTP.getText().toString().trim();
            verifyOTP(userId, otp); // To call verify otp api to verify otp
        });
    }

    // To call verify otp api usin android networking lib to verify otp
    private void verifyOTP(String userId, String otp){
        String API_URL = "http://meatablez.com/api/v1/verifyOtp";
        AndroidNetworking.initialize(this);
        AndroidNetworking.post(API_URL)
                .addBodyParameter("userId", "40")
                .addBodyParameter("otp", "921979")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                       Toast.makeText(VerifyOTPActivity.this, ""+ response, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(VerifyOTPActivity.this, ""+ anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}