package com.basic.metablez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basic.metablez.activities.ForgotPassActivity;
import com.basic.metablez.activities.HomeActivity;
import com.basic.metablez.activities.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtSignup, txtForgotPass;
    Button btnLogin;
    EditText edtMobileNo, edtPassword;
    String mobileNo, password;
    ImageView imgFb, imgInsta, imgTwitter, imgYoutube;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMain();

        //To listen the login request results and get the otp
        firebaseLoginVerificationCallback();
    }

    //To initialize all the views...
    public void initMain(){
        edtMobileNo = findViewById(R.id.edt_mobile_no);
        edtPassword = findViewById(R.id.edt_password);
        txtSignup = findViewById(R.id.signup_txt2);
        txtForgotPass = findViewById(R.id.forgot_pass_txt);
        btnLogin = findViewById(R.id.btn_signin);
        imgFb = findViewById(R.id.fb_icon_img);
        imgInsta = findViewById(R.id.insta_icon_img);
        imgTwitter = findViewById(R.id.twitter_icon_img);
        imgYoutube = findViewById(R.id.youtube_icon_img);

        txtSignup.setOnClickListener(this);
        txtForgotPass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        imgFb.setOnClickListener(this);
        imgInsta.setOnClickListener(this);
        imgTwitter.setOnClickListener(this);
        imgYoutube.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup_txt2:
                startSignup();
                break;
            case R.id.forgot_pass_txt:
                startForgotPass();
                break;
            case R.id.btn_signin:
                startHomeActivity();
                //firebasePhoneLogin();
                getEdtdata();
                firebaseEmailPasSignIn(mobileNo, password);
                break;
            case R.id.fb_icon_img:
                intentToBrowser(getResources().getString(R.string.fb_url));
                break;
            case R.id.insta_icon_img:
                intentToBrowser(getResources().getString(R.string.insta_url));
                break;
            case R.id.twitter_icon_img:
                intentToBrowser(getResources().getString(R.string.twitter_url));
                break;
            case R.id.youtube_icon_img:
                intentToBrowser(getResources().getString(R.string.youtube_url));
                break;
        }
    }

    //To call signup activity intent
    public void startSignup(){
        Intent iSignUp = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(iSignUp);
    }

    //To call forgot_pass activity intent
    public void startForgotPass(){
        Intent iForgot = new Intent(LoginActivity.this, ForgotPassActivity.class);
        startActivity(iForgot);
    }

    //To call HomeActivity Intent
    public void startHomeActivity(){
        Intent iHome = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(iHome);
    }

    //To login on firebase
    public void firebasePhoneLogin(){

        //To get data from edit text
        getEdtdata();

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber(mobileNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    //To get edit text data
    public void getEdtdata(){

        if(edtPassword.getText() != null && edtMobileNo.getText() != null){
            mobileNo = edtMobileNo.getText().toString().trim();
            password = edtPassword.getText().toString().trim();
        }else {
            mobileNo = null;
            password = null;
        }

    }

    //firebase phone login callback
    public void firebaseLoginVerificationCallback(){
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d("Login_TAG", "onVerificationCompleted:" + credential);


                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w( "Login_TAG", "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d( "Login_TAG", "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                String mVerificationId = verificationId;
                PhoneAuthProvider.ForceResendingToken mResendToken = token;

                //PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, password);
            }
        };
    }


    //To sign in on firebase with phone no.
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI

                            startHomeActivity();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }


    //To verify otp code
    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
    }

    //To sign in using firebase email-password sign in method
    private void firebaseEmailPasSignIn(String email, String password){

        if(!email.isEmpty() && !password.isEmpty()){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            startHomeActivity();
                            Toast.makeText(LoginActivity.this, ""+ user.getEmail(), Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    });

        }else {
            Toast.makeText(this, "Login Detail Missing!", Toast.LENGTH_SHORT).show();
        }

    }

    //To call implicit intent to open social media urls in the browser
    public void intentToBrowser(String url){
        Intent iBrowse = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(iBrowse);
    }
}