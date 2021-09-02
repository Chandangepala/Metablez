package com.makemywallet.ui.allfragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.makemywallet.databinding.FragmentPrepaidRechargeBinding;
import com.makemywallet.ui.Models.CircleModel;
import com.makemywallet.ui.Models.PrepaidPlanModel;
import com.makemywallet.ui.adapter.CircleAdapter;
import com.makemywallet.ui.adapter.PrepaidPlanAdapter;
import com.makemywallet.ui.adapter.PrepaidRechargeAdapter;
import com.makemywallet.ui.constant.ApplicationConstant;
import com.makemywallet.ui.constant.ProgressDialog;
import com.makemywallet.ui.constant.UtilsMethods;
import com.makemywallet.ui.Models.PrepaidResponseModel;
import com.makemywallet.ui.customClickListener.OnCustomClickListener;
import com.makemywallet.ui.customClickListener.OnCustomSingleClickListener;
import com.makemywallet.ui.customClickListener.OnCustomThreeClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PrepaidRechargeFragment extends Fragment implements OnCustomThreeClickListener, OnCustomSingleClickListener, OnCustomClickListener {
    private static final String TAG = "PrepaidRechargeFragment";
    ArrayList<PrepaidResponseModel> prepaidResponses = new ArrayList<>();
    ArrayList<PrepaidPlanModel> arrayListPrepaidPlans = new ArrayList<>();
    ArrayList<CircleModel> circleArrayList = new ArrayList<>();

    BottomSheetDialog dialogPrepaid, dialogPrepaidPlans, dialogPreview,dialogCircle;
    Activity mActivity;
    int service_id = 1;
    private String mMobile = "";
    private String mAmount = "";
    private String mSelectedOperator = "";
    private String mProviderId = "";
    private final int REQUEST_CODE = 99;
    FragmentPrepaidRechargeBinding binding;
    Button btnConfirm;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPrepaidRechargeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mActivity = getActivity();
        getInitUI(root);
        return root;
    }

    private void getInitUI(View view) {
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.prepaid_recharge));
        binding.tvBrowsePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    getPrepaidPlans();
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                }
            }
        });

        /* radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            // no radio buttons are checked
            //Toast.makeText(LoginActivity.this, "Please select you user category", Toast.LENGTH_SHORT).show();
        } else {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            loginCategory = (RadioButton) view.findViewById(selectedId);
            Log.d(TAG, "init: " + loginCategory.getText());
            mType = String.valueOf(loginCategory.getText());
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedUserCate = radioGroup.getCheckedRadioButtonId();
                loginCategory = (RadioButton) view.findViewById(selectedUserCate);
                mType = String.valueOf(loginCategory.getText());
                edtMobile.setText("");
                edtRechargeAmount.setText("");
                mSelectedOperator = "";
                mProviderId = "";
                tvOperator.setText(getResources().getString(R.string.please_select_operator));
                Log.d(TAG, "onCheckedChanged: " + selectedUserCate + "><>" + loginCategory.getText());

            }
        });
        */

        binding.ivContactAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        binding.rLayoutOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    service_id = 1;
                    getPrepaidAPI();
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                }
            }
        });

        binding.rLayoutCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    getCircleAPI();
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                }
            }
        });

        binding.btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMobile = binding.edtMobile.getText().toString().trim();
                mAmount = binding.edtRechargeAmount.getText().toString().trim();
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
                    UtilsMethods.INSTANCE.getShowToast(mActivity, mActivity.getResources().getString(R.string.enter_mobile_number));
                }
            }
        });

    }

    private void getPrepaidPlans() {
        ProgressDialog pdPrepaid = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            Log.d(">>>>>>>", ApplicationConstant.INSTANCE.GET_PREPAID_PLANS + service_id); // ?mobile_circle=34&mobile_provider=1&page=1&limit=50
            // JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApplicationConstant.INSTANCE.GET_PREPAID_PLANS + service_id, null, new Response.Listener<JSONObject>() {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://makemywallet.co.in/app/prepaid-plan?mobile_circle=34&mobile_provider=1&page=1&limit=50", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (pdPrepaid != null && pdPrepaid.isShowing()) {
                        pdPrepaid.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    arrayListPrepaidPlans.clear();
                    try {
                        if (response != null && response.has("data")) {
                            JSONArray jsonArray = response.getJSONArray("data");
                            if (jsonArray != null && jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    PrepaidPlanModel model = new PrepaidPlanModel(jsonArray.getJSONObject(i).getString("id"),
                                            jsonArray.getJSONObject(i).getString("operator_id"),
                                            jsonArray.getJSONObject(i).getString("circle_id"),
                                            jsonArray.getJSONObject(i).getString("recharge_amount"),
                                            // jsonArray.getJSONObject(i).getString("recharge_talktime"),
                                            jsonArray.getJSONObject(i).getString("recharge_validity"),
                                            jsonArray.getJSONObject(i).getString("recharge_short_desc"),
                                            jsonArray.getJSONObject(i).getString("recharge_long_desc"),
                                            jsonArray.getJSONObject(i).getString("recharge_type"),
                                            jsonArray.getJSONObject(i).getString("updated_at"));
                                    arrayListPrepaidPlans.add(model);
                                }
                                if (arrayListPrepaidPlans.size() > 0) {
                                    Log.d(">>Size>>", String.valueOf(arrayListPrepaidPlans.size()));
                                    getBottomSheetPrepaidPlans();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.something_went_wrong));
                    if (pdPrepaid != null && pdPrepaid.isShowing()) {
                        pdPrepaid.dismiss();
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
            if (pdPrepaid != null && pdPrepaid.isShowing()) {
                pdPrepaid.dismiss();
            }
            e.printStackTrace();
        } catch (Exception e) {
            if (pdPrepaid != null && pdPrepaid.isShowing()) {
                pdPrepaid.dismiss();
            }
            e.printStackTrace();
        }
    }

    private void getPrepaidAPI() {
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
                                model.setLogo(response.getJSONObject(i).getString("logo"));
                                model.setStatusId(response.getJSONObject(i).getInt("status_id"));
                                if (response.getJSONObject(i).getString("provider_name").equalsIgnoreCase("Jio Prepaid")){
                                    prepaidResponses.add(0,model);
                                }else {
                                    prepaidResponses.add(model);
                                }

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

    private void getCircleAPI() {
        ProgressDialog pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, ApplicationConstant.INSTANCE.GET_CIRCLE, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    circleArrayList.clear();
                    if (response != null && response.length() > 0) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                circleArrayList.add(new CircleModel(String.valueOf(response.getJSONObject(i).getInt("id")),response.getJSONObject(i).getString("state")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (circleArrayList.size() > 0) {
                           getBottomCircleSheet();
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

    private void getBottomCircleSheet() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_custom, null);
        dialogCircle = new BottomSheetDialog(getActivity());
        dialogCircle.setContentView(view);
        ImageView ivClose = dialogCircle.findViewById(R.id.ivClose);
        TextView tvNoRecord = dialogCircle.findViewById(R.id.tvNoRecord);
        TextView tvHeadBottomTittle = dialogCircle.findViewById(R.id.tvHeadBottomTittle);
        tvHeadBottomTittle.setText("Circle");
        RecyclerView recyclerView = dialogCircle.findViewById(R.id.recyclerView);

       if (circleArrayList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoRecord.setVisibility(View.GONE);
            CircleAdapter adapter = new CircleAdapter(mActivity, PrepaidRechargeFragment.this, circleArrayList);
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
                dialogCircle.dismiss();
            }
        });

        dialogCircle.show();
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
            PrepaidRechargeAdapter adapter = new PrepaidRechargeAdapter(mActivity, PrepaidRechargeFragment.this, prepaidResponses);
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

    private void getBottomSheetPrepaidPlans() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_custom, null);
        dialogPrepaidPlans = new BottomSheetDialog(getActivity());
        dialogPrepaidPlans.setContentView(view);
        ImageView ivClose = dialogPrepaidPlans.findViewById(R.id.ivClose);
        TextView tvNoRecord = dialogPrepaidPlans.findViewById(R.id.tvNoRecord);
        TextView tvHeadBottomTittle = dialogPrepaidPlans.findViewById(R.id.tvHeadBottomTittle);
        tvHeadBottomTittle.setText("Operators");
        RecyclerView recyclerView = dialogPrepaidPlans.findViewById(R.id.recyclerView);

        if (arrayListPrepaidPlans.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoRecord.setVisibility(View.GONE);
            PrepaidPlanAdapter adapter = new PrepaidPlanAdapter(mActivity, PrepaidRechargeFragment.this, arrayListPrepaidPlans);
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
                dialogPrepaidPlans.dismiss();
            }
        });
        dialogPrepaidPlans.show();
    }

    private void getBottomSheetPreview(String v1, String v2, String v3, String v4) {
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
        btnConfirm = dialogPreview.findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    btnConfirm.setEnabled(false);
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

    public void onResume() {
        super.onResume();
        // ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.prepaid_recharge));
    }

    @Override
    public void onCustomThreeClick(String v1, String v2, String v3) {
        Log.d("><><>", v1 + ">>>>>>>>>" + v2);
        mSelectedOperator = v2;
        mProviderId = v1;
        binding.tvOperator.setText(v2);
        Glide.with(this).load(v3)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivOperatorImg);
        dialogPrepaid.dismiss();
    }

    private void getTimeTrimAPI(String mob, String amt, String sOperator) {
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
                            if (mTimetrim != null && !mTimetrim.isEmpty()) {
                                getBottomSheetPreview(mob, amt, sOperator, mTimetrim);
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

    private void getRechargeAPI(String v4) {
        btnConfirm.setEnabled(true);
        ProgressDialog pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            String mobile = UtilsMethods.INSTANCE.getPreference(mActivity, "mMobile");
            String password = UtilsMethods.INSTANCE.getPreference(mActivity, "password");
            Log.d(">>>>>>>", ApplicationConstant.INSTANCE.GET_RECHARGE + "?username=" + mobile + "&password=" + password + "&mobile=" + mMobile + "&provider_id=" + mProviderId + "&amount=" + mAmount + "&timetrim=" + v4);
            String url = ApplicationConstant.INSTANCE.GET_RECHARGE + "?username=" + mobile + "&password=" + password + "&mobile=" + mMobile + "&provider_id=" + mProviderId + "&amount=" + mAmount + "&timetrim=" + v4;
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
                                binding.edtMobile.setText("");
                                binding.edtRechargeAmount.setText("");
                                mSelectedOperator = "";
                                mProviderId = "";
                                binding.tvOperator.setText(getResources().getString(R.string.please_select_operator));
                                btnConfirm.setEnabled(true);
                                dialogPreview.dismiss();
                                getSuccessful(mActivity, message);
                            } else if (status.equals("failure")) {
                                binding.edtMobile.setText("");
                                binding.edtRechargeAmount.setText("");
                                mSelectedOperator = "";
                                mProviderId = "";
                                binding.tvOperator.setText(getResources().getString(R.string.please_select_operator));
                                btnConfirm.setEnabled(true);
                                dialogPreview.dismiss();
                                UtilsMethods.INSTANCE.getFailed(mActivity, message);
                            } else {
                                binding.edtMobile.setText("");
                                binding.edtRechargeAmount.setText("");
                                mSelectedOperator = "";
                                mProviderId = "";
                                binding.tvOperator.setText(getResources().getString(R.string.please_select_operator));
                                btnConfirm.setEnabled(true);
                                dialogPreview.dismiss();
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
                        dialogPreview.dismiss();
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
                        dialogPreview.dismiss();
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (REQUEST_CODE):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getActivity().getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String num = "";
                        String mobileN = "";
                        if (Integer.valueOf(hasNumber) == 1) {
                            Cursor numbers = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                            while (numbers.moveToNext()) {
                                num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                boolean isExitsNumber = num.startsWith("+91");
                                if (isExitsNumber) {
                                    mobileN = num.substring(3);
                                } else {
                                    mobileN = num;
                                }
                                binding.edtMobile.setText(mobileN);
                                //  Toast.makeText(getActivity(), "Number=" + num, Toast.LENGTH_LONG).show();
                                Log.d(">>>>>>>", num);
                            }
                        }
                    }
                    break;
                }
        }
    }

    @Override
    public void onCustomSingleClick(String v1) {
        binding.edtRechargeAmount.setText(v1);
        dialogPrepaidPlans.dismiss();
    }

    @Override
    public void onCustomClick(String v1, String v2) {
        binding.tvCircle.setText(v2);
        dialogCircle.dismiss();
    }
}