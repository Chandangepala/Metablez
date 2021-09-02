package com.makemywallet.ui.home.adapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.makemywallet.ui.allfragment.BankingFragment;
import com.makemywallet.ui.allfragment.OtherFragment;
import com.makemywallet.ui.allfragment.RechargeFragment;

public class CustomAdapter extends FragmentPagerAdapter {
    private static final String TAG = CustomAdapter.class.getSimpleName();
    private static final int FRAGMENT_COUNT = 3;

    public CustomAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RechargeFragment();
            case 1:
                return new BankingFragment();
            case 2:
                return new OtherFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "RECHARGE";
            case 1:
                return "BANKING";
            case 2:
                return "OTHERS";
        }
        return null;
    }
}




