package com.spreedly.sdk_sample.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class WidgetPagerAdapter extends FragmentPagerAdapter {
    public WidgetPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Sample";
        }
        throw new IndexOutOfBoundsException();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SecureFormCreditCardFragment.newInstance();
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getCount() {
        return 1;
    }
}
