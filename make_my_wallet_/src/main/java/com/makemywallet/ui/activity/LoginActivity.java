package com.makemywallet.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import com.google.gson.Gson;
import com.makemywallet.MainActivity;
import com.makemywallet.R;
import com.makemywallet.databinding.ActivityLoginBinding;
import com.makemywallet.ui.RestApi.ApiClient;
import com.makemywallet.ui.RestApi.EndPointInterface;
import com.makemywallet.ui.constant.ProgressDialog;
import com.makemywallet.ui.constant.UtilsMethods;
import com.makemywallet.ui.AllResponse.LoginResponse;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    ActivityLoginBinding binding;
    private boolean doubleBackToExitPressedOnce = false;
    private String mMobile = null;
    private String password = null;
    private String mPin = null;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getInitUI();
    }

    private void getInitUI() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(LoginActivity.this)) {
                    mMobile = binding.edtUsername.getText().toString().trim();
                    password = binding.edtPassword.getText().toString().trim();
                    mPin = binding.edtMPin.getText().toString().trim();
                    if (mMobile != null && !mMobile.isEmpty()) {
                        if (password != null && !password.isEmpty()) {
                            if (mPin != null && !mPin.isEmpty()) {
                                getLoginAPI();
                            } else {
                                binding.tvErrorMessage.setVisibility(View.VISIBLE);
                                binding.tvErrorMessage.setText(getResources().getString(R.string.please_enter_mpin));
                            }
                        } else {
                            binding.tvErrorMessage.setVisibility(View.VISIBLE);
                            binding.tvErrorMessage.setText(getResources().getString(R.string.enter_password));
                        }
                    } else {
                        binding.tvErrorMessage.setVisibility(View.VISIBLE);
                        binding.tvErrorMessage.setText(getResources().getString(R.string.enter_mobile));
                    }
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(LoginActivity.this, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                }
            }
        });
        binding.txtforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getLoginAPI() {
        try {
            pd = ProgressDialog.show(LoginActivity.this, "Please Wait...");
            EndPointInterface git = ApiClient.getClient(LoginActivity.this).create(EndPointInterface.class);
            Call<LoginResponse> call = git.getLogin(mMobile, password,mPin);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull retrofit2.Response<LoginResponse> response) {
                    Log.d(TAG, "onResponse:" + "><Login><" + new Gson().toJson(response.body()));
                    try {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (response != null && response.code() == 200 && response.isSuccessful()) {
                            String mStatus = response.body().getStatus();
                            if (mStatus.equals("success")) {
                                String user_id = String.valueOf(response.body().getUserId());
                                String userName = response.body().getName();
                                String userEmail = response.body().getEmail();
                                String mainBalance = response.body().getMainBalance();
                                String aepsBalance = response.body().getAepsBalance();

                                UtilsMethods.INSTANCE.setPreference(LoginActivity.this, "mPIN", mPin);
                                UtilsMethods.INSTANCE.setPreference(LoginActivity.this, "mMobile", mMobile);
                                UtilsMethods.INSTANCE.setPreference(LoginActivity.this, "password", password);
                                UtilsMethods.INSTANCE.setPreference(LoginActivity.this, "user_id", user_id);
                                UtilsMethods.INSTANCE.setPreference(LoginActivity.this, "userName", userName);
                                UtilsMethods.INSTANCE.setPreference(LoginActivity.this, "userEmail", userEmail);
                                UtilsMethods.INSTANCE.setPreference(LoginActivity.this, "mainBalance", mainBalance);
                                UtilsMethods.INSTANCE.setPreference(LoginActivity.this, "aepsBalance", aepsBalance);
                                getSuccessful(LoginActivity.this, "Welcome to MMW");
                            } else {
                                // for error
                                binding.tvErrorMessage.setVisibility(View.VISIBLE);
                                binding.tvErrorMessage.setText(getResources().getString(R.string.invalid_login));
                                if (pd != null && pd.isShowing()) {
                                    pd.dismiss();
                                }
                            }
                        } else {
                            String mError = response.errorBody().string();
                            UtilsMethods.INSTANCE.getShowToast(LoginActivity.this, mError);
                            if (pd != null && pd.isShowing()) {
                                pd.dismiss();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    Log.e("response", "error " + t.getMessage());
                    UtilsMethods.INSTANCE.getShowToast(LoginActivity.this, t.getMessage());
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSuccessful(final Context context, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(context.getResources().getString(R.string.successful_title))
                .setContentText(message)
                .setCustomImage(R.drawable.ic_check_circle_green)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        binding.edtUsername.setText("");
                        binding.edtPassword.setText("");
                        binding.edtMPin.setText("");
                        mMobile = "";
                        password = "";
                        mPin = "";
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        UtilsMethods.INSTANCE.getShowToast(this, "Please click BACK again to exit");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}