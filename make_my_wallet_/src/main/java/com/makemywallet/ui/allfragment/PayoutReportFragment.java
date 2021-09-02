package com.makemywallet.ui.allfragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
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
import com.makemywallet.databinding.FragmentPayoutReportBinding;
import com.makemywallet.ui.Models.PayoutReportModel;
import com.makemywallet.ui.adapter.PayoutReportAdapter;
import com.makemywallet.ui.constant.ApplicationConstant;
import com.makemywallet.ui.constant.ProgressDialog;
import com.makemywallet.ui.constant.UtilsMethods;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;

public class PayoutReportFragment extends Fragment {
    FragmentPayoutReportBinding binding;
    Activity mActivity;
    ProgressDialog pd;
    ArrayList<PayoutReportModel> arrayList = new ArrayList<>();
    DatePickerDialog datePickerDialogFrom;
    int yearFrom, monthFrom, dayOfMonthFrom;
    String mFromDate = "";
    Calendar calendarFrom;
    DatePickerDialog datePickerDialogTo;
    int yearTo, monthTo, dayOfMonthTo;
    Calendar calendarTo;
    String mToDate = "";

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPayoutReportBinding.inflate(inflater, container, false);
        mActivity = getActivity();
        getInitUI();
        return binding.getRoot();
    }

    private void getInitUI() {
        calendarFrom = Calendar.getInstance();
        yearFrom = calendarFrom.get(Calendar.YEAR);
        monthFrom = calendarFrom.get(Calendar.MONTH);
        dayOfMonthFrom = calendarFrom.get(Calendar.DAY_OF_MONTH);
        mFromDate = yearFrom + "-" + (monthFrom + 1) + "-" + dayOfMonthFrom;
        binding.txtFromdate.setText(dayOfMonthFrom + "-" + (monthFrom + 1) + "-" + yearFrom);
        binding.rlFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialogFrom = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        mFromDate = year + "-" + (month + 1) + "-" + day;
                        binding.txtFromdate.setText(day + "-" + (month + 1) + "-" + year);
                    }
                }, yearFrom, monthFrom, dayOfMonthFrom);
                // datePickerDialogFrom.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialogFrom.show();
            }
        });

        calendarTo = Calendar.getInstance();
        yearTo = calendarTo.get(Calendar.YEAR);
        monthTo = calendarTo.get(Calendar.MONTH);
        dayOfMonthTo = calendarTo.get(Calendar.DAY_OF_MONTH);
        mToDate = yearTo + "-" + (monthTo + 1) + "-" + dayOfMonthTo;
        binding.txtTodate.setText(dayOfMonthTo + "-" + (monthTo + 1) + "-" + yearTo);
        binding.rlToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialogTo = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        mToDate = year + "-" + (month + 1) + "-" + day;
                        binding.txtTodate.setText(day + "-" + (month + 1) + "-" + year);
                    }
                }, yearTo, monthTo, dayOfMonthTo);
                // datePickerDialogTo.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialogTo.show();
            }
        });
        if (mFromDate != null && !mFromDate.isEmpty()) {
            if (mToDate != null && !mToDate.isEmpty()) {
                getPayoutReport();
            } else {
                binding.tvErrorMessage.setVisibility(View.VISIBLE);
                binding.tvErrorMessage.setText(getResources().getString(R.string.select_to_date));
            }
        } else {
            binding.tvErrorMessage.setVisibility(View.VISIBLE);
            binding.tvErrorMessage.setText(getResources().getString(R.string.select_from_date));
        }
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    if (mFromDate != null && !mFromDate.isEmpty()) {
                        if (mToDate != null && !mToDate.isEmpty()) {
                            getPayoutReport();
                        } else {
                            binding.tvErrorMessage.setVisibility(View.VISIBLE);
                            binding.tvErrorMessage.setText(getResources().getString(R.string.select_to_date));
                        }
                    } else {
                        binding.tvErrorMessage.setVisibility(View.VISIBLE);
                        binding.tvErrorMessage.setText(getResources().getString(R.string.select_from_date));
                    }
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                }
            }
        });
    }


    private void getPayoutReport() {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            String mobile = UtilsMethods.INSTANCE.getPreference(mActivity, "mMobile");
            String mPassword = UtilsMethods.INSTANCE.getPreference(mActivity, "password");
            String url = ApplicationConstant.INSTANCE.GET_PAYOUT_REPORT + "?username=" + mobile + "&password=" + mPassword + "&fromdate=" + mFromDate + "&todate=" + mToDate;

            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    arrayList.clear();
                    if (response.toString().equals("[]")) {
                        binding.tvNoRecord.setVisibility(View.VISIBLE);
                    } else {
                    if (response != null && response.length() > 0) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                PayoutReportModel model = new PayoutReportModel();
                                model.setOrderId(response.getJSONObject(i).getString("order_id"));
                                model.setDate(response.getJSONObject(i).getString("date"));
                                model.setNumber(response.getJSONObject(i).getString("number"));
                                model.setProviderName(response.getJSONObject(i).getString("provider_name"));
                                model.setTxnid(response.getJSONObject(i).getString("txnid"));
                                model.setAmount(response.getJSONObject(i).getString("amount"));
                                model.setTotalBalance(response.getJSONObject(i).getString("total_balance"));
                                model.setStatus(response.getJSONObject(i).getString("status"));
                                model.setSurcharge(response.getJSONObject(i).getString("surcharge"));
                                arrayList.add(model);
                            }
                            if (arrayList.size() > 0) {
                                binding.recycViewAccountState.setVisibility(View.VISIBLE);
                                binding.tvNoRecord.setVisibility(View.GONE);
                                PayoutReportAdapter adapter = new PayoutReportAdapter(mActivity, arrayList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                binding.recycViewAccountState.setLayoutManager(mLayoutManager);
                                binding.recycViewAccountState.setItemAnimator(new DefaultItemAnimator());
                                binding.recycViewAccountState.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                binding.recycViewAccountState.setVisibility(View.GONE);
                                binding.tvNoRecord.setVisibility(View.VISIBLE);
                                binding.tvNoRecord.setText(getResources().getString(R.string.noDataFound));
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