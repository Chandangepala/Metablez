package com.makemywallet.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.android.volley.toolbox.Volley;
import com.makemywallet.R;
import com.makemywallet.ui.adapter.BannerAdapter;
import com.makemywallet.ui.Models.BannerModel;
import com.makemywallet.ui.constant.ApplicationConstant;
import com.makemywallet.ui.constant.ProgressDialog;
import com.makemywallet.ui.constant.UtilsMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
   // ActivityDashboardBinding binding;
    BannerAdapter bannerAdapter;
    ArrayList<BannerModel> arrayListBanner = new ArrayList<>();
TextView tvNoRecord;
RecyclerView recyclerViewBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_dashboard);
        getInitUI();
    }

    private void getInitUI() {
        tvNoRecord =findViewById(R.id.tvNoRecord);
        recyclerViewBanner =findViewById(R.id.recyclerViewBanner);
        recyclerViewBanner.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBanner.setLayoutManager(mLayoutManager);
        recyclerViewBanner.setItemAnimator(new DefaultItemAnimator());

        if (UtilsMethods.INSTANCE.isNetworkAvailable(DashboardActivity.this)) {
            getSliderApi();
        } else {
            UtilsMethods.INSTANCE.getNetworkError(DashboardActivity.this, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
        }
    }


    private void getSliderApi() {
        ProgressDialog pd = ProgressDialog.show(this, "Please Wait...");
        Log.d("<<<<<>>>>>>>>>>>", ">????" + ApplicationConstant.INSTANCE.GET_SLIDER_API);
        String url =ApplicationConstant.INSTANCE.GET_SLIDER_API;
        try {
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    arrayListBanner.clear();
                    if (response.toString().equals("[]")) {
                        tvNoRecord.setVisibility(View.VISIBLE);
                    } else {
                        if (response != null && response.length() > 0) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    BannerModel bannerModel = new BannerModel();
                                    bannerModel.setBannerName(response.getJSONObject(i).getString("img"));
                                    arrayListBanner.add(bannerModel);
                                }
                                if (arrayListBanner != null && arrayListBanner.size() > 0) {
                                    recyclerViewBanner.setVisibility(View.VISIBLE);
                                    tvNoRecord.setVisibility(View.GONE);
                                    bannerAdapter = new BannerAdapter(DashboardActivity.this, arrayListBanner);
                                    recyclerViewBanner.setAdapter(bannerAdapter);
                                } else {
                                    recyclerViewBanner.setVisibility(View.GONE);
                                    tvNoRecord.setVisibility(View.VISIBLE);
                                    tvNoRecord.setText(getResources().getString(R.string.noDataFound));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    UtilsMethods.INSTANCE.getShowToast(DashboardActivity.this, getResources().getString(R.string.something_went_wrong));
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
                                UtilsMethods.INSTANCE.getShowToast(DashboardActivity.this, getResources().getString(R.string.something_went_wrong));
                            } else if (response.statusCode == 401) {

                            } else if (response.statusCode == 422) {
                                //  json = trimMessage(new String(response.data));
                                if (json != "" && json != null) {
                                    // displayMessage(json);
                                } else {
                                    UtilsMethods.INSTANCE.getShowToast(DashboardActivity.this, getResources().getString(R.string.please_try_again));
                                }
                            } else if (response.statusCode == 503) {
                                UtilsMethods.INSTANCE.getShowToast(DashboardActivity.this, getResources().getString(R.string.server_down));

                            } else {
                                UtilsMethods.INSTANCE.getShowToast(DashboardActivity.this, getResources().getString(R.string.please_try_again));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (error instanceof NoConnectionError) {
                            UtilsMethods.INSTANCE.getShowToast(DashboardActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                        } else if (error instanceof NetworkError) {
                            UtilsMethods.INSTANCE.getShowToast(DashboardActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                        } else if (error instanceof TimeoutError) {
                            try {
                                if (error.networkResponse == null) {
                                    if (error.getClass().equals(TimeoutError.class)) {
                                        UtilsMethods.INSTANCE.getShowToast(DashboardActivity.this, getResources().getString(R.string.timed_out));
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
            RequestQueue queue = Volley.newRequestQueue(DashboardActivity.this);
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