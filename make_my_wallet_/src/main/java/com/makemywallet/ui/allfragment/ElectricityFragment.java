package com.makemywallet.ui.allfragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makemywallet.MainActivity;
import com.makemywallet.R;
import com.makemywallet.databinding.FragmentElectricityBinding;
import com.makemywallet.ui.Models.BillerByStateModel;
import com.makemywallet.ui.Models.RegionModel;
import com.makemywallet.ui.adapter.BillerAdapter;
import com.makemywallet.ui.adapter.RegionAdapter;
import com.makemywallet.ui.constant.ApplicationConstant;
import com.makemywallet.ui.constant.ProgressDialog;
import com.makemywallet.ui.constant.UtilsMethods;
import com.makemywallet.ui.customClickListener.OnCustomSingleClickListener;
import com.makemywallet.ui.customClickListener.OnCustomThreeClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ElectricityFragment extends Fragment implements OnCustomSingleClickListener, OnCustomThreeClickListener {
    private static final String TAG = "ElectricityFragment";
    FragmentElectricityBinding binding;
    BottomSheetDialog dialogState, dialogBiller;
    Activity mActivity;
    ArrayList<RegionModel> regionArrayList = new ArrayList<>();
    ArrayList<BillerByStateModel> billerByStateArrayList = new ArrayList<>();
    int service_id = 5;
    String selectedState = "";
    String selectedBillerByState = "";
    int paracount;
    String fetchOption = "";
    String paramName1 ="";
    String paramName2 ="";
    String paramName3="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentElectricityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mActivity = getActivity();
        getInitUI();
        return root;
    }

    private void getInitUI() {
        binding.rLayoutState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    getRegionAPI();
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                }
            }
        });

        binding.rLayoutBillerName.setOnClickListener(v -> {
            if (selectedState != null && !selectedState.isEmpty()) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    getGetBillerByStateAPI(selectedState);
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                }
            } else {
                UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.select_region));
            }
        });

        binding.btnGetBill.setOnClickListener(v -> {
            if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                if (fetchOption != null && !fetchOption.isEmpty()) {
                    if (fetchOption.equals("MANDATORY")) {
                        getFetchBillAPI(paramName1,binding.edt1.getText().toString().trim());   //  billfatech
                    } else if (fetchOption.equals("NOT_SUPPORTED")) {
                        getBillPayAPI(paramName1,binding.edt1.getText().toString().trim(),paramName2,binding.edt2.getText().toString().trim(),paramName3,binding.edt3.getText().toString().trim()); // quiyPay
                    } else {
                        getBillPayAPI(paramName1,binding.edt1.getText().toString().trim(),paramName2,binding.edt2.getText().toString().trim(),paramName3,binding.edt3.getText().toString().trim()); // quiyPay
                    }
                }
                // getBillPayAPI();
            } else {
                UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
            }
        });
    }

    private void getFetchBillAPI(String paraname,String paravalue) {
        ProgressDialog pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            String mobile = UtilsMethods.INSTANCE.getPreference(mActivity, "mMobile");
            String mPassword = UtilsMethods.INSTANCE.getPreference(mActivity, "password");

            Log.d(">>>>>>>", ApplicationConstant.INSTANCE.GET_BILL_FETCH + "?username=" + mobile + "&password=" + mPassword + "&billerId=" + selectedBillerByState + "&pcount=" + paracount + "&paraname=" +paraname + "&paravalue=" +paravalue);

            String URL = ApplicationConstant.INSTANCE.GET_BILL_FETCH + "?username=" + mobile + "&password=" + mPassword + "&billerId=" + selectedBillerByState + "&pcount=" + paracount + "&paraname=" + paraname + "&paravalue=" + paravalue;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    try {
                        UtilsMethods.INSTANCE.getShowToast(mActivity, response.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
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

  /*  private void getElectricityAPI() {
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
    } */

    private void getRegionAPI() {
        ProgressDialog pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            Log.d(">>>>>>>", ApplicationConstant.INSTANCE.GET_REGION_API);
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, ApplicationConstant.INSTANCE.GET_REGION_API, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    regionArrayList.clear();
                    if (response != null && response.length() > 0) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                regionArrayList.add(new RegionModel(response.getJSONObject(i).getString("region")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (regionArrayList.size() > 0) {
                            getBottomSheetState();
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

    private void getBottomSheetState() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_custom, null);
        dialogState = new BottomSheetDialog(getActivity());
        dialogState.setContentView(view);
        ImageView ivClose = dialogState.findViewById(R.id.ivClose);
        TextView tvNoRecord = dialogState.findViewById(R.id.tvNoRecord);
        TextView tvHeadBottomTittle = dialogState.findViewById(R.id.tvHeadBottomTittle);
        tvHeadBottomTittle.setText("Select State");
        RecyclerView recyclerView = dialogState.findViewById(R.id.recyclerView);

        if (regionArrayList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoRecord.setVisibility(View.GONE);
            RegionAdapter adapter = new RegionAdapter(mActivity, ElectricityFragment.this, regionArrayList);
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
                dialogState.dismiss();
            }
        });
        dialogState.show();
    }

    private void getBottomSheetBiller() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_custom, null);
        dialogBiller = new BottomSheetDialog(getActivity());
        dialogBiller.setContentView(view);
        ImageView ivClose = dialogBiller.findViewById(R.id.ivClose);
        TextView tvNoRecord = dialogBiller.findViewById(R.id.tvNoRecord);
        TextView tvHeadBottomTittle = dialogBiller.findViewById(R.id.tvHeadBottomTittle);
        tvHeadBottomTittle.setText("Biller");
        RecyclerView recyclerView = dialogBiller.findViewById(R.id.recyclerView);

        if (billerByStateArrayList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoRecord.setVisibility(View.GONE);
            BillerAdapter adapter = new BillerAdapter(mActivity, ElectricityFragment.this, billerByStateArrayList);
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
                dialogBiller.dismiss();
            }
        });
        dialogBiller.show();
    }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.electricity));
    }

    @Override
    public void onCustomSingleClick(String v1) {
        binding.txtState.setText(v1);
        selectedState = v1;
        if (selectedState != null && !selectedState.isEmpty()) {
            binding.rLayBillerName.setVisibility(View.VISIBLE);
            Log.d("><><State><>", v1);
        }
        dialogState.dismiss();
    }

    @Override
    public void onCustomThreeClick(String v1, String v2, String v3) {
        binding.tvBillerName.setText(v1);
        selectedBillerByState = v2;
        Log.d("><><StateByState><>", v2);
        if (selectedBillerByState != null && !selectedBillerByState.isEmpty()) {
            if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                getGetBillerInfoAPI(selectedBillerByState);
            } else {
                UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
            }
        }
        dialogBiller.dismiss();
    }

    private void getGetBillerByStateAPI(String mSelectedState) {
        ProgressDialog pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            Log.d(">>>>>>>", ApplicationConstant.INSTANCE.GET_BILLER_BY_STATE + mSelectedState);
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, ApplicationConstant.INSTANCE.GET_BILLER_BY_STATE + mSelectedState, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    billerByStateArrayList.clear();
                    if (response != null && response.length() > 0) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                billerByStateArrayList.add(new BillerByStateModel(response.getJSONObject(i).getString("billerId"), response.getJSONObject(i).getString("billerName")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (billerByStateArrayList.size() > 0) {
                            getBottomSheetBiller();
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

    private void getGetBillerInfoAPI(String billerId) {
        ProgressDialog pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            Log.d(">>>>>>>", ApplicationConstant.INSTANCE.GET_BILLER_INFO + billerId);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApplicationConstant.INSTANCE.GET_BILLER_INFO + billerId, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    if (response != null && response.has("para")) {
                        try {
                            int status = response.getInt("status");
                            String isAdhoc = response.getString("isAdhoc");
                            fetchOption = response.getString("fetchOption");
                            paracount = response.getInt("paracount");
                            Log.d(">>>>>>>>>>>", "Count----" + String.valueOf(paracount));
                            JSONArray jsonArray = response.getJSONArray("para");
                            if (jsonArray != null && jsonArray.length() > 0) {
                                if (paracount == 1) {
                                    binding.rLayout1.setVisibility(View.VISIBLE);
                                    binding.rLayout2.setVisibility(View.GONE);
                                    binding.rLayout3.setVisibility(View.GONE);
                                    binding.tvName1.setText(jsonArray.getJSONObject(0).getString("paramName"));
                                     paramName1 =jsonArray.getJSONObject(0).getString("paramName");
                                    binding.edt1.setHint(jsonArray.getJSONObject(0).getString("paramName"));
                                    // binding.edt1.setInputType(InputType.TYPE_CLASS_PHONE);
                                } else if (paracount == 2) {
                                    binding.rLayout1.setVisibility(View.VISIBLE);
                                    binding.rLayout2.setVisibility(View.VISIBLE);
                                    binding.rLayout3.setVisibility(View.GONE);
                                    binding.tvName1.setText(jsonArray.getJSONObject(0).getString("paramName"));
                                    binding.edt1.setHint(jsonArray.getJSONObject(0).getString("paramName"));
                                    binding.tvName2.setText(jsonArray.getJSONObject(1).getString("paramName"));
                                    binding.edt2.setHint(jsonArray.getJSONObject(1).getString("paramName"));
                                     paramName1 =jsonArray.getJSONObject(0).getString("paramName");
                                     paramName2 =jsonArray.getJSONObject(1).getString("paramName");
                                } else {
                                    binding.rLayout1.setVisibility(View.VISIBLE);
                                    binding.rLayout2.setVisibility(View.VISIBLE);
                                    binding.rLayout3.setVisibility(View.VISIBLE);
                                    binding.tvName1.setText(jsonArray.getJSONObject(0).getString("paramName"));
                                    binding.edt1.setHint(jsonArray.getJSONObject(0).getString("paramName"));
                                    binding.tvName2.setText(jsonArray.getJSONObject(1).getString("paramName"));
                                    binding.edt2.setHint(jsonArray.getJSONObject(1).getString("paramName"));
                                    binding.tvName3.setText(jsonArray.getJSONObject(3).getString("paramName"));
                                    binding.edt3.setHint(jsonArray.getJSONObject(3).getString("paramName"));

                                    paramName1 =jsonArray.getJSONObject(0).getString("paramName");
                                    paramName2 =jsonArray.getJSONObject(1).getString("paramName");
                                    paramName3 =jsonArray.getJSONObject(3).getString("paramName");
                                }
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

    private void getBillPayAPI(String parmName1,String parmValue1,String parmName2,String parmValue2,String parmName3,String parmValue3) {
        ProgressDialog pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            Log.d(">>>>>>>", ApplicationConstant.INSTANCE.GET_BILL_PAY);
            JSONObject jsonBody = null;
            try {
                jsonBody = new JSONObject();
                jsonBody.put("username", UtilsMethods.INSTANCE.getPreference(mActivity, "mMobile"));
                jsonBody.put("password", UtilsMethods.INSTANCE.getPreference(mActivity, "password"));
                jsonBody.put("pcount", paracount);
                jsonBody.put("paraname", parmName1);
                jsonBody.put("paravalue", parmValue1);
                jsonBody.put("paraname1", parmName2);
                jsonBody.put("paravalue1", parmValue2);
                jsonBody.put("paraname2", parmName3);
                jsonBody.put("paravalue2", parmValue3);
                jsonBody.put("billerId", selectedBillerByState);
                jsonBody.put("amount", "");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApplicationConstant.INSTANCE.GET_BILL_PAY, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    if (response != null) {
                        //
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
}