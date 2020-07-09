package com.spreedly.express;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.spreedly.client.models.Address;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;

import java.util.List;

import io.reactivex.rxjava3.core.SingleObserver;

public class PaymentOptions {
    public SingleObserver<StoredCard> savedCardCallback;
    boolean showZipcode;
    @Nullable
    Address shippingAddress;
    @Nullable
    Address billingAddress;
    @Nullable
    String merchantTitle;
    @Nullable
    String merchantText;
    @Nullable
    PaymentType paymentType;
    @Nullable
    String buttonText;
    @Nullable
    List<StoredCard> storedCardList;
    @Nullable
    int merchantIcon;
    @NonNull
    SingleObserver<TransactionResult<PaymentMethodResult>> submitCallback;

    public void setMerchantTitle(@Nullable String merchantTitle) {
        this.merchantTitle = merchantTitle;
    }

    public void setMerchantText(@Nullable String merchantText) {
        this.merchantText = merchantText;
    }

    public void setShippingAddress(@Nullable Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setBillingAddress(@Nullable Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setPaymentType(@Nullable PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public void setButtonText(@Nullable String buttonText) {
        this.buttonText = buttonText;
    }

    public void setShowZipcode(@Nullable boolean showZipcode) {
        this.showZipcode = showZipcode;
    }

    public void setStoredCardList(@Nullable List<StoredCard> storedCardList) {
        this.storedCardList = storedCardList;
    }

    public void setMerchantIcon(@Nullable int merchantIcon) {
        this.merchantIcon = merchantIcon;
    }

    public void setSubmitCallback(@NonNull SingleObserver<TransactionResult<PaymentMethodResult>> submitCallback) {
        this.submitCallback = submitCallback;
    }

    public void setSavedCardCallback(SingleObserver<StoredCard> savedCardCallback) {
        this.savedCardCallback = savedCardCallback;
    }
}
