package com.makemywallet.ui.allfragment;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.makemywallet.MainActivity;
import com.makemywallet.R;
import com.makemywallet.databinding.FragmentRechargeBinding;
import com.makemywallet.ui.constant.Utils;
import com.makemywallet.ui.constant.UtilsMethods;

public class RechargeFragment extends Fragment {
    FragmentRechargeBinding binding;
    Activity mActivity;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRechargeBinding.inflate(getLayoutInflater(), container, false);
        mActivity =getActivity();
        getInitUI();
        return binding.getRoot();
    }

    private void getInitUI() {
        binding.lLayMobileRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Utils.RECHARGE_TYPE="Prepaid";
             /*   NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_nav_gallery);*/
            }
        });
        binding.lLayGasRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_gasFragment);
            }
        });

        binding.lLayDataRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilsMethods.getShowToast(mActivity,"Coming soon..");
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_navDatacard);
            }
        });

        binding.lLayDTHRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_DTHRechargeFragment);
            }
        });

        binding.lLayPostpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Utils.RECHARGE_TYPE="Postpaid";
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_postpaidFragment);
               // navController.navigate(R.id.action_nav_home_to_nav_gallery);
            }
        });

        binding.lLayBBPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_BBPSFragment);
            }
        });

        binding.lLayLandLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_landlineFragment);
            }
        });

        binding.lLayBroadBand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilsMethods.getShowToast(mActivity,"Coming soon..");
            }
        });
        binding.lLayCable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilsMethods.getShowToast(mActivity,"Coming soon..");
            }
        });

    }

    public void onResume() {
        super.onResume();
       // ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.menu_home));
    }
}