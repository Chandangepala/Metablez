package com.makemywallet.ui.allfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.makemywallet.R;
import com.makemywallet.databinding.FragmentExpressDMRBinding;
import com.makemywallet.ui.Models.BankListModel;
import com.makemywallet.ui.Models.BeneficiaryListModel;
import com.makemywallet.ui.Models.PrepaidResponseModel;
import com.makemywallet.ui.adapter.BankListAdapter;
import com.makemywallet.ui.adapter.BeneficiaryListAdapter;
import com.makemywallet.ui.adapter.CustomSpinnerAdapter;
import com.makemywallet.ui.constant.ApplicationConstant;
import com.makemywallet.ui.constant.ProgressDialog;
import com.makemywallet.ui.constant.UtilsMethods;
import com.makemywallet.ui.customClickListener.OnCustomClickListener;
import com.makemywallet.ui.customClickListener.OnCustomFourClickListener;
import com.makemywallet.ui.customClickListener.OnCustomSingleClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ExpressDMRFragment extends Fragment implements OnCustomClickListener, OnCustomSingleClickListener, OnCustomFourClickListener {
    FragmentExpressDMRBinding binding;
    Activity mActivity;
    ProgressDialog pd;
    ArrayList<BeneficiaryListModel> arrayListBeneficiary = new ArrayList<>();
    ArrayList<BankListModel> arrayListBank = new ArrayList<>();
    BottomSheetDialog dialogBeneList;
    BottomSheetDialog dialogTransferTo;
    String mBankId = "";
    String mBankName = "";
    int modeId = 0;
    String accountNumber = "";
    String baneName = "";
    String iFSCCode = "";
    String mobileNumber = "";
    BankListAdapter adapterBank;
    String search = "";

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExpressDMRBinding.inflate(inflater, container, false);
        mActivity = getActivity();
        getInitUI();
        return binding.getRoot();
    }

    private void getInitUI() {
        binding.btnALLBaneficiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.lLayoutAddForm.setVisibility(View.GONE);
                binding.lLayoutAddBaneList.setVisibility(View.VISIBLE);
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    getBeneficiaryList();
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                }
            }
        });

        binding.btnAddBaneficiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.lLayoutAddForm.setVisibility(View.VISIBLE);
                binding.lLayoutAddBaneList.setVisibility(View.GONE);
            }
        });

        if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
            getBankList();
        } else {
            UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
        }

        binding.edtChooseBank.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(">>>>>>>>>", charSequence.toString());
                binding.recyclerView.setVisibility(View.VISIBLE);
                search = charSequence.toString();
                binding.lLaddForm.setVisibility(View.VISIBLE);
                if (search != null && search.length() > 1 && !search.isEmpty()) {
                    if (arrayListBank.size() > 0) {
                        binding.recyclerView.setVisibility(View.VISIBLE);
                        binding.lLaddForm.setVisibility(View.GONE);
                        filter(charSequence.toString());
                    }
                }
                /*search = charSequence.toString();
                if (arrayListBank.size() > 0) {
                    if (search.length()>=1) {
                        getBottomSheetBeneList(search);
                    }
                }*/
            }

            @Override
            public void afterTextChanged(Editable editable) {
                search = editable.toString();
                if (arrayListBank.size() > 0) {
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    filter(editable.toString());
                    /*if (search.length()>=1) {
                        //getBottomSheetBeneList(search);
                    }*/
                }
                //after the change calling the method and passing the search input
                //filter(editable.toString());
              /*  if (arrayListBank.size() > 0) {
                    getBottomSheetBeneList(editable.toString());
                }*/
            }
        });

/*
        binding.btnIFSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    if (mBankId != null && !mBankId.isEmpty()) {
                        getIFSC();
                    } else {
                        binding.tvErrorMessage.setVisibility(View.VISIBLE);
                        binding.tvErrorMessage.setText(getResources().getString(R.string.please_choose_bank));
                    }
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                }
            }
        });*/

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    accountNumber = binding.edtAccountNumber.getText().toString().trim();
                    baneName = binding.edtBanefeciaryName.getText().toString().trim();
                    iFSCCode = binding.edtIFSCCode.getText().toString().trim();
                    mobileNumber = binding.edtMobile.getText().toString().trim();

                    if (mBankName != null && !mBankName.isEmpty()) {
                        if (accountNumber != null && !accountNumber.isEmpty()) {
                            if (baneName != null && !baneName.isEmpty()) {
                                if (iFSCCode != null && !iFSCCode.isEmpty()) {
                                    if (mobileNumber != null && !mobileNumber.isEmpty()) {
                                        binding.tvErrorMessage.setVisibility(View.INVISIBLE);
                                        getAddBenefisry();
                                    } else {
                                        binding.tvErrorMessage.setVisibility(View.VISIBLE);
                                        binding.tvErrorMessage.setText(getResources().getString(R.string.enter_mobile));
                                    }
                                } else {
                                    binding.tvErrorMessage.setVisibility(View.VISIBLE);
                                    binding.tvErrorMessage.setText(getResources().getString(R.string.enter_ifsc_code));
                                }
                            } else {
                                binding.tvErrorMessage.setVisibility(View.VISIBLE);
                                binding.tvErrorMessage.setText(getResources().getString(R.string.enter_baneficiary_name));
                            }
                        } else {
                            binding.tvErrorMessage.setVisibility(View.VISIBLE);
                            binding.tvErrorMessage.setText(getResources().getString(R.string.enter_account_number));
                        }
                    } else {
                        binding.tvErrorMessage.setVisibility(View.VISIBLE);
                        binding.tvErrorMessage.setText(getResources().getString(R.string.please_choose_bank));
                    }
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                }
            }
        });

    }

    @Override
    public void onCustomFourClick(String v1, String v2, String v3, String v4) {
        Log.d(">>>>>", v1 + ">>>>" + v2 + ">>>>" + v3);
        if (v1 != null && v2 != null && v3 != null && v4 != null) {
            getBottomSheetTransfer(v1, v2, v3, v4);
        }
    }

    @Override
    public void onCustomSingleClick(String beneId) {
        Log.d("><><><>", ">>>>>>>" + beneId);
        if (beneId != null && !beneId.isEmpty()) {
            getDeleteDialog(beneId);
        }
    }

    private void getDeleteDialog(String baneId) {
        new SweetAlertDialog(mActivity, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Attantion !")
                .setContentText("Are you sure want to delete?")
                .setConfirmText("Yes")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                            getDeleteBene(baneId);
                        } else {
                            UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                        }
                    }
                })
                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    private void getDeleteBene(String beneId) {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            String mobile = UtilsMethods.INSTANCE.getPreference(mActivity, "mMobile");
            String password = UtilsMethods.INSTANCE.getPreference(mActivity, "password");
            String url = ApplicationConstant.INSTANCE.GET_XPRESS_DMR_DELETE_BENE + "?username=" + mobile + "&password=" + password + "&bene_id=" + beneId;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    if (response != null && response.has("status")) {
                        try {
                            String message = response.getString("message");
                            getSuccessful(mActivity, message);
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

    private void getBeneficiaryList() {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            String mobile = UtilsMethods.INSTANCE.getPreference(mActivity, "mMobile");
            String password = UtilsMethods.INSTANCE.getPreference(mActivity, "password");
            String url = ApplicationConstant.INSTANCE.GET_XPRESS_DMR_BENE_LIST + "?username=" + mobile + "&password=" + password;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    if (response != null && response.has("bene_list")) {
                        try {
                            arrayListBeneficiary.clear();
                            JSONArray jsonArray = response.getJSONArray("bene_list");
                            if (jsonArray != null && jsonArray.length() > 0) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    BeneficiaryListModel model = new BeneficiaryListModel();
                                    model.setBeneficiaryId(jsonArray.getJSONObject(i).getString("bene_id"));
                                    model.setRecipientName(jsonArray.getJSONObject(i).getString("bene_name"));
                                    model.setBank(jsonArray.getJSONObject(i).getString("bank_name"));
                                    model.setIfsc(jsonArray.getJSONObject(i).getString("ifsc_code"));
                                    model.setAccount(jsonArray.getJSONObject(i).getString("account_number"));
                                    arrayListBeneficiary.add(model);
                                }

                                if (arrayListBeneficiary.size() > 0) {
                                    binding.lLayoutAddBaneList.setVisibility(View.VISIBLE);
                                    binding.recycViewBaneficiary.setVisibility(View.VISIBLE);
                                    binding.tvNoRecord.setVisibility(View.GONE);
                                    BeneficiaryListAdapter adapter = new BeneficiaryListAdapter(mActivity, ExpressDMRFragment.this, ExpressDMRFragment.this, arrayListBeneficiary);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                    binding.recycViewBaneficiary.setLayoutManager(mLayoutManager);
                                    binding.recycViewBaneficiary.setItemAnimator(new DefaultItemAnimator());
                                    binding.recycViewBaneficiary.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    binding.recycViewBaneficiary.setVisibility(View.GONE);
                                    binding.tvNoRecord.setVisibility(View.VISIBLE);
                                    binding.tvNoRecord.setText(getResources().getString(R.string.noDataFound));
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

    private void getBankList() {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, ApplicationConstant.INSTANCE.GET_XPRESS_DMR_BANK_LIST, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    if (response != null && response.length() > 0) {
                        arrayListBank.clear();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jresponse = response.getJSONObject(i);
                                BankListModel model = new BankListModel();
                                model.setId(jresponse.getString("id"));
                                model.setBankId(jresponse.getString("bank_id"));
                                model.setBankName(jresponse.getString("bank_name"));
                                model.setBankCode(jresponse.getString("bank_code"));
                                model.setIfsc(jresponse.getString("ifsc"));
                                arrayListBank.add(model);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (arrayListBank.size() > 0) {
                            //  binding.recyclerView.setVisibility(View.VISIBLE);
                            //tvNoRecord.setVisibility(View.GONE);
                            adapterBank = new BankListAdapter(mActivity, ExpressDMRFragment.this, arrayListBank);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            binding.recyclerView.setLayoutManager(mLayoutManager);
                            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                            binding.recyclerView.setAdapter(adapterBank);
                            adapterBank.notifyDataSetChanged();
                        } else {
                            binding.recyclerView.setVisibility(View.GONE);
                            // tvNoRecord.setVisibility(View.VISIBLE);
                            //  tvNoRecord.setText(getResources().getString(R.string.noDataFound));
                        }

                        /*if (arrayListBank.size() > 0) {
                            getBottomSheetBeneList();
                        }*/
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

    private void getBottomSheetBeneList(String text) {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_custom, null);
        dialogBeneList = new BottomSheetDialog(getActivity());
        dialogBeneList.setContentView(view);
        ImageView ivClose = dialogBeneList.findViewById(R.id.ivClose);
        TextView tvNoRecord = dialogBeneList.findViewById(R.id.tvNoRecord);
        TextView tvHeadBottomTittle = dialogBeneList.findViewById(R.id.tvHeadBottomTittle);
        tvHeadBottomTittle.setText("Choose Bank");
        RecyclerView recyclerView = dialogBeneList.findViewById(R.id.recyclerView);

        if (arrayListBank.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoRecord.setVisibility(View.GONE);
            adapterBank = new BankListAdapter(mActivity, ExpressDMRFragment.this, arrayListBank);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapterBank);
            adapterBank.notifyDataSetChanged();
        } else {
            recyclerView.setVisibility(View.GONE);
            tvNoRecord.setVisibility(View.VISIBLE);
            tvNoRecord.setText(getResources().getString(R.string.noDataFound));
        }
        ArrayList<BankListModel> filterdNames = new ArrayList<>();
        for (BankListModel s : arrayListBank) {
            if (s.getBankName().contains(text)) {
                filterdNames.add(s);
            }
        }
        adapterBank.updateList(filterdNames);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBeneList.dismiss();
            }
        });
        dialogBeneList.show();
    }

    @Override
    public void onCustomClick(String v1, String v2) {
        Log.d(">>>>>>>>", ">>" + v1 + ">>>>>" + v2);
        if (v1 != null && v2 != null) {
            binding.edtChooseBank.setText(v2);
            mBankId = v1;
            mBankName = v2;
            binding.recyclerView.setVisibility(View.GONE);
            binding.lLaddForm.setVisibility(View.VISIBLE);
            if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                if (mBankId != null && !mBankId.isEmpty()) {
                    getIFSC();
                } else {
                    binding.tvErrorMessage.setVisibility(View.VISIBLE);
                    binding.tvErrorMessage.setText(getResources().getString(R.string.please_choose_bank));
                }
            } else {
                UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
            }
        }
//        dialogBeneList.dismiss();
    }

    private void getIFSC() {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            String mobile = UtilsMethods.INSTANCE.getPreference(mActivity, "mMobile");
            String password = UtilsMethods.INSTANCE.getPreference(mActivity, "password");
            String url = ApplicationConstant.INSTANCE.GET_XPRESS_DMR_IFSC + "?username=" + mobile + "&password=" + password + "&bank_id=" + mBankId;
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
                            if (status == 0) {
                                binding.edtIFSCCode.setText(response.getString("ifsc"));
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

    public void getSuccessfulAddBene(final Context context, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(context.getResources().getString(R.string.successful_title))
                .setContentText(message)
                .setCustomImage(R.drawable.ic_check_circle_green)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        binding.edtChooseBank.setText("");
                        binding.edtChooseBank.setHint("Choose Your Bank");
                        binding.edtAccountNumber.setText("");
                        binding.edtBanefeciaryName.setText("");
                        binding.edtIFSCCode.setText("");
                        binding.edtMobile.setText("");
                        mBankId = "";
                        mBankName = "";
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.lLaddForm.setVisibility(View.VISIBLE);
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
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
                        getBeneficiaryList();
                    }
                })
                .show();
    }

    private void getBottomSheetTransfer(String baneId, String name, String account, String ifsc) {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_transfer_layout, null);
        dialogTransferTo = new BottomSheetDialog(getActivity());
        dialogTransferTo.setContentView(view);
        ImageView ivClose = dialogTransferTo.findViewById(R.id.ivClose);
        TextView tvHeadBottomTittle = dialogTransferTo.findViewById(R.id.tvHeadBottomTittle);
        tvHeadBottomTittle.setText("Transfer To");
        TextView tvAccount = dialogTransferTo.findViewById(R.id.tvAccount);
        TextView txtHolderValue = dialogTransferTo.findViewById(R.id.txtHolderValue);
        TextView txtIFSCBankName = dialogTransferTo.findViewById(R.id.txtIFSCBankName);
        //TextView rLayoutModeType = dialogTransferTo.findViewById(R.id.rLayoutModeType);
        // TextView tvModeType = dialogTransferTo.findViewById(R.id.tvModeType);
        Spinner spinnerModeType = dialogTransferTo.findViewById(R.id.spinnerModeType);
        Button btnSubmit = dialogTransferTo.findViewById(R.id.btnSubmit);
        EditText edtTransferAmount = dialogTransferTo.findViewById(R.id.edtTransferAmount);
        tvAccount.setText(account);
        txtHolderValue.setText(name);
        txtIFSCBankName.setText(ifsc);
        ArrayList<PrepaidResponseModel> arrayListModeType = new ArrayList<>();
        arrayListModeType.clear();
        arrayListModeType.add(new PrepaidResponseModel(0, "Please select transfer method"));
        arrayListModeType.add(new PrepaidResponseModel(1, "NEFT"));
        arrayListModeType.add(new PrepaidResponseModel(2, "IMPS"));
        arrayListModeType.add(new PrepaidResponseModel(2, "RTGS"));

        if (arrayListModeType.size() > 0) {
            CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(mActivity, arrayListModeType);
            spinnerModeType.setAdapter(customAdapter);
        }

        spinnerModeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    //   UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.please_select_transfer_method));
                } else {
                    modeId = arrayListModeType.get(i).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilsMethods.INSTANCE.isNetworkAvailable(mActivity)) {
                    String amount = edtTransferAmount.getText().toString().trim();
                    if (modeId != 0) {
                        if (amount != null && !amount.isEmpty()) {
                            getTransaction(baneId, name, amount, account, ifsc);
                        } else {
                            UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.please_enter_transfer_amount));
                        }
                    } else {
                        UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.please_select_transfer_method));
                    }
                } else {
                    UtilsMethods.INSTANCE.getNetworkError(mActivity, getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
                }
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTransferTo.dismiss();
            }
        });
        dialogTransferTo.show();
    }

    private void getAddBenefisry() {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            String mobile = UtilsMethods.INSTANCE.getPreference(mActivity, "mMobile");
            String mPassword = UtilsMethods.INSTANCE.getPreference(mActivity, "password");
            String url = ApplicationConstant.INSTANCE.GET_XPRESS_DMR_ADD_BENE + "?username=" + mobile + "&password=" + mPassword + "&mobile=" + mobileNumber + "&account_number=" + accountNumber + "&bene_name=" + baneName + "&ifsc_code=" + iFSCCode + "&bank_name=" + mBankName;
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
                                getSuccessfulAddBene(mActivity, message);
                            } else {
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

    private void getTransaction(String beneId, String name, String amount, String accountNumber, String ifsc) {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        try {
            String mobile = UtilsMethods.INSTANCE.getPreference(mActivity, "mMobile");
            String password = UtilsMethods.INSTANCE.getPreference(mActivity, "password");
            String searchMobile = UtilsMethods.INSTANCE.getPreference(mActivity, "searchMobile");

            String url = ApplicationConstant.INSTANCE.GET_XPRESS_DMR_TRANSFER + "?username=" + mobile + "&password=" + password + "&mobile=" + searchMobile + "&beneficiary_id=" + beneId + "&amount=" + amount + "&account_number=" + accountNumber + "&ifsc=" + ifsc + "&name=" + name;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    if (response != null && response.has("status")) {
                        try {
                            String message = response.getString("message");
                            getSuccessfulTransaction(mActivity, message);
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

    public void getSuccessfulTransaction(final Context context, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(context.getResources().getString(R.string.successful_title))
                .setContentText(message)
                .setCustomImage(R.drawable.ic_check_circle_green)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        dialogTransferTo.dismiss();
                    }
                })
                .show();
    }

    private void filter(String text) {
        ArrayList<BankListModel> filterdNames = new ArrayList<>();
        for (BankListModel s : arrayListBank) {
            if (s.getBankName().toLowerCase().contains(text.toLowerCase())) {
                filterdNames.add(s);
            }
        }
        adapterBank.updateList(filterdNames);
    }
}