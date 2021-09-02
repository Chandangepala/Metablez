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
import com.makemywallet.R;
import com.makemywallet.databinding.FragmentMemberDetailsBinding;
import com.makemywallet.ui.constant.ApplicationConstant;
import com.makemywallet.ui.constant.ProgressDialog;
import com.makemywallet.ui.constant.UtilsMethods;
import org.json.JSONException;
import org.json.JSONObject;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MemberDetailsFragment extends Fragment {
    FragmentMemberDetailsBinding binding;
    Activity mActivity;
    String mState = "";
    String mEmail = "";
    String mMobile = "";
    String mAmount = "";
    String mRemark = "";
    ProgressDialog pd;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMemberDetailsBinding.inflate(inflater, container, false);
        mActivity = getActivity();
        getInitUI();
        return binding.getRoot();
    }

    private void getInitUI() {
        String topMobile = UtilsMethods.INSTANCE.getPreference(mActivity, "TopUpMobile");
        String mTopState = UtilsMethods.INSTANCE.getPreference(mActivity, "TopUpName");
        String TopEmail = UtilsMethods.INSTANCE.getPreference(mActivity, "TopUpEmail");

        if (!mTopState.isEmpty() && mTopState != null) {
            binding.edtState.setText(mTopState);
        }
        if (!topMobile.isEmpty() && topMobile != null) {
            binding.edtMobile.setText(topMobile);
        }
        if (!TopEmail.isEmpty() && TopEmail != null) {
            binding.edtEmail.setText(TopEmail);
        }
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    mState = binding.edtState.getText().toString().trim();
                    mEmail = binding.edtEmail.getText().toString().trim();
                    mMobile = binding.edtMobile.getText().toString().trim();
                    mAmount = binding.edtAmount.getText().toString().trim();
                    mRemark = binding.edtRemark.getText().toString().trim();
                    if (mState != null && !mState.isEmpty()) {
                        if (mEmail != null && !mEmail.isEmpty()) {
                            if (mMobile != null && !mMobile.isEmpty()) {
                                if (mAmount != null && !mAmount.isEmpty()) {
                                    if (mRemark != null && !mRemark.isEmpty()) {
                                        getSendApi();
                                    } else {
                                        binding.tvErrorMessage.setVisibility(View.VISIBLE);
                                        binding.tvErrorMessage.setText(getResources().getString(R.string.remark));
                                    }
                                } else {
                                    binding.tvErrorMessage.setVisibility(View.VISIBLE);
                                    binding.tvErrorMessage.setText(getResources().getString(R.string.enter_amount));
                                }
                            } else {
                                binding.tvErrorMessage.setVisibility(View.VISIBLE);
                                binding.tvErrorMessage.setText(getResources().getString(R.string.enter_mobile_number));
                            }
                        } else {
                            binding.tvErrorMessage.setVisibility(View.VISIBLE);
                            binding.tvErrorMessage.setText(getResources().getString(R.string.enter_email));
                        }
                    } else {
                        binding.tvErrorMessage.setVisibility(View.VISIBLE);
                        binding.tvErrorMessage.setText(getResources().getString(R.string.enter_state));
                    }
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                }
            }
        });
    }

    private void getSendApi() {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            String mobile = UtilsMethods.INSTANCE.getPreference(mActivity, "mMobile");
            String mPassword = UtilsMethods.INSTANCE.getPreference(mActivity, "password");
            String mTopUpMobile = UtilsMethods.INSTANCE.getPreference(mActivity, "TopUpMobile");

            String url = ApplicationConstant.INSTANCE.GET_TOP_UP_TRANSACTION + "?username=" + mobile + "&password=" + mPassword + "&mobile=" + mTopUpMobile + "&amount=" + mAmount + "&remark=" + mRemark;
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
                            if (status == 0) {
                                getSuccessful(mActivity, message);
                            } else {
                              /*  binding.edtState.setText("");
                                binding.edtEmail.setText("");
                                binding.edtMobile.setText("");
                                binding.edtAmount.setText("");
                                binding.edtRemark.setText("");*/
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
                        binding.edtState.setText("");
                        binding.edtEmail.setText("");
                        binding.edtMobile.setText("");
                        binding.edtAmount.setText("");
                        binding.edtRemark.setText("");
                        mState = "";
                        mEmail = "";
                        mMobile = "";
                        mAmount = "";
                        mRemark = "";
                        NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                        navController.navigateUp();
                    }
                })
                .show();
    }

}