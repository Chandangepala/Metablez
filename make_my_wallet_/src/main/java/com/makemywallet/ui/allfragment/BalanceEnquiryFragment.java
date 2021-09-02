package com.makemywallet.ui.allfragment;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.makemywallet.R;

public class BalanceEnquiryFragment extends Fragment {
    Activity mActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_balance_enquiry, container, false);
        // View root =inflater.inflate(R.layout.fragment_balance_enquiry, container, false);
        mActivity = getActivity();
        getInitUi(root);
        return root.getRootView();
    }

    private void getInitUi(View view) {

    }
}