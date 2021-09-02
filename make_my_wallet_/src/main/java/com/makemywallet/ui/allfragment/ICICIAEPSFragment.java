package com.makemywallet.ui.allfragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.makemywallet.R;
import com.makemywallet.ui.constant.UtilsMethods;
import com.pay2all.aeps.AEPS_Service;
import java.util.ArrayList;
import java.util.List;

public class ICICIAEPSFragment extends Fragment {
    RelativeLayout rlAmountWithdraw, rlBalanceEnquiry, rlAdharPay, rlMiniStatement;
    FragmentManager fm;
    FragmentTransaction transaction;
    private List<String> backStack = new ArrayList<>();
    TextView txtAmountWithdraw, txtBalanceEnquairy, txtAdharPay, txtMiniStatement;
    ImageView ivAmountWithdraw,ivBalanceEnquiry,ivAdharPay,ivMiniStatement;
    int REQUEST_CODE = 1421;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_i_c_i_c_i_a_e_p_s, container, false);
        getInitUi(root);
        return root.getRootView();
    }

    private void getInitUi(View view) {
        rlAmountWithdraw = view.findViewById(R.id.rlAmountWithdraw);
        rlBalanceEnquiry = view.findViewById(R.id.rlBalanceEnquiry);
        rlAdharPay = view.findViewById(R.id.rlAdharPay);
        rlMiniStatement = view.findViewById(R.id.rlMiniStatement);
        txtAmountWithdraw = view.findViewById(R.id.txtAmountWithdraw);
        txtBalanceEnquairy = view.findViewById(R.id.txtBalanceEnquairy);
        txtAdharPay = view.findViewById(R.id.txtAdharPay);
        txtMiniStatement = view.findViewById(R.id.txtMiniStatement);
        ivAmountWithdraw = view.findViewById(R.id.ivAmountWithdraw);
        ivBalanceEnquiry = view.findViewById(R.id.ivBalanceEnquiry);
        ivAdharPay = view.findViewById(R.id.ivAdharPay);
        ivMiniStatement = view.findViewById(R.id.ivMiniStatement);
        txtAmountWithdraw.setTextColor(getResources().getColor(R.color.colorWhite));
        txtBalanceEnquairy.setTextColor(getResources().getColor(R.color.colorTextLight));
        txtAdharPay.setTextColor(getResources().getColor(R.color.colorTextLight));
        txtMiniStatement.setTextColor(getResources().getColor(R.color.colorTextLight));
        rlAmountWithdraw.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        rlBalanceEnquiry.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        rlAdharPay.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        rlMiniStatement.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        ivAmountWithdraw.setColorFilter(getResources().getColor(R.color.white));
        ivBalanceEnquiry.setColorFilter(getResources().getColor(R.color.colorYellow));
        ivAdharPay.setColorFilter(getResources().getColor(R.color.colorTextLight));
        ivMiniStatement.setColorFilter(getResources().getColor(R.color.colorTextLight));
        /*Intent intent=new Intent(getActivity(), AEPS_Service.class);
        intent.putExtra("mobile","9876543210");
        intent.putExtra("outlet_id","1421");
        intent.putExtra("service","cw");
        intent.putExtra("name","Basant Yadav");
        startActivityForResult(intent,REQUEST_CODE);*/
       // isUpdateFragment(new AmountWithdrawFragment(), "Amount Withdraw");
        rlAmountWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtAmountWithdraw.setTextColor(getResources().getColor(R.color.colorWhite));
                txtBalanceEnquairy.setTextColor(getResources().getColor(R.color.colorTextLight));
                txtAdharPay.setTextColor(getResources().getColor(R.color.colorTextLight));
                txtMiniStatement.setTextColor(getResources().getColor(R.color.colorTextLight));
                rlAmountWithdraw.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                rlBalanceEnquiry.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                rlAdharPay.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                rlMiniStatement.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                ivAmountWithdraw.setColorFilter(getResources().getColor(R.color.white));
                ivBalanceEnquiry.setColorFilter(getResources().getColor(R.color.colorYellow));
                ivAdharPay.setColorFilter(getResources().getColor(R.color.colorTextLight));
                ivMiniStatement.setColorFilter(getResources().getColor(R.color.colorTextLight));
                Intent intent=new Intent(getActivity(), AEPS_Service.class);
                //intent.putExtra("mobile","9876543210");
                intent.putExtra("mobile",UtilsMethods.INSTANCE.getPreference(getActivity(), "mMobile"));
                intent.putExtra("outlet_id","1421");
                intent.putExtra("service","cw");
                intent.putExtra("name","Basant Yadav");
                startActivityForResult(intent,REQUEST_CODE);

                // isUpdateFragment(new AmountWithdrawFragment(), "Amount Withdraw");
            }
        });
        rlBalanceEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtAmountWithdraw.setTextColor(getResources().getColor(R.color.colorTextLight));
                txtBalanceEnquairy.setTextColor(getResources().getColor(R.color.colorWhite));
                txtAdharPay.setTextColor(getResources().getColor(R.color.colorTextLight));
                txtMiniStatement.setTextColor(getResources().getColor(R.color.colorTextLight));
                rlBalanceEnquiry.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                rlAmountWithdraw.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                rlAdharPay.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                rlMiniStatement.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                ivAmountWithdraw.setColorFilter(getResources().getColor(R.color.colorOrange));
                ivBalanceEnquiry.setColorFilter(getResources().getColor(R.color.white));
                ivAdharPay.setColorFilter(getResources().getColor(R.color.colorTextLight));
                ivMiniStatement.setColorFilter(getResources().getColor(R.color.colorTextLight));
                Intent intent=new Intent(getActivity(), AEPS_Service.class);
               // intent.putExtra("mobile","9876543210");
                intent.putExtra("mobile",UtilsMethods.INSTANCE.getPreference(getActivity(), "mMobile"));
                intent.putExtra("outlet_id","1421");
                intent.putExtra("service","be");
                intent.putExtra("name","Basant Yadav");
                startActivityForResult(intent,REQUEST_CODE);
                //isUpdateFragment(new BalanceEnquiryFragment(), "Balance Enquiry");
            }
        });
        rlAdharPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtAmountWithdraw.setTextColor(getResources().getColor(R.color.colorTextLight));
                txtBalanceEnquairy.setTextColor(getResources().getColor(R.color.colorTextLight));
                txtAdharPay.setTextColor(getResources().getColor(R.color.colorWhite));
                txtMiniStatement.setTextColor(getResources().getColor(R.color.colorTextLight));
                rlBalanceEnquiry.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                rlAmountWithdraw.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                rlAdharPay.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                rlMiniStatement.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                ivAmountWithdraw.setColorFilter(getResources().getColor(R.color.colorOrange));
                ivBalanceEnquiry.setColorFilter(getResources().getColor(R.color.colorYellow));
                ivAdharPay.setColorFilter(getResources().getColor(R.color.colorWhite));
                ivMiniStatement.setColorFilter(getResources().getColor(R.color.colorTextLight));
                Intent intent=new Intent(getActivity(), AEPS_Service.class);
               // intent.putExtra("mobile","9876543210");
                intent.putExtra("mobile",UtilsMethods.INSTANCE.getPreference(getActivity(), "mMobile"));
                intent.putExtra("outlet_id","1421");
                intent.putExtra("service","ap");
                intent.putExtra("name","Basant Yadav");
                startActivityForResult(intent,REQUEST_CODE);
               // isUpdateFragment(new AdharPayFragment(), "Adhar Pay");
            }
        });
        rlMiniStatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtAmountWithdraw.setTextColor(getResources().getColor(R.color.colorTextLight));
                txtBalanceEnquairy.setTextColor(getResources().getColor(R.color.colorTextLight));
                txtAdharPay.setTextColor(getResources().getColor(R.color.colorTextLight));
                txtMiniStatement.setTextColor(getResources().getColor(R.color.colorWhite));
                rlBalanceEnquiry.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                rlAmountWithdraw.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                rlAdharPay.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                rlMiniStatement.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivAmountWithdraw.setColorFilter(getResources().getColor(R.color.colorOrange));
                ivBalanceEnquiry.setColorFilter(getResources().getColor(R.color.colorYellow));
                ivAdharPay.setColorFilter(getResources().getColor(R.color.colorTextLight));
                ivMiniStatement.setColorFilter(getResources().getColor(R.color.colorWhite));
                Intent intent=new Intent(getActivity(), AEPS_Service.class);
                intent.putExtra("mobile",UtilsMethods.INSTANCE.getPreference(getActivity(), "mMobile"));
                //intent.putExtra("mobile","9876543210");
                intent.putExtra("outlet_id","1421");
                intent.putExtra("service","mst");
                intent.putExtra("name","Basant Yadav");
                startActivityForResult(intent,REQUEST_CODE);
                //isUpdateFragment(new MiniStatementFragment(), "Mini Statement");
            }
        });
    }

    private void isUpdateFragment(Fragment fragment, String title) {
        fm = getActivity().getSupportFragmentManager();
        transaction = fm.beginTransaction();
        transaction.replace(R.id.frameContainer, fragment);
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
        backStack.add(title);
    }

    /*@Override
    public void onBackPressed() {
        if (backStack.size() == 1) {
            //finish();
            return;
        }
        if (backStack.size() > 1) {
            try {
                backStack.remove(backStack.size() - 1);
                if (backStack.size() != 0) {
                    fm.popBackStack();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        super.onBackPressed();
    }
*/


}