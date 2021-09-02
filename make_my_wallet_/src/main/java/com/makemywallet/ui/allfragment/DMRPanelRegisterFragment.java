package com.makemywallet.ui.allfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makemywallet.R;
import com.makemywallet.databinding.FragmentDMRPanelRegisterBinding;
import com.makemywallet.ui.constant.ApplicationConstant;
import com.makemywallet.ui.constant.ProgressDialog;
import com.makemywallet.ui.constant.UtilsMethods;
import org.json.JSONException;
import org.json.JSONObject;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class DMRPanelRegisterFragment extends Fragment {
    FragmentDMRPanelRegisterBinding binding;
    Activity mActivity;
    BottomSheetDialog dialogSendOtp;
    String mName = "";
    String mSurname = "";
    String mPinCode = "";
    String mAddress1 = "";
    String mAddress2 = "";
    String sendOtp = "";
    ProgressDialog pd;
    EditText edtSentOtp;
    TextView tvErrorMessageOTP;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDMRPanelRegisterBinding.inflate(inflater, container, false);
        mActivity = getActivity();
        getInitUI();
        return binding.getRoot();
    }

    private void getInitUI() {
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    mName = binding.edtName.getText().toString().trim();
                    mSurname = binding.edtSurName.getText().toString().trim();
                    mPinCode = binding.edtPinCode.getText().toString().trim();
                    mAddress1 = binding.edtAddress1.getText().toString().trim();
                    mAddress2 = binding.edtAddress2.getText().toString().trim();

                    if (mName != null && !mName.isEmpty()) {
                        if (mSurname != null && !mSurname.isEmpty()) {
                            if (mPinCode != null && !mPinCode.isEmpty()) {
                                if (mAddress1 != null && !mAddress1.isEmpty()) {
                                    if (mAddress2 != null && !mAddress2.isEmpty()) {
                                        getRegister();
                                    } else {
                                        binding.tvErrorMessage.setVisibility(View.VISIBLE);
                                        binding.tvErrorMessage.setText(getResources().getString(R.string.enter_address_2));
                                    }
                                } else {
                                    binding.tvErrorMessage.setVisibility(View.VISIBLE);
                                    binding.tvErrorMessage.setText(getResources().getString(R.string.enter_address_1));
                                }
                            } else {
                                binding.tvErrorMessage.setVisibility(View.VISIBLE);
                                binding.tvErrorMessage.setText(getResources().getString(R.string.enter_pincode));
                            }
                        } else {
                            binding.tvErrorMessage.setVisibility(View.VISIBLE);
                            binding.tvErrorMessage.setText(getResources().getString(R.string.enter_surname));
                        }
                    } else {
                        binding.tvErrorMessage.setVisibility(View.VISIBLE);
                        binding.tvErrorMessage.setText(getResources().getString(R.string.enter_name));
                    }
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                }
            }
        });
    }

    private void getRegister() {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            String mobile = UtilsMethods.INSTANCE.getPreference(mActivity, "mMobile");
            String mPassword = UtilsMethods.INSTANCE.getPreference(mActivity, "password");
            String searchMobile = UtilsMethods.INSTANCE.getPreference(mActivity, "searchMobile");

            String url = ApplicationConstant.INSTANCE.GET_ADD_SENDER + "?username=" + mobile + "&password=" + mPassword + "&mobile=" + searchMobile + "&fname=" + mName + "&lname=" + mSurname + "&pin_code=" + mPinCode + "&address1=" + mAddress1 + "&address2=" + mAddress2;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    if (response != null && response.has("status")) {
                        try {
                            int status = response.getInt("status");
                            String message = response.getString("message");
                            if (status == 0 && message.equals("success")) {
                                getSuccessful(mActivity, message);
                            } else {
                                binding.edtName.setText("");
                                binding.edtSurName.setText("");
                                binding.edtPinCode.setText("");
                                binding.edtAddress1.setText("");
                                binding.edtAddress2.setText("");
                                UtilsMethods.INSTANCE.getFailed(mActivity, message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.something_went_wrong));
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    String json = null;
                    String Message;
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.data != null) {
                        try {
                            JSONObject errorObj = new JSONObject(new String(response.data));
                            if (response.statusCode == 400 || response.statusCode == 405 || response.statusCode == 500) {
                                UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.something_went_wrong));
                            } else if (response.statusCode == 401) {

                            } else if (response.statusCode == 422) {
                                //  json = trimMessage(new String(response.data));
                                if (json != "" && json != null) {
                                    // displayMessage(json);
                                } else {
                                    UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.please_try_again));
                                }
                            } else if (response.statusCode == 503) {
                                UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.server_down));

                            } else {
                                UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.please_try_again));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (error instanceof NoConnectionError) {
                            UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.oops_connect_your_internet));
                        } else if (error instanceof NetworkError) {
                            UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.oops_connect_your_internet));
                        } else if (error instanceof TimeoutError) {
                            try {
                                if (error.networkResponse == null) {
                                    if (error.getClass().equals(TimeoutError.class)) {
                                        UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.timed_out));
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    error.printStackTrace();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

              /*  @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    String mobile = UtilsMethods.INSTANCE.getPreference(mActivity, "mMobile");
                    String mPassword = UtilsMethods.INSTANCE.getPreference(mActivity, "password");
                    String searchMobile = UtilsMethods.INSTANCE.getPreference(mActivity, "searchMobile");
                    map.put("username", mobile);
                    map.put("password", mPassword);
                    map.put("mobile", searchMobile);
                    map.put("fname", mName);
                    map.put("lname", mSurname);
                    map.put("pin_code", mPinCode);
                    map.put("address1", mAddress1);
                    map.put("address2", mAddress2);

                    Log.d("map===", String.valueOf(map));
                    return map;

                }*/
            };
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue queue = Volley.newRequestQueue(mActivity);
            queue.add(jsonObjectRequest);
        } catch (Error e) {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
            e.printStackTrace();
        } catch (Exception e) {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
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
                        binding.edtName.setText("");
                        binding.edtSurName.setText("");
                        binding.edtAddress1.setText("");
                        binding.edtPinCode.setText("");
                        binding.edtAddress1.setText("");
                        binding.edtAddress2.setText("");
                        mName = "";
                        mSurname = "";
                        mPinCode = "";
                        mAddress1 = "";
                        mAddress2 = "";
                        getBottomSheet();
                    }
                })
                .show();
    }

    private void getBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_send_otp, null);
        dialogSendOtp = new BottomSheetDialog(getActivity());
        dialogSendOtp.setContentView(view);
        ImageView ivClose = dialogSendOtp.findViewById(R.id.ivClose);
        TextView tvHeadBottomTittle = dialogSendOtp.findViewById(R.id.tvHeadBottomTittle);
        tvHeadBottomTittle.setText("Submit OTP");
        edtSentOtp = dialogSendOtp.findViewById(R.id.edtSentOtp);
        Button btnSubmit = dialogSendOtp.findViewById(R.id.btnSubmit);
        Button btnClear = dialogSendOtp.findViewById(R.id.btnClear);
        tvErrorMessageOTP = dialogSendOtp.findViewById(R.id.tvErrorMessageOTP);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    sendOtp = edtSentOtp.getText().toString().trim();
                    if (sendOtp != null && !sendOtp.isEmpty()) {
                        getSendOTP();
                    } else {
                        tvErrorMessageOTP.setVisibility(View.VISIBLE);
                        tvErrorMessageOTP.setText(getResources().getString(R.string.enter_otp));
                    }
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                }
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSentOtp.setText("");
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSendOtp.dismiss();
            }
        });
        dialogSendOtp.show();
    }

    private void getSendOTP() {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            String mobile = UtilsMethods.INSTANCE.getPreference(mActivity, "mMobile");
            String password = UtilsMethods.INSTANCE.getPreference(mActivity, "password");
            String searchMobile = UtilsMethods.INSTANCE.getPreference(mActivity, "searchMobile");

            String url = ApplicationConstant.INSTANCE.GET_SEND_OTP + "?username="+mobile+"&password="+password+"&mobile="+searchMobile+"&otp="+sendOtp;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    if (response != null && response.has("status")) {
                        try {
                            int status = response.getInt("status");
                            String message = response.getString("message");
                            if (status == 0 && message.equalsIgnoreCase("success")) {
                                getSuccessfulOTP(mActivity, message);
                            } else {
                                edtSentOtp.setText("");
                                getFailed(mActivity, message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("><><><><", error.getMessage());
                    UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.something_went_wrong));
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    String json = null;
                    String Message;
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.data != null) {
                        try {
                            JSONObject errorObj = new JSONObject(new String(response.data));
                            if (response.statusCode == 400 || response.statusCode == 405 || response.statusCode == 500) {
                                UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.something_went_wrong));
                            } else if (response.statusCode == 401) {

                            } else if (response.statusCode == 422) {
                                //  json = trimMessage(new String(response.data));
                                if (json != "" && json != null) {
                                    // displayMessage(json);
                                } else {
                                    UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.please_try_again));
                                }
                            } else if (response.statusCode == 503) {
                                UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.server_down));

                            } else {
                                UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.please_try_again));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (error instanceof NoConnectionError) {
                            UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.oops_connect_your_internet));
                        } else if (error instanceof NetworkError) {
                            UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.oops_connect_your_internet));
                        } else if (error instanceof TimeoutError) {
                            try {
                                if (error.networkResponse == null) {
                                    if (error.getClass().equals(TimeoutError.class)) {
                                        UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.timed_out));
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    error.printStackTrace();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue queue = Volley.newRequestQueue(mActivity);
            queue.add(jsonObjectRequest);
        } catch (Error e) {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
            e.printStackTrace();
        } catch (Exception e) {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
            e.printStackTrace();
        }
    }

    public void getSuccessfulOTP(final Context context, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(context.getResources().getString(R.string.successful_title))
                .setContentText(message)
                .setCustomImage(R.drawable.ic_check_circle_green)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        edtSentOtp.setText("");
                        sendOtp = "";
                        dialogSendOtp.dismiss();
                        dialogSendOtp.cancel();
                        NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                        navController.navigateUp();
                    }
                })
                .show();
    }

    public void getFailed(final Context context, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(context.getResources().getString(R.string.attention_error_title))
                .setContentText(message)
                .setCustomImage(R.drawable.ic_cancel_black_24dp)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();

                    }
                })
                .show();
    }

}