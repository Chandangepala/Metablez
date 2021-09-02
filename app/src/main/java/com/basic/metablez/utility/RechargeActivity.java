package com.basic.metablez.utility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basic.metablez.R;
import com.makemywallet.ui.allfragment.RechargeFragment;

public class RechargeActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtTitle, txtNext, txtChooseOperator;
    Animation slideInAnim;
    Animation fadeInAnim;
    LinearLayout networkLayout, airtelLayout, bsnlLayout, mtnlLayout, viLayout, jioLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        //To initialize UI
        initUI();

        txtNext.setOnClickListener(v -> {

        });

        txtChooseOperator.setOnClickListener(v -> {
            networkLayout.startAnimation(slideInAnim);
            networkLayout.setVisibility(View.VISIBLE);

            airtelLayout.setBackgroundResource(R.color.white);
            bsnlLayout.setBackgroundResource(R.color.white);
            mtnlLayout.setBackgroundResource(R.color.white);
            viLayout.setBackgroundResource(R.color.white);
            jioLayout.setBackgroundResource(R.color.white);
        });
        //to load the fragment in the fragment container frame layout
        loadFragment(new RechargeFragment());
    }

    //to initialize the UI
    public void initUI(){
        txtTitle = findViewById(R.id.txt_action_bar_title);
        txtNext = findViewById(R.id.next_btn_txt);
        txtChooseOperator = findViewById(R.id.choose_operator_txt);
        slideInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        networkLayout = findViewById(R.id.networks_layout);

        airtelLayout = findViewById(R.id.airtel_layout);
        bsnlLayout = findViewById(R.id.bsnl_layout);
        mtnlLayout = findViewById(R.id.mtnl_layout);
        viLayout = findViewById(R.id.vi_layout);
        jioLayout = findViewById(R.id.jio_layout);

        airtelLayout.setOnClickListener(this);
        bsnlLayout.setOnClickListener(this);
        mtnlLayout.setOnClickListener(this);
        viLayout.setOnClickListener(this);
        jioLayout.setOnClickListener(this);

        txtTitle.setText("Recharge");
    }

    //To load the fragment in the fragment container frame layout
    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container_utility, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.airtel_layout:
                airtelLayout.setBackgroundResource(R.drawable.button_backgroud);
                txtChooseOperator.setText("Airtel");
                break;
            case R.id.bsnl_layout:
                bsnlLayout.setBackgroundResource(R.drawable.button_backgroud);
                txtChooseOperator.setText("BSNL");
                break;
            case R.id.mtnl_layout:
                mtnlLayout.setBackgroundResource(R.drawable.button_backgroud);
                txtChooseOperator.setText("MTNL");
                break;
            case R.id.vi_layout:
                viLayout.setBackgroundResource(R.drawable.button_backgroud);
                txtChooseOperator.setText("Vodafone Idea");
                break;
            case R.id.jio_layout:
                jioLayout.setBackgroundResource(R.drawable.button_backgroud);
                txtChooseOperator.setText("Reliance Jio");
                break;
        }

        networkLayout.startAnimation(fadeInAnim);
        networkLayout.setVisibility(View.GONE);
    }
}