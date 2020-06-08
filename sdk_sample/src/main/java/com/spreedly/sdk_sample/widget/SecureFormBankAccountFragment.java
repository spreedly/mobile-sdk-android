package com.spreedly.sdk_sample.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.spreedly.client.models.enums.BankAccountType;
import com.spreedly.sdk_sample.R;

public class SecureFormBankAccountFragment extends Fragment {

    private SecureFormBankAccountViewModel mViewModel;

    public static SecureFormBankAccountFragment newInstance() {
        return new SecureFormBankAccountFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.secure_form_bank_account_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SecureFormBankAccountViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.layout = getView().findViewById(R.id.bank_account_form);
        mViewModel.spinner = getView().findViewById(R.id.spreedly_ba_account_type);
        mViewModel.error = getView().findViewById(R.id.error);
        mViewModel.token = getView().findViewById(R.id.token);
        ArrayAdapter<BankAccountType> adapter = new ArrayAdapter<BankAccountType>(getContext(), android.R.layout.simple_list_item_1, BankAccountType.values());
        mViewModel.spinner.setAdapter(adapter);
        mViewModel.layout.setSpreedlyClient("XsQXqPtrgCOnpexSwyhzN9ngr2c", "ghEGueczUT4BhJv54K24G6B4Oy9yWaM5R4dR2yt5gRsx3xnwbZE0OZ0mRg2zyI5g", true);
        getView().<Button>findViewById(R.id.spreedly_cc_submit).setOnClickListener(b -> mViewModel.submitBankAccount());
    }

}
