package com.spreedly.sdk_sample.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.spreedly.sdk_sample.R;

public class SecureFormCreditCardFragment extends Fragment {

    private SecureFormCreditCardViewModel mViewModel;

    public static SecureFormCreditCardFragment newInstance() {
        return new SecureFormCreditCardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.secure_form_credit_card_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SecureFormCreditCardViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.layout = getView().findViewById(R.id.credit_card_form);
        getView().<Button>findViewById(R.id.cc_submit).setOnClickListener(b -> mViewModel.submitCreditCard());
    }

}
