package com.spreedly.sdk_sample.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.spreedly.sdk_sample.R;
import com.spreedly.sdk_sample.simple.SimplePagerAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import io.reactivex.rxjava3.annotations.NonNull;

public class SimpleExamplesFragment extends Fragment {

    @NonNull public static SimpleExamplesFragment newInstance() {
        return new SimpleExamplesFragment();
    }

    @Override
    @Nullable public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_core_api, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SimplePagerAdapter sectionsPagerAdapter = new SimplePagerAdapter(getChildFragmentManager());
        ViewPager viewPager = getView().findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = getView().findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }
}
