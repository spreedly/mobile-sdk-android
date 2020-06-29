package com.spreedly.sdk_sample.express;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.spreedly.sdk_sample.R;

public class PaymentMenuFragment extends Fragment {

    private PaymentMenuViewModel mViewModel;

    public static PaymentMenuFragment newInstance() {
        return new PaymentMenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.payment_menu_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PaymentMenuViewModel.class);
        mViewModel.mainView = getView().findViewById(R.id.express_credit_card_form);
    }

}
