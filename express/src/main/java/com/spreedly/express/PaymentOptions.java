package com.spreedly.express;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.spreedly.client.models.Address;
import com.spreedly.client.models.BankAccountInfo;
import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.PaymentMethodInfo;

import java.io.Serializable;
import java.util.List;

public class PaymentOptions implements Serializable {
    boolean showZipcode;
    @Nullable
    Address shippingAddress;
    @Nullable
    Address billingAddress;
    @Nullable
    String merchantText;
    @Nullable
    PaymentType paymentType;
    @Nullable
    String buttonText;
    @Nullable
    List<StoredCard> storedCardList;
    @Nullable
    @LayoutRes
    Integer header;
    @Nullable
    @LayoutRes
    Integer footer;
    @Nullable
    PaymentMethodInfo defaultPaymentMethodInfo;
    @Nullable
    CreditCardInfo defaultCreditCardInfo;
    @Nullable
    BankAccountInfo defaultBankAccountInfo;

    public void setHeader(@LayoutRes int layout) {
        header = layout;
    }

    public void setFooter(@LayoutRes int layout) {
        footer = layout;
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

    public void setDefaultPaymentMethodInfo(@Nullable PaymentMethodInfo defaultPaymentMethodInfo) {
        this.defaultPaymentMethodInfo = defaultPaymentMethodInfo;
    }

    public void setDefaultCreditCardInfo(@Nullable CreditCardInfo defaultCreditCardInfo) {
        this.defaultCreditCardInfo = defaultCreditCardInfo;
    }

    public void setDefaultBankInfo(@Nullable BankAccountInfo defaultBankAccountInfo) {
        this.defaultBankAccountInfo = defaultBankAccountInfo;
    }
}
