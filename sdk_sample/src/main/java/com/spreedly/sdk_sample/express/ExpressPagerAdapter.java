package com.spreedly.sdk_sample.express;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ExpressPagerAdapter extends FragmentPagerAdapter {
    public ExpressPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Menu";
            case 1:
                return "Bank Account";
            case 3:
                return "Credit Card";
        }
        throw new IndexOutOfBoundsException();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return PaymentMenuFragment.newInstance();
            case 1:
                return ExpressBankAccountFragment.newInstance();
            case 2:
                return ExpressCreditCardFragment.newInstance();
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
