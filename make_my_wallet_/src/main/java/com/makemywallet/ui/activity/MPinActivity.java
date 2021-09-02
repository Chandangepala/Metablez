package com.makemywallet.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.makemywallet.MainActivity;
import com.makemywallet.R;
import com.makemywallet.databinding.ActivityMPinBinding;
import com.makemywallet.ui.constant.ProgressDialog;
import com.makemywallet.ui.constant.UtilsMethods;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MPinActivity extends AppCompatActivity {
    private static final String TAG = "MPinActivity";
    private boolean doubleBackToExitPressedOnce = false;
    ActivityMPinBinding binding;
    private String mPin = null;
    ProgressDialog pd;
    String email = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMPinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getInitUI();
    }

    private void getInitUI() {
        Intent intent = getIntent();
        if (intent != null) {
            email = intent.getStringExtra("email");
            password = intent.getStringExtra("password");
        }
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(MPinActivity.this)) {
                    mPin = binding.edtMPin.getText().toString().trim();
                    if (mPin != null && !mPin.isEmpty()) {
                       // getMPinAPI();
                    } else {
                        binding.tvErrorMessage.setVisibility(View.VISIBLE);
                        binding.tvErrorMessage.setText(getResources().getString(R.string.please_enter_mpin));
                    }
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(MPinActivity.this, getResources().getString(R.string.attention_error_title),
                            getResources().getString(R.string.connection_lost));
                }
            }
        });
    }

 /*   private void getMPinAPI() {
        try {
            pd = ProgressDialog.show(MPinActivity.this, "Please Wait...");
            EndPointInterface git = ApiClient.getClient(MPinActivity.this).create(EndPointInterface.class);
            Call<LoginResponse> call = git.getMPin(email, password, mPin);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull retrofit2.Response<LoginResponse> response) {
                    Log.d(TAG, "onResponse:" + "><mPin><" + new Gson().toJson(response.body()));
                    try {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (response != null && response.code() == 200 && response.isSuccessful()) {
                            String mStatus = response.body().getStatus();
                            if (mStatus.equals("success")) {
                                getSuccessful(MPinActivity.this, "Welcome to MMW");
                                binding.edtMPin.setText("");
                                mPin = "";

                            } else {
                                binding.tvErrorMessage.setVisibility(View.VISIBLE);
                                binding.tvErrorMessage.setText(getResources().getString(R.string.invalid_login));
                                if (pd != null && pd.isShowing()) {
                                    pd.dismiss();
                                }
                            }
                        } else {
                            String mError = response.errorBody().string();
                            UtilsMethods.INSTANCE.getShowToast(MPinActivity.this, mError);
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
                    UtilsMethods.INSTANCE.getShowToast(MPinActivity.this, t.getMessage());
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    public void getSuccessful(final Context context, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(context.getResources().getString(R.string.successful_title))
                .setContentText(message)
                .setCustomImage(R.drawable.ic_check_circle_green)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        Intent intent = new Intent(MPinActivity.this, MainActivity.class);
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