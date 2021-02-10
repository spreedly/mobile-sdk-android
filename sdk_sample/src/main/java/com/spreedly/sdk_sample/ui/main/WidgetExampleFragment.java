package com.spreedly.sdk_sample.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.spreedly.sdk_sample.R;
import com.spreedly.sdk_sample.widget.WidgetPagerAdapter;

public class WidgetExampleFragment extends Fragment {


    @NonNull
    public static WidgetExampleFragment newInstance() {
        return new WidgetExampleFragment();
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.widget_example_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        WidgetPagerAdapter widgetPagerAdapter = new WidgetPagerAdapter(getChildFragmentManager());
        ViewPager viewPager = getView().findViewById(R.id.view_pager);
        viewPager.setAdapter(widgetPagerAdapter);
        TabLayout tabs = getView().findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

}
