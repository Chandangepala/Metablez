package com.makemywallet.ui.allfragment;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.makemywallet.R;
import com.makemywallet.databinding.FragmentBankingBinding;
import com.makemywallet.ui.constant.UtilsMethods;

public class BankingFragment extends Fragment {
    FragmentBankingBinding binding;
    Activity mActivity;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBankingBinding.inflate(inflater, container, false);
        mActivity = getActivity();
        getInitUI();
        return binding.getRoot();
    }

    private void getInitUI() {
        binding.lLayoutMPin.setVisibility(View.VISIBLE);
        binding.lLayoutBanking.setVisibility(View.GONE);

        if (!UtilsMethods.INSTANCE.getPreference(mActivity, "mFirstPin").isEmpty() && UtilsMethods.INSTANCE.getPreference(mActivity, "mFirstPin") != null && UtilsMethods.INSTANCE.getPreference(mActivity, "mFirstPin").equals("First")) {
            binding.lLayoutMPin.setVisibility(View.GONE);
            binding.lLayoutBanking.setVisibility(View.VISIBLE);
        }

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mpin = binding.edtMPin.getText().toString().trim();
                if (mpin != null && !mpin.isEmpty()) {
                    if (mpin.equals(UtilsMethods.INSTANCE.getPreference(mActivity, "mPIN"))) {
                        binding.lLayoutMPin.setVisibility(View.GONE);
                        binding.lLayoutBanking.setVisibility(View.VISIBLE);
                        binding.edtMPin.setText("");
                        UtilsMethods.INSTANCE.setPreference(mActivity, "mFirstPin", "First");
                    } else {
                        UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.please_enter_correct_mpin));
                    }
                } else {
                    UtilsMethods.INSTANCE.getShowToast(mActivity, getResources().getString(R.string.please_enter_mpin));
                }
            }
        });

        binding.lLayoutSendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_DMRPanelFragment);
            }
        });
        binding.lLayoutExpressDMR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_expressDMRFragment);
            }
        });

        binding.lLayoutMMM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_MMMFragment);
            }
        });

        binding.lLayoutGiveTopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_giveTopupFragment);
            }
        });

        binding.lLayoutCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_accountStatimentFragment);*/
                // navController.navigate(R.id.action_nav_home_to_creditCardFragment);
            }
        });
        binding.lLayoutICICIAEPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_ICICIAEPSFragment);
            }
        });
     /*   binding.lLayoutLoanRepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_loanRepaymentFragment);
            }
        });
        binding.lLayoutMutualFund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_home_to_mutualFundFragment);
            }
        });

*/
        // lLayoutICICIPos
    }


    @Override
    public void onStart() {
        super.onStart();
    }
}