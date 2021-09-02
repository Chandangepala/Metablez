package com.makemywallet.ui.allfragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
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
import com.makemywallet.databinding.FragmentMMMBinding;
import com.makemywallet.ui.Models.BankDetailDemoModel;
import com.makemywallet.ui.Models.BankDetailMethodModel;
import com.makemywallet.ui.Models.BankDetailModel;
import com.makemywallet.ui.adapter.BankDetailAdapter;
import com.makemywallet.ui.adapter.BankPaymentListAdapter;
import com.makemywallet.ui.adapter.CompanyBankListAdapter;
import com.makemywallet.ui.constant.ApplicationConstant;
import com.makemywallet.ui.constant.ProgressDialog;
import com.makemywallet.ui.constant.UtilsMethods;
import com.makemywallet.ui.customClickListener.OnCustomClickListener;
import com.makemywallet.ui.customClickListener.OnCustomSingleClickListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MMMFragment extends Fragment implements OnCustomClickListener,OnCustomSingleClickListener {
    FragmentMMMBinding binding;
    Activity mActivity;
    ProgressDialog pd;
    ArrayList<BankDetailModel> arrayListBankDetail = new ArrayList<>();
    ArrayList<BankDetailMethodModel> arrayListMethod = new ArrayList<>();
    ArrayList<BankDetailDemoModel> arrayListMethodDemo = new ArrayList<>();

    //ArrayList<BankListModel> arrayListMethod = new ArrayList<>();
    //ArrayList<MethodModel> arrayListMethod = new ArrayList<>();
    //ArrayList<BankListModel> arrayListBank = new ArrayList<>();

    DatePickerDialog datePickerDialog;
    int year, month, dayOfMonth;
    Calendar calendar;
    BottomSheetDialog dialogBeneList;
    BottomSheetDialog dialogPayment;
    BankPaymentListAdapter adapterPayment;
    CompanyBankListAdapter companyBankListAdapter;
    String mBankName = "";
    String mPaymentMethod = "";
    String paymentDate = "";
    String accountNumber = "";
    String bankRefId = "";

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMMMBinding.inflate(inflater, container, false);
        mActivity = getActivity();
        getInitUI();
        return binding.getRoot();
    }

    private void getInitUI() {
        binding.rlPaymentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        paymentDate = day + "-" + month + "-" + year;
                        binding.tvDate.setText(paymentDate);
                    }
                }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

      /*  if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
            getBankMethod();
        } else {
            UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
        }*/

        if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
            // getBankDetailS();
            getBankDetail();
        } else {
            UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
        }

        binding.rLayoutChooseBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBottomSheetCompanyList();
            }
        });

        binding.rLayoutChoosePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBottomSheetPaymentList();
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    accountNumber = binding.edtAmount.getText().toString().trim();
                    bankRefId = binding.edtBankRefId.getText().toString().trim();
                    if (accountNumber != null && !accountNumber.isEmpty()) {
                        if (mBankName != null && !mBankName.isEmpty()) {
                            if (mPaymentMethod != null && !mPaymentMethod.isEmpty()) {
                                if (paymentDate != null && !paymentDate.isEmpty()) {
                                    if (bankRefId != null && !bankRefId.isEmpty()) {
                                        binding.tvErrorMessage.setVisibility(View.INVISIBLE);
                                        getFundRequest();
                                    } else {
                                        binding.tvErrorMessage.setVisibility(View.VISIBLE);
                                        binding.tvErrorMessage.setText(getResources().getString(R.string.enter_bankRefId));
                                    }
                                } else {
                                    binding.tvErrorMessage.setVisibility(View.VISIBLE);
                                    binding.tvErrorMessage.setText(getResources().getString(R.string.select_payment_date));
                                }
                            } else {
                                binding.tvErrorMessage.setVisibility(View.VISIBLE);
                                binding.tvErrorMessage.setText(getResources().getString(R.string.enter_payment_method));
                            }
                        } else {
                            binding.tvErrorMessage.setVisibility(View.VISIBLE);
                            binding.tvErrorMessage.setText(getResources().getString(R.string.please_choose_bank));
                        }
                    } else {
                        binding.tvErrorMessage.setVisibility(View.VISIBLE);
                        binding.tvErrorMessage.setText(getResources().getString(R.string.enter_amount));
                    }
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                }
            }
        });

    }

    private void getBankDetailS() {
        arrayListMethodDemo.clear();
        arrayListMethodDemo.add(new BankDetailDemoModel("1", "Makmywallet", "HDFC", "HDFC000001", "928372632828", "Jaipur"));
        arrayListMethodDemo.add(new BankDetailDemoModel("1", "Makmywallet", "HDFC", "HDFC000001", "928372632828", "Jaipur"));
        arrayListMethodDemo.add(new BankDetailDemoModel("1", "Makmywallet", "HDFC", "HDFC000001", "928372632828", "Jaipur"));
        arrayListMethodDemo.add(new BankDetailDemoModel("1", "Makmywallet", "HDFC", "HDFC000001", "928372632828", "Jaipur"));
        arrayListMethodDemo.add(new BankDetailDemoModel("1", "Makmywallet", "HDFC", "HDFC000001", "928372632828", "Jaipur"));
        arrayListMethodDemo.add(new BankDetailDemoModel("1", "Makmywallet", "HDFC", "HDFC000001", "928372632828", "Jaipur"));
        arrayListMethodDemo.add(new BankDetailDemoModel("1", "Makmywallet", "HDFC", "HDFC000001", "928372632828", "Jaipur"));
        arrayListMethodDemo.add(new BankDetailDemoModel("1", "Makmywallet", "HDFC", "HDFC000001", "928372632828", "Jaipur"));
        arrayListMethodDemo.add(new BankDetailDemoModel("1", "Makmywallet", "HDFC", "HDFC000001", "928372632828", "Jaipur"));
        arrayListMethodDemo.add(new BankDetailDemoModel("1", "Makmywallet", "HDFC", "HDFC000001", "928372632828", "Jaipur"));


        if (arrayListMethodDemo.size() > 0) {
            binding.tvNoRecord.setVisibility(View.GONE);
            BankDetailAdapter adapter = new BankDetailAdapter(mActivity, arrayListMethodDemo);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            binding.recycViewBankDetail.setLayoutManager(mLayoutManager);
            binding.recycViewBankDetail.setItemAnimator(new DefaultItemAnimator());
            binding.recycViewBankDetail.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            binding.recycViewBankDetail.setVisibility(View.GONE);
            binding.tvNoRecord.setVisibility(View.VISIBLE);
            binding.tvNoRecord.setText(getResources().getString(R.string.noDataFound));
        }
    }

    private void getBankDetail() {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            String url = ApplicationConstant.INSTANCE.GET_BANK_METHOD;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    if (response != null && response.has("bankdetail")) {
                        try {
                            arrayListMethod.clear();
                            arrayListBankDetail.clear();
                            JSONArray jsonArray = response.getJSONArray("bankdetail");
                            if (jsonArray != null && jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    BankDetailModel model = new BankDetailModel();
                                    model.setId(jsonArray.getJSONObject(i).getString("id"));
                                    model.setHolderName(jsonArray.getJSONObject(i).getString("holder_name"));
                                    model.setBankName(jsonArray.getJSONObject(i).getString("bank_name"));
                                    model.setBankIfsc(jsonArray.getJSONObject(i).getString("bank_ifsc"));
                                    model.setBankBranch(jsonArray.getJSONObject(i).getString("bank_branch"));
                                    model.setBankAccount(jsonArray.getJSONObject(i).getString("bank_account"));
                                    model.setCreated_at(jsonArray.getJSONObject(i).getString("created_at"));
                                    arrayListBankDetail.add(model);
                                }
                                JSONArray jsonArrayMethod = response.getJSONArray("method");
                                if (jsonArrayMethod != null && jsonArrayMethod.length() > 0) {
                                    for (int i = 0; i < jsonArrayMethod.length(); i++) {
                                        BankDetailMethodModel model = new BankDetailMethodModel();
                                        model.setId(jsonArrayMethod.getJSONObject(i).getString("id"));
                                        model.setMethodName(jsonArrayMethod.getJSONObject(i).getString("method_name"));
                                        arrayListMethod.add(model);
                                    }
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

    private void getFundRequest() {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            String mobile = UtilsMethods.INSTANCE.getPreference(mActivity, "mMobile");
            String password = UtilsMethods.INSTANCE.getPreference(mActivity, "password");
            String url = ApplicationConstant.INSTANCE.GET_FUND_REQUEST + "?username=" + mobile + "&password=" + password + "&amount=" + accountNumber + "&bank_name=" + "1" + "&payment_method=" + "1" + "&payment_date=" + paymentDate + "&bank_ref=" + password + "&wallet_type=" + "1";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    if (response != null && response.has("status")) {
                        try {
                            if (response.getString("message") != null && !response.getString("message").isEmpty()) {
                                getErrorDial(mActivity, response.getString("message"));
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

    private void getBottomSheetCompanyList() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_custom, null);
        dialogBeneList = new BottomSheetDialog(getActivity());
        dialogBeneList.setContentView(view);
        ImageView ivClose = dialogBeneList.findViewById(R.id.ivClose);
        TextView tvNoRecord = dialogBeneList.findViewById(R.id.tvNoRecord);
        TextView tvHeadBottomTittle = dialogBeneList.findViewById(R.id.tvHeadBottomTittle);
        tvHeadBottomTittle.setText("Choose Company Bank");
        RecyclerView recyclerView = dialogBeneList.findViewById(R.id.recyclerView);
        if (arrayListBankDetail.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoRecord.setVisibility(View.GONE);
            companyBankListAdapter = new CompanyBankListAdapter(mActivity, MMMFragment.this, arrayListBankDetail);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(companyBankListAdapter);
            companyBankListAdapter.notifyDataSetChanged();
        } else {
            recyclerView.setVisibility(View.GONE);
            tvNoRecord.setVisibility(View.VISIBLE);
            tvNoRecord.setText(getResources().getString(R.string.noDataFound));
        }
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBeneList.dismiss();
            }
        });
        dialogBeneList.show();
    }

    private void getBottomSheetPaymentList() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_custom, null);
        dialogPayment = new BottomSheetDialog(getActivity());
        dialogPayment.setContentView(view);
        ImageView ivClose = dialogPayment.findViewById(R.id.ivClose);
        TextView tvNoRecord = dialogPayment.findViewById(R.id.tvNoRecord);
        TextView tvHeadBottomTittle = dialogPayment.findViewById(R.id.tvHeadBottomTittle);
        tvHeadBottomTittle.setText("Choose Payment Method");
        RecyclerView recyclerView = dialogPayment.findViewById(R.id.recyclerView);
        if (arrayListMethod.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoRecord.setVisibility(View.GONE);
            adapterPayment = new BankPaymentListAdapter(mActivity, MMMFragment.this, arrayListMethod);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapterPayment);
            adapterPayment.notifyDataSetChanged();
        } else {
            recyclerView.setVisibility(View.GONE);
            tvNoRecord.setVisibility(View.VISIBLE);
            tvNoRecord.setText(getResources().getString(R.string.noDataFound));
        }
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPayment.dismiss();
            }
        });
        dialogPayment.show();
    }

    @Override
    public void onCustomClick(String v1, String v2) {
        Log.d(">>>>>>>>>>", v1 + ">>>>>>>>>>" + v2);
        if (v1 != null && v2 != null) {
            mPaymentMethod = v2;
            binding.edtChoosePayment.setText(v2);
            dialogPayment.dismiss();
        }
    }

    @Override
    public void onCustomSingleClick(String v1) {
        Log.d(">>>>>>>>>>", v1);
        if (v1 != null) {
            mBankName = v1;
            binding.edtChooseBank.setText(v1);
            dialogBeneList.dismiss();
        }
    }

    public void getErrorDial(final Context context, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(context.getResources().getString(R.string.attention_error_title))
                .setContentText(message)
                .setCustomImage(R.drawable.ic_check_circle_green)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }

}