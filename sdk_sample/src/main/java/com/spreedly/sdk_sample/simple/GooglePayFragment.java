package com.spreedly.sdk_sample.simple;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.spreedly.sdk_sample.R;

public class GooglePayFragment extends BillingFragmentBase {

    private GooglePayFragmentViewModel mViewModel;

    public static GooglePayFragment newInstance() {
        return new GooglePayFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.google_pay_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GooglePayFragmentViewModel.class);

        bindEditText(getView().findViewById(R.id.name), mViewModel.name);

        bindTextView(getView().findViewById(R.id.token), mViewModel.token);
        bindTextView(getView().findViewById(R.id.error), mViewModel.error);

        mViewModel.inProgress.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean inProgress) {
                getView().findViewById(R.id.name).setEnabled(!inProgress);
            }
        });

        getView().<Button>findViewById(R.id.submit).setOnClickListener(b -> mViewModel.create());
    }

}
