package com.spreedly.sdk_sample.simple;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.spreedly.sdk_sample.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class BankAccountFragment extends BillingFragmentBase {

    private BankAccountFragmentViewModel mViewModel;

    public static BankAccountFragment newInstance() {
        return new BankAccountFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bank_account_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BankAccountFragmentViewModel.class);

        bindEditText(getView().findViewById(R.id.name), mViewModel.name);
        bindEditText(getView().findViewById(R.id.account), mViewModel.account);
        bindEditText(getView().findViewById(R.id.routing), mViewModel.routing);
        bindSpinner(getView().findViewById(R.id.type), mViewModel.type, new String[]{
                "checking", "savings"
        });
        bindTextView(getView().findViewById(R.id.token), mViewModel.token);
        bindTextView(getView().findViewById(R.id.error), mViewModel.error);

        mViewModel.inProgress.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean inProgress) {
                getView().findViewById(R.id.name).setEnabled(!inProgress);
                getView().findViewById(R.id.account).setEnabled(!inProgress);
                getView().findViewById(R.id.routing).setEnabled(!inProgress);
                getView().findViewById(R.id.type).setEnabled(!inProgress);
            }
        });

        getView().<Button>findViewById(R.id.submit).setOnClickListener(b -> mViewModel.create());
    }


}
