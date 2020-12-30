package com.spreedly.sdk_sample.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.spreedly.client.SpreedlyClient;
import com.spreedly.sdk_sample.R;
import com.spreedly.threedssecure.SpreedlyThreeDS;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
        mViewModel = new ViewModelProvider(this).get(ThreeDSViewModel.class);
        mViewModel.errorView = getView().findViewById(R.id.error);
        mViewModel.token = getView().findViewById(R.id.token);
        mViewModel.amount = getView().findViewById(R.id.spreedly_3ds_amount);
        mViewModel.secureCreditCardField = getView().findViewById(R.id.spreedly_credit_card_number);
        mViewModel.challengeType = getView().findViewById(R.id.challenge_type);
        mViewModel.challengeType.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, new String[]{
                "Standard", "Single", "Multi", "OOB", "HTML"
        }));
        mViewModel.handler = new Handler(Looper.getMainLooper());

        try {
            mViewModel.client = SpreedlyClient.newInstance("A54wvT9knP8Sc6ati68epUcq72l", "0f0Cpq17bb5mAAUxtx0QmY2mXyHnEk26uYTrPttn4PIMKZC4zdTJVJSk4YHbe1Ij", true);
            mViewModel.spreedlyThreeDS = new SpreedlyThreeDS(Objects.requireNonNull(getContext()), Objects.requireNonNull(getActivity()), null, true);
        } catch (Exception e) {
            Log.e("Spreedly", "error init", e);
            mViewModel.errorView.setText(e.getMessage());
        }
        getView().<Button>findViewById(R.id.spreedly_cc_submit).setOnClickListener(b -> mViewModel.submitCreditCard());
    }
}