package com.makemywallet.ui.allfragment;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.makemywallet.R;

public class BBPSFragment extends Fragment {
    LinearLayout lLayElectricity, lLayWater;
    Activity mActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_b_b_p_s, container, false);
        mActivity =getActivity();
        getInitUI(root);
        return root;
    }

    private void getInitUI(View view) {
        lLayElectricity = view.findViewById(R.id.lLayElectricity);
        lLayWater = view.findViewById(R.id.lLayWater);
        lLayElectricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_BBPSFragment_to_electricityFragment);
            }
        });
        lLayWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_BBPSFragment_to_waterFragment);
            }
        });
    }
}