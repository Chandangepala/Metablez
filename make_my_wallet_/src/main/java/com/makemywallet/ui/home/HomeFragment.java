package com.makemywallet.ui.home;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.google.android.material.tabs.TabLayout;
import com.makemywallet.MainActivity;
import com.makemywallet.R;
import com.makemywallet.databinding.FragmentHomeBinding;
import com.makemywallet.ui.activity.DashboardActivity;
import com.makemywallet.ui.adapter.BannerAdapter;
import com.makemywallet.ui.Models.BannerModel;
import com.makemywallet.ui.constant.ApplicationConstant;
import com.makemywallet.ui.constant.ProgressDialog;
import com.makemywallet.ui.constant.UtilsMethods;
import com.makemywallet.ui.home.adapter.CustomAdapter;
import com.makemywallet.ui.home.adapter.HomeAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    BannerAdapter bannerAdapter;
    HomeAdapter homeAdapter;
    Activity mActivity;
    ArrayList<BannerModel> arrayListBanner = new ArrayList<>();
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        mActivity = getActivity();
        getInitUI();
        return binding.getRoot();
    }

    private void getInitUI() {
        binding.tvMarque.setSelected(true);
       /* String mainBal = UtilsMethods.INSTANCE.getPreference(mActivity, "mainBalance");
        String aepsBal = UtilsMethods.INSTANCE.getPreference(mActivity, "aepsBalance");
        if (mainBal != null && !mainBal.isEmpty()) {
            binding.tvMainWalletValue.setText("₹ "+mainBal);
        }
        if (aepsBal != null && !aepsBal.isEmpty()) {
            binding.tvAESWalletValue.setText("₹ " + aepsBal);
        }*/
        /*ArrayList<BannerModel> arrayListHome = new ArrayList<>();
        arrayListHome.clear();
        ArrayList<BannerModel> arrayListBanner = new ArrayList<>();
        arrayListBanner.clear();

        for (int i = 0; i < 10; i++) {
            BannerModel bannerModel = new BannerModel();
            bannerModel.setBannerName("Banner");
            bannerModel.setBannerName("Banner");
            bannerModel.setBannerName("Banner");
            bannerModel.setBannerName("Banner");
            bannerModel.setBannerName("Banner");
            arrayListBanner.add(bannerModel);
        }*/



        //For setting Adapter
        binding.viewpager.setAdapter(new CustomAdapter(getChildFragmentManager()));
        int selectedTabPosition = binding.tabs.getSelectedTabPosition(); // get the position for the current selected
       /* if (Cons.BASE_ADDRESS.equals(Cons.REAL_BASE_ADDRESS)) {
            tabLayout.setBackgroundColor(getResources().getColor(R.color.blue));
        } else {
            tabLayout.setBackgroundColor(getResources().getColor(R.color.light_charcoal_grey));
        }*/
        binding.tabs.setSelectedTabIndicatorColor(Color.parseColor("#47b6fa"));
        binding.tabs.setSelectedTabIndicatorHeight((int) (2 * getResources().getDisplayMetrics().density));
        binding.tabs.setTabTextColors(Color.parseColor("#47b6fa"), Color.parseColor("#000a12"));
        binding.tabs.setupWithViewPager(binding.viewpager);

        binding.tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                final int position = tab.getPosition();
                try {
                    switch (tab.getPosition()) {
                        case 0:
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        default:
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });




       /* binding.rLayoutPhone42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_viewMoreFragment);
            }
        });
*/

/*
        if (arrayListHome != null && arrayListHome.size() > 0) {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.tvNoRecord.setVisibility(View.GONE);
            homeAdapter = new HomeAdapter(mActivity, arrayListHome);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL, false);
            binding.recyclerView.setHasFixedSize(true);
            binding.recyclerView.setLayoutManager(mLayoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setAdapter(homeAdapter);
        } else {
            binding.recyclerView.setVisibility(View.GONE);
            binding.tvNoRecord.setVisibility(View.VISIBLE);
            binding.tvNoRecord.setText(getResources().getString(R.string.noDataFound));
        }
*/

        if (UtilsMethods.INSTANCE.isNetworkAvailable(getActivity())) {
            getMainsWallet();
            getSliderApi();
        } else {
            UtilsMethods.INSTANCE.getNetworkError(getActivity(), getResources().getString(R.string.attention_error_title), getResources().getString(R.string.connection_lost));
        }
    }

    private void getMainsWallet() {
        ProgressDialog pdPrepaid = ProgressDialog.show(getActivity(), "Please Wait...");
        try {
            String mobile = UtilsMethods.INSTANCE.getPreference(getActivity(), "mMobile");
            String mPassword = UtilsMethods.INSTANCE.getPreference(getActivity(), "password");
            String url = ApplicationConstant.INSTANCE.GET_MAIN_WALLET + "?username=" + mobile + "&password=" + mPassword;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (pdPrepaid != null && pdPrepaid.isShowing()) {
                        pdPrepaid.dismiss();
                    }
                    Log.d("RESPONCE:::", response.toString());
                    try {
                        if (response != null) {
                            JSONObject obj = new JSONObject(response.toString());
                            String status = obj.getString("status");
                            if (status.equalsIgnoreCase("success")) {
                                binding.tvMainWalletValue.setText("₹ " + obj.getString("main_balance"));
                                binding.tvAESWalletValue.setText("₹ " + obj.getString("aeps_balance"));
                                binding.tvInsentiveWalletValue.setText("₹ " + obj.getString("insentive_balance"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    UtilsMethods.INSTANCE.getShowToast(getActivity(), getResources().getString(R.string.something_went_wrong));
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
                                UtilsMethods.INSTANCE.getShowToast(getActivity(), getResources().getString(R.string.something_went_wrong));
                            } else if (response.statusCode == 401) {

                            } else if (response.statusCode == 422) {
                                //  json = trimMessage(new String(response.data));
                                if (json != "" && json != null) {
                                    // displayMessage(json);
                                } else {
                                    UtilsMethods.INSTANCE.getShowToast(getActivity(), getResources().getString(R.string.please_try_again));
                                }
                            } else if (response.statusCode == 503) {
                                UtilsMethods.INSTANCE.getShowToast(getActivity(), getResources().getString(R.string.server_down));

                            } else {
                                UtilsMethods.INSTANCE.getShowToast(getActivity(), getResources().getString(R.string.please_try_again));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (error instanceof NoConnectionError) {
                            UtilsMethods.INSTANCE.getShowToast(getActivity(), getResources().getString(R.string.oops_connect_your_internet));
                        } else if (error instanceof NetworkError) {
                            UtilsMethods.INSTANCE.getShowToast(getActivity(), getResources().getString(R.string.oops_connect_your_internet));
                        } else if (error instanceof TimeoutError) {
                            try {
                                if (error.networkResponse == null) {
                                    if (error.getClass().equals(TimeoutError.class)) {
                                        UtilsMethods.INSTANCE.getShowToast(getActivity(), getResources().getString(R.string.timed_out));
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
            RequestQueue queue = Volley.newRequestQueue(getActivity());
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

    private void getSliderApi() {
       // ProgressDialog pd = ProgressDialog.show(mActivity, "Please Wait...");
        Log.d("<<<<<>>>>>>>>>>>", ">????" + ApplicationConstant.INSTANCE.GET_SLIDER_API);
        String url =ApplicationConstant.INSTANCE.GET_SLIDER_API;
        try {
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                   /* if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }*/
                    Log.d("RESPONCE:::", response.toString());
                    arrayListBanner.clear();
                    if (response.toString().equals("[]")) {
                        binding.tvNoRecordBanner.setVisibility(View.VISIBLE);
                    } else {
                        if (response != null && response.length() > 0) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    BannerModel bannerModel = new BannerModel();
                                    bannerModel.setBannerName(response.getJSONObject(i).getString("img"));
                                    arrayListBanner.add(bannerModel);
                                }
                                if (arrayListBanner != null && arrayListBanner.size() > 0) {
                                    binding.recyclerViewBanner.setVisibility(View.VISIBLE);
                                    binding.tvNoRecordBanner.setVisibility(View.GONE);
                                    bannerAdapter = new BannerAdapter(mActivity, arrayListBanner);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
                                    binding.recyclerViewBanner.setHasFixedSize(true);
                                    binding.recyclerViewBanner.setLayoutManager(mLayoutManager);
                                    binding.recyclerViewBanner.setItemAnimator(new DefaultItemAnimator());
                                    binding.recyclerViewBanner.setAdapter(bannerAdapter);
                                } else {
                                    binding.recyclerViewBanner.setVisibility(View.GONE);
                                    binding.tvNoRecordBanner.setVisibility(View.VISIBLE);
                                    binding.tvNoRecordBanner.setText(getResources().getString(R.string.noDataFound));
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
                 /*   if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }*/
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
           /* if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }*/
            e.printStackTrace();
        } catch (Exception e) {
            /*if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }*/
            e.printStackTrace();
        }
    }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.menu_home));
    }
}