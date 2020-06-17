package com.spreedly.sdk_sample.express;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.spreedly.sdk_sample.R;

public class ExpressCreditCard extends Fragment {

    private ExpressCreditCardViewModel mViewModel;

    public static ExpressCreditCard newInstance() {
        return new ExpressCreditCard();
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.express_credit_card_fragment, container, false);
    }

    @Override
    @Nullable
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ExpressCreditCardViewModel.class);
        // TODO: Use the ViewModel
    }

}
