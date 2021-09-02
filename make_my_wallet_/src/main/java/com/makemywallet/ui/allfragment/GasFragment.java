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
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makemywallet.MainActivity;
import com.makemywallet.R;
import com.makemywallet.ui.constant.ApplicationConstant;
import com.makemywallet.ui.constant.ProgressDialog;
import com.makemywallet.ui.constant.UtilsMethods;
import com.makemywallet.ui.customClickListener.OnCustomClickListener;
import com.makemywallet.ui.adapter.CustomAdapter;
import com.makemywallet.ui.Models.PrepaidResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GasFragment extends Fragment implements OnCustomClickListener {
    private static final String TAG = "GasFragment";
    BottomSheetDialog dialogPrepaid;
    Activity mActivity;
    ProgressDialog pd;
    ArrayList<PrepaidResponseModel> prepaidResponses = new ArrayList<>();
    TextView tvOperator;
    int service_id = 8;
    ImageView ivDownArrow;
    RelativeLayout rLayoutOperator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gas, container, false);
        mActivity = getActivity();
        getInitUI(root);
        return root;
    }

    private void getInitUI(View view) {
        tvOperator = view.findViewById(R.id.tvOperator);
        ivDownArrow = view.findViewById(R.id.ivDownArrow);
        rLayoutOperator = view.findViewById(R.id.rLayoutOperator);
        rLayoutOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    getGasAPI();
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title),
                            getResources().getString(R.string.connection_lost));
                }
            }
        });
    }

    private void getGasAPI() {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
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
            CustomAdapter adapter = new CustomAdapter(mActivity, GasFragment.this, prepaidResponses);
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
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.gas_recharge));
    }

    @Override
    public void onCustomClick(String v1, String v2) {
        Log.d("><><>", v1 + ">>>>>>>>>" + v2);
        tvOperator.setText(v2);
        dialogPrepaid.dismiss();
    }
}