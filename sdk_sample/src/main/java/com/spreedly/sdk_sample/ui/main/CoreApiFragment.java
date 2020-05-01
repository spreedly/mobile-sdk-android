package com.spreedly.sdk_sample.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spreedly.sdk_sample.R;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoreApiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoreApiFragment extends Fragment {

    public CoreApiFragment() {
        // Required empty public constructor
    }

    public static CoreApiFragment newInstance() {
        return new CoreApiFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_core_api, container, false);
    }
}
