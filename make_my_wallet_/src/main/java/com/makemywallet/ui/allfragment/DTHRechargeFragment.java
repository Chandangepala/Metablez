package com.makemywallet.ui.allfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makemywallet.MainActivity;
import com.makemywallet.R;
import com.makemywallet.databinding.FragmentDTHRechargeBinding;
import com.makemywallet.ui.adapter.DTHRechargeAdapter;
import com.makemywallet.ui.constant.ApplicationConstant;
import com.makemywallet.ui.constant.ProgressDialog;
import com.makemywallet.ui.constant.UtilsMethods;
import com.makemywallet.ui.Models.PrepaidResponseModel;
import com.makemywallet.ui.customClickListener.OnCustomThreeClickListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class DTHRechargeFragment extends Fragment implements OnCustomThreeClickListener {
    BottomSheetDialog dialogPrepaid, dialogPreview;
    Activity mActivity;
    ArrayList<PrepaidResponseModel> prepaidResponses = new ArrayList<>();
    int service_id = 2;
    private String mMobile = "";
    private String mAmount = "";
    private String mSelectedOperator = "";
    private String mProviderId = "";
    FragmentDTHRechargeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDTHRechargeBinding.inflate(inflater, container, false);
        View root= binding.getRoot();
        mActivity = getActivity();
        getInitUI(root);
        return root;
    }

    private void getInitUI(View view) {

        binding.rLayoutOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    getOperatorAPI();
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title),getResources().getString(R.string.connection_lost));
                }
            }
        });

        binding.btnProceed.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mMobile =  binding.edtDTHNumber.getText().toString().trim();
                mAmount =  binding.edtAmount.getText().toString().trim();
                if (mMobile != null && !mMobile.isEmpty()) {
                    if (mSelectedOperator != null && !mSelectedOperator.isEmpty()) {
                        if (mAmount != null && !mAmount.isEmpty()) {
                            getTimeTrimAPI(mMobile, mAmount, mSelectedOperator);
                        } else {
                            UtilsMethods.INSTANCE.getShowToast(mActivity, mActivity.getResources().getString(R.string.enter_recharge_amount));
                        }
                    } else {
                        UtilsMethods.INSTANCE.getShowToast(mActivity, mActivity.getResources().getString(R.string.please_select_operator));
                    }
                } else {
                    UtilsMethods.INSTANCE.getShowToast(mActivity, mActivity.getResources().getString(R.string.enter_dth_number));
                }
            }
        });
    }

    private void getOperatorAPI() {
        ProgressDialog pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            Log.d(">>>>>>>", ApplicationConstant.INSTANCE.PREPAID_RECHARGE + service_id);
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, ApplicationConstant.INSTANCE.PREPAID_RECHARGE + service_id, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    prepaidResponses.clear();
                    if (response != null && response.length() > 0) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                PrepaidResponseModel model = new PrepaidResponseModel();
                                model.setId(response.getJSONObject(i).getInt("id"));
                                model.setProviderName(response.getJSONObject(i).getString("provider_name"));
                                model.setStatusId(response.getJSONObject(i).getInt("status_id"));
                                model.setLogo(response.getJSONObject(i).getString("logo"));
                                prepaidResponses.add(model);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (prepaidResponses.size() > 0) {
                            getBottomSheet();
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

    private void getBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_custom, null);
        dialogPrepaid = new BottomSheetDialog(getActivity());
        dialogPrepaid.setContentView(view);
        ImageView ivClose = dialogPrepaid.findViewById(R.id.ivClose);
        TextView tvNoRecord = dialogPrepaid.findViewById(R.id.tvNoRecord);
        TextView tvHeadBottomTittle = dialogPrepaid.findViewById(R.id.tvHeadBottomTittle);
        tvHeadBottomTittle.setText("Operators");
        RecyclerView recyclerView = dialogPrepaid.findViewById(R.id.recyclerView);

        if (prepaidResponses.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoRecord.setVisibility(View.GONE);
            DTHRechargeAdapter adapter = new DTHRechargeAdapter(mActivity, DTHRechargeFragment.this, prepaidResponses);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            recyclerView.setVisibility(View.GONE);
            tvNoRecord.setVisibility(View.VISIBLE);
            tvNoRecord.setText(getResources().getString(R.string.noDataFound));
        }

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPrepaid.dismiss();
            }
        });
        dialogPrepaid.show();
    }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.dth_recharge));
    }

    @Override
    public void onCustomThreeClick(String v1, String v2, String v3) {
        mSelectedOperator = v2;
        mProviderId = v1;
        binding.tvOperator.setText(v2);
        Glide.with(this).load(v3)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into( binding.ivOperatorImg);
        dialogPrepaid.dismiss();
    }

    private void getTimeTrimAPI(String mob,String amt,String sOperator) {
        ProgressDialog pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApplicationConstant.INSTANCE.GET_TIME_TRIM, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    if (response != null && response.has("timetrim")) {
                        try {
                            String mTimetrim = response.getString("timetrim");
                            if (mTimetrim!=null && !mTimetrim.isEmpty()){
                                getBottomSheetPreview(mob,amt,sOperator,mTimetrim);
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

    private void getBottomSheetPreview(String v1, String v2, String v3,String v4) {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_preview, null);
        dialogPreview = new BottomSheetDialog(getActivity());
        dialogPreview.setContentView(view);
        ImageView ivClose = dialogPreview.findViewById(R.id.ivClose);
        TextView tvHeadBottomTittle = dialogPreview.findViewById(R.id.tvHeadBottomTittle);
        TextView txtProviderName = dialogPreview.findViewById(R.id.txtProviderName);
        TextView txtNumber = dialogPreview.findViewById(R.id.txtNumber);
        TextView txtAmount = dialogPreview.findViewById(R.id.txtAmount);
        Button btnCancel = dialogPreview.findViewById(R.id.btnCancel);
        txtNumber.setText(v1);
        txtAmount.setText("₹ " + v2);
        txtProviderName.setText(v3);
        tvHeadBottomTittle.setText("Preview");
        Button btnConfirm = dialogPreview.findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    getRechargeAPI(v4);
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, mActivity.getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPreview.dismiss();
            }
        });

        dialogPreview.show();
    }

    private void getRechargeAPI(String v4) {
        ProgressDialog pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            String mobile = UtilsMethods.INSTANCE.getPreference(mActivity, "mMobile");
            String password = UtilsMethods.INSTANCE.getPreference(mActivity, "password");
            Log.d(">>>>>>>", ApplicationConstant.INSTANCE.GET_RECHARGE+"?username="+mobile+"&password="+password+"&mobile="+mMobile+"&provider_id="+mProviderId+"&amount="+mAmount+"&timetrim="+v4);
            String url = ApplicationConstant.INSTANCE.GET_RECHARGE+"?username="+mobile+"&password="+password+"&mobile="+mMobile+"&provider_id="+mProviderId+"&amount="+mAmount+"&timetrim="+v4;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    if (response != null && response.has("status")) {
                        try {
                            String status = response.getString("status");
                            String message = response.getString("message");
                            if (status.equals("success")) {
                                binding.edtDTHNumber.setText("");
                                binding.edtAmount.setText("");
                                mSelectedOperator = "";
                                mProviderId = "";
                                binding.tvOperator.setText(getResources().getString(R.string.please_select_operator));
                                getSuccessful(mActivity, message);
                            } else if (status.equals("failure")) {
                                binding.edtDTHNumber.setText("");
                                binding.edtAmount.setText("");
                                mSelectedOperator = "";
                                mProviderId = "";
                                binding.tvOperator.setText(getResources().getString(R.string.please_select_operator));
                                UtilsMethods.INSTANCE.getFailed(mActivity, message);
                            } else {
                                binding.edtDTHNumber.setText("");
                                binding.edtAmount.setText("");
                                mSelectedOperator = "";
                                mProviderId = "";
                                binding.tvOperator.setText(getResources().getString(R.string.please_select_operator));
                                getSuccessfulPopUp(mActivity, message);
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
                        NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                        navController.navigateUp();
                    }
                })
                .show();
    }

    public void getSuccessfulPopUp(final Context context, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(context.getResources().getString(R.string.successful_title))
                .setContentText(message)
                .setCustomImage(R.drawable.ic_check_circle_yellow)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }

}