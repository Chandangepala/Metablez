package com.basic.metablez.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basic.metablez.R;
import com.basic.metablez.adpaters.WalletViewPagerAdapter;

import java.util.ArrayList;

import pl.pzienowicz.autoscrollviewpager.AutoScrollViewPager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    AutoScrollViewPager autoScrollVPagerWallet;
    public WalletFragment() {
        // Required empty public constructor
    }

    public static WalletFragment newInstance(String param1, String param2) {
        WalletFragment fragment = new WalletFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wallet, container, false);

        initMain();

        setupViewPager();

        return view;
    }

    //To initialize all the views
    public void initMain(){
        autoScrollVPagerWallet = view.findViewById(R.id.auto_viewpager_wallet);
    }

    //To setup wallet view pager
    public void setupViewPager(){
        ArrayList<Integer> arrOffers = new ArrayList<>();
        addOffers(arrOffers);
        WalletViewPagerAdapter walletVPagerAdapter = new WalletViewPagerAdapter(arrOffers, getContext());
        autoScrollVPagerWallet.setAdapter(walletVPagerAdapter);
        autoScrollVPagerWallet.startAutoScroll();
    }

    //To add valus to ArrayList arrOffers
    public void addOffers(ArrayList<Integer> arrOffers){
        arrOffers.add(R.drawable.app_logo);
        arrOffers.add(R.drawable.app_name_img);
        arrOffers.add(R.drawable.farm_chicken_img);
        arrOffers.add(R.drawable.country_chicken);
        arrOffers.add(R.drawable.egg_img);
        arrOffers.add(R.drawable.sea_food_img);
        arrOffers.add(R.drawable.wallet_img);
        arrOffers.add(R.drawable.add_fund_img_y);
    }
}