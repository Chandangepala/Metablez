package com.basic.metablez.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basic.metablez.R;
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

public class ForgotPassActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtMoblilNo, edtOTP;
    Button btnSend, btnVerify;
    TextView txtForgotPass;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        initMain();

        firebaseLoginVerificationCallback();

    }

    //To initialize all the views
    public void initMain(){
        edtMoblilNo = findViewById(R.id.edt_mobile_no);
        edtOTP = findViewById(R.id.edt_otp);
        btnSend =  findViewById(R.id.btn_send_otp);
        btnVerify = findViewById(R.id.btn_verify_otp);
        txtForgotPass = findViewById(R.id.forgot_pass_otp_txt);

        btnSend.setOnClickListener(this);
        btnVerify.setOnClickListener(this);
        txtForgotPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_otp:
                edtMoblilNo.setVisibility(View.GONE);
                btnSend.setVisibility(View.GONE);
                edtOTP.setVisibility(View.VISIBLE);
                btnVerify.setVisibility(View.VISIBLE);
                txtForgotPass.setText("Enter OTP");

                String mobileNo = "+91" + edtMoblilNo.getText().toString().trim();
                firebasePhoneLogin(mobileNo);

                break;
            case R.id.btn_verify_otp:
                 String otp = edtOTP.getText().toString().trim();
                 verifyPhoneNumberWithCode(mVerificationId, otp);
                 //startResetPassActivity();
                break;
        }
    }

    //To call ResetPassword actitvity intent
    public void startResetPassActivity(){
        Intent iReset = new Intent(ForgotPassActivity.this, ResetPasswordActivity.class);
        startActivity(iReset);
        finish();
    }

    public void firebasePhoneLogin(String mobileNo){


        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber(mobileNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
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
                mVerificationId = verificationId;
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
        signInWithPhoneAuthCredential(credential);
        Toast.makeText(this, "Verifying OTP", Toast.LENGTH_SHORT).show();
        // [END verify_with_code]
    }

    //To call HomeActivity Intent
    public void startHomeActivity(){
        Intent iHome = new Intent(ForgotPassActivity.this, HomeActivity.class);
        startActivity(iHome);
    }
}