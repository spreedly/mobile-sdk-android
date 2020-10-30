package com.spreedly.sdk_sample.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.spreedly.client.SpreedlyClient;
import com.spreedly.sdk_sample.R;
import com.spreedly.threedssecure.SpreedlyThreeDS;

import java.util.Objects;

import io.reactivex.rxjava3.annotations.NonNull;

public class ThreeDSFragment extends Fragment {
    private ThreeDSViewModel mViewModel;

    @NonNull
    public static ThreeDSFragment newInstance() {
        return new ThreeDSFragment();
    }

    @Override
    @Nullable
    public View onCreateView(@androidx.annotation.NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thee_d_s, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            mViewModel = new ViewModelProvider(this).get(ThreeDSViewModel.class);
            mViewModel.client = SpreedlyClient.newInstance("A54wvT9knP8Sc6ati68epUcq72l", "0f0Cpq17bb5mAAUxtx0QmY2mXyHnEk26uYTrPttn4PIMKZC4zdTJVJSk4YHbe1Ij", true);
            mViewModel.spreedlyThreeDS = new SpreedlyThreeDS(Objects.requireNonNull(getContext()), Objects.requireNonNull(getActivity()));
        } catch (Exception e) {
            Log.i("Spreedly", e.getMessage());
        }
        mViewModel.errorView = getView().findViewById(R.id.error);
        mViewModel.token = getView().findViewById(R.id.token);
        mViewModel.amount = getView().findViewById(R.id.spreedly_3ds_amount);
        mViewModel.secureCreditCardField = getView().findViewById(R.id.spreedly_credit_card_number);
        getView().<Button>findViewById(R.id.spreedly_cc_submit).setOnClickListener(b -> mViewModel.submitCreditCard());
    }
}