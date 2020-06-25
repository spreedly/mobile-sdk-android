package com.spreedly.sdk_sample.express;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;
import com.spreedly.express.OnCustomEventListener;
import com.spreedly.sdk_sample.R;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

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
        mViewModel.token = getView().findViewById(R.id.token);
        mViewModel.expressView = getView().findViewById(R.id.express_credit_card_form);
        mViewModel.expressView.setClientInfo("XsQXqPtrgCOnpexSwyhzN9ngr2c", "ghEGueczUT4BhJv54K24G6B4Oy9yWaM5R4dR2yt5gRsx3xnwbZE0OZ0mRg2zyI5g", true);
        mViewModel.expressView.setCustomEventListener(new OnCustomEventListener() {
            @Override
            public void onEvent() {
                Log.i("Spreedly", "On Event");
                mViewModel.expressView.result.subscribeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<TransactionResult<PaymentMethodResult>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        Log.i("Spreedly", "On Subscribe");
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull TransactionResult<PaymentMethodResult> paymentMethodResultTransactionResult) {
                        Log.i("Spreedly", "Created Credit Card");
                        String result = "Token: " + paymentMethodResultTransactionResult.result.token;
                        try {
                            mViewModel.token.setText(result);
                        } catch (Exception e) {
                            mViewModel.token.setText(R.string.generic_error);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        mViewModel.token.setText(e.getMessage());
                        Log.e("Spreedly", e.getMessage());
                    }
                });
            }
        });
    }

}
