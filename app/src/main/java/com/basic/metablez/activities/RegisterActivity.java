package com.basic.metablez.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.basic.metablez.LoginActivity;
import com.basic.metablez.R;
import com.basic.metablez.modelResponse.RegisterModel;
import com.basic.metablez.retrofit.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity  {

    TextView txtSingIn;
    EditText edtEmail, edtMobileNo, edtPassword;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initMain();

        txtSingIn.setOnClickListener(v -> {
            Intent iSignIn = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(iSignIn);
            finish();
        });

        btnSignUp.setOnClickListener(v -> {

                try {
                    String email = edtEmail.getText().toString().trim();
                    String password = edtPassword.getText().toString().trim();
                    String phoneNo = edtMobileNo.getText().toString().trim();
                    registerUser(email,phoneNo, password);
                    //registerUserAndroidNetworking(email, phoneNo, password);
                    //firebaseSignUp(email, password);
                }catch (Exception e){

                }

        });


    }

    //To initialize all the views
    public void initMain(){
        txtSingIn = findViewById(R.id.signin_txt2);
        edtEmail = findViewById(R.id.edt_email);
        edtMobileNo = findViewById(R.id.edt_mobile_no);
        edtPassword = findViewById(R.id.edt_password);
        btnSignUp = findViewById(R.id.btn_signup);
    }

    //To signup user using firebase email password signup
    private void firebaseSignUp(String email, String password){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            Toast.makeText(this, "Already login", Toast.LENGTH_SHORT).show();
        }
    }

    //To register new user on the server...
    //Using retrofit and rest api
    public void registerUser(String email, String mobileNo, String password){
        Toast.makeText(this, email + "\n" + mobileNo, Toast.LENGTH_SHORT).show();
        Call<RegisterModel> call = RetrofitClient
                .getInstance()
                .getApi()
                .register("meatablez", email, "4",
                        mobileNo, password);
        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                RegisterModel registerModel = response.body();
                if (response.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Successful: "+ response.code(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RegisterActivity.this, "Failed: "+ response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //To hit register api using android networking library to register a user
    //on server
    public void registerUserAndroidNetworking(String email, String mobileNo, String password){
        String API_URL = "http://meatablez.com/api/v1/register";
        AndroidNetworking.initialize(this);
        AndroidNetworking.post(API_URL)
                .addBodyParameter("name", "meatablez")
                .addBodyParameter("email", email)
                .addBodyParameter("role_id", "4")
                .addBodyParameter("phone_number", mobileNo)
                .addBodyParameter("password", password)
                .setPriority(Priority.HIGH)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(RegisterActivity.this, "Response"+ response, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(RegisterActivity.this, "Error: "+ anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}