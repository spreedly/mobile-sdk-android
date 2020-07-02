package com.spreedly.express;

import androidx.annotation.Nullable;

import com.spreedly.client.models.Address;

public class PaymentOptions {
    public boolean showZipcode;
    @Nullable
    public Address shippingAddress;
    @Nullable
    public Address billingAddress;
    @Nullable
    public String merchantTitle;
    @Nullable
    public String merchantText;
    @Nullable
    public PaymentType paymentType;
    @Nullable
    public String buttonText;

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
}
