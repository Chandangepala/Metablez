package com.makemywallet.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.makemywallet.databinding.ActivityForgetPasswordBinding;

public class ForgetPasswordActivity extends AppCompatActivity {
    ActivityForgetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getInitUI();
    }

    private void getInitUI() {
        binding.header.tvHeaderTittle.setText("Forget Password");
        binding.header.tvBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* oldPassword = binding.edtOldPassword.getText().toString();
                nPassword = binding.edtNPassword.getText().toString();
                confirmPassword = binding.edtConfirmPassword.getText().toString();
                if (oldPassword != null && oldPassword.trim().length() > 0) {
                    if (nPassword != null && nPassword.trim().length() > 0) {
                        if (nPassword.equals(confirmPassword)) {
                            if (isInternetConnected(mActivity)) {
                                getChangePass();
                            } else {
                                getShowToast(mActivity, getResources().getString(R.string.oopsConnectInternet));
                            }
                        }else {
                            getShowToast(ChangePassActivity.this, "New password and confirm password must be same");
                        }
                    } else {
                        getShowToast(ChangePassActivity.this, "Please enter New Password");
                    }
                } else {
                    getShowToast(ChangePassActivity.this, "Please enter Old Password");
                }*/
            }
        });
    }

}