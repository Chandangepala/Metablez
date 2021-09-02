package com.makemywallet.ui.allfragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makemywallet.MainActivity;
import com.makemywallet.R;
import com.makemywallet.ui.Models.PostpaidModel;
import com.makemywallet.ui.Models.PrepaidPlanModel;
import com.makemywallet.ui.adapter.PostpaidRechargeAdapter;
import com.makemywallet.ui.adapter.PrepaidPlanAdapter;
import com.makemywallet.ui.constant.ApplicationConstant;
import com.makemywallet.ui.constant.ProgressDialog;
import com.makemywallet.ui.constant.UtilsMethods;
import com.makemywallet.ui.customClickListener.OnCustomSingleClickListener;
import com.makemywallet.ui.customClickListener.OnCustomThreeClickListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class PostpaidFragment extends Fragment implements OnCustomThreeClickListener, OnCustomSingleClickListener {
    private static final String TAG = "PostpaidFragment";
    BottomSheetDialog dialogPrepaid, dialogPrepaidPlans, dialogPreview;
    Activity mActivity;
    // ArrayList<PrepaidResponseModel> prepaidResponses = new ArrayList<>();
    ArrayList<PostpaidModel> prepaidResponses = new ArrayList<>();
    ArrayList<PrepaidPlanModel> arrayListPrepaidPlans = new ArrayList<>();
    RadioGroup radioGroup;
    RadioButton loginCategory;
    // String mType = "";
    TextView tvOperator, tvBrowsePlan;
    // int service_id = 1;
    Button btnPreview;
    EditText edtMobile, edtRechargeAmount;
    private String mMobile = "";
    private String mAmount = "";
    private String mSelectedOperator = "";
    private String mProviderId = "";
    ImageView ivDownArrow, ivContactAccount, ivOperatorImg;
    RelativeLayout rLayoutOperator;
    private final int REQUEST_CODE = 99;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_postpaid, container, false);
        mActivity = getActivity();
        getInitUI(root);
        return root;
    }

    private void getInitUI(View view) {
        tvOperator = view.findViewById(R.id.tvOperator);
        edtMobile = view.findViewById(R.id.edtMobile);
        edtRechargeAmount = view.findViewById(R.id.edtRechargeAmount);
        btnPreview = view.findViewById(R.id.btnPreview);
        ivDownArrow = view.findViewById(R.id.ivDownArrow);
        ivContactAccount = view.findViewById(R.id.ivContactAccount);
        rLayoutOperator = view.findViewById(R.id.rLayoutOperator);
       // tvBrowsePlan = view.findViewById(R.id.tvBrowsePlan);
        ivOperatorImg = view.findViewById(R.id.ivOperatorImg);

        // if (mType.equals("Prepaid")) {
        //((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.prepaid_recharge));

        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.postpaid_recharge));


       /*  tvBrowsePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    getPrepaidPlans();
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                }
            }
        });*/

        /*} else {
            ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.postpaid_recharge));
        }*/

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

        ivContactAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        rLayoutOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    // service_id = 4;
                    /*if (mType.equals("Prepaid")) {
                        service_id = 1;
                    } else {

                    }*/
                    getPrepaidAPI();
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                }
            }
        });

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMobile = edtMobile.getText().toString().trim();
                mAmount = edtRechargeAmount.getText().toString().trim();
                if (mMobile != null && !mMobile.isEmpty()) {
                    if (mSelectedOperator != null && !mSelectedOperator.isEmpty()) {
                        if (mAmount != null && !mAmount.isEmpty()) {
                            getBottomSheetPreview(mMobile, mAmount, mSelectedOperator);
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
            // Log.d(">>>>>>>", ApplicationConstant.INSTANCE.GET_PREPAID_PLANS + service_id); // ?mobile_circle=34&mobile_provider=1&page=1&limit=50
            // JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApplicationConstant.INSTANCE.GET_PREPAID_PLANS + service_id, null, new Response.Listener<JSONObject>() {
            // JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://makemywallet.co.in/app/prepaid-plan?mobile_circle=34&mobile_provider=1&page=1&limit=50", null, new Response.Listener<JSONObject>() {
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
            // Log.d(">>>>>>>", ApplicationConstant.INSTANCE.PREPAID_RECHARGE + service_id);
            Log.d(">>>>>>>", ApplicationConstant.INSTANCE.GET_BILL_POSTPAID);
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, ApplicationConstant.INSTANCE.GET_BILL_POSTPAID, null, new Response.Listener<JSONArray>() {
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
                                PostpaidModel model = new PostpaidModel();
                                model.setBillerId(response.getJSONObject(i).getString("billerId"));
                                model.setBillerName(response.getJSONObject(i).getString("billerName"));
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
            PostpaidRechargeAdapter adapter = new PostpaidRechargeAdapter(mActivity, PostpaidFragment.this, prepaidResponses);
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
            PrepaidPlanAdapter adapter = new PrepaidPlanAdapter(mActivity, PostpaidFragment.this, arrayListPrepaidPlans);
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

    private void getBottomSheetPreview(String v1, String v2, String v3) {
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
                    getRechargeAPI();
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
        tvOperator.setText(v2);
         /*  Glide.with(this).load(v3)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivOperatorImg);*/
        dialogPrepaid.dismiss();
    }

    private void getRechargeAPI() {
        ProgressDialog pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            String mobile = UtilsMethods.INSTANCE.getPreference(mActivity, "mMobile");
            String password = UtilsMethods.INSTANCE.getPreference(mActivity, "password");
            Log.d(">>>>>>>", ApplicationConstant.INSTANCE.GET_RECHARGE + "?username=" + mobile + "&password=" + password + "&mobile=" + mMobile + "&provider_id=" + mProviderId + "&amount=" + mAmount);
            String url = ApplicationConstant.INSTANCE.GET_RECHARGE + "?username=" + mobile + "&password=" + password + "&mobile=" + mMobile + "&provider_id=" + mProviderId + "&amount=" + mAmount;
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
                                edtMobile.setText("");
                                edtRechargeAmount.setText("");
                                mSelectedOperator = "";
                                mProviderId = "";
                                tvOperator.setText(getResources().getString(R.string.please_select_operator));
                                dialogPreview.dismiss();
                                getSuccessful(mActivity, message);
                            } else if (status.equals("failure")) {
                                edtMobile.setText("");
                                edtRechargeAmount.setText("");
                                mSelectedOperator = "";
                                mProviderId = "";
                                tvOperator.setText(getResources().getString(R.string.please_select_operator));
                                dialogPreview.dismiss();
                                UtilsMethods.INSTANCE.getFailed(mActivity, message);
                            } else {
                                edtMobile.setText("");
                                edtRechargeAmount.setText("");
                                mSelectedOperator = "";
                                mProviderId = "";
                                tvOperator.setText(getResources().getString(R.string.please_select_operator));
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
                                edtMobile.setText(mobileN);
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
        edtRechargeAmount.setText(v1);
        dialogPrepaidPlans.dismiss();
    }

}