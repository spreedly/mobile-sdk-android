package com.spreedly.express;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.spreedly.client.models.BankAccountInfo;
import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.PaymentMethodInfo;

import java.io.Serializable;
import java.util.List;

public class PaymentOptions implements Serializable {
    boolean showZipcode;
    @Nullable
    String merchantText;
    @Nullable
    PaymentType paymentType;
    @Nullable
    String buttonText;
    @Nullable
    List<PaymentMethodItem> paymentMethodItemList;
    @Nullable
    @LayoutRes
    Integer header;
    @Nullable
    @LayoutRes
    Integer footer;
    @Nullable
    PaymentMethodInfo paymentMethodDefaults;
    @Nullable
    CreditCardInfo creditCardDefaults;
    @Nullable
    BankAccountInfo bankAccountDefaults;

    public void setHeader(@LayoutRes int layout) {
        header = layout;
    }

    public void setFooter(@LayoutRes int layout) {
        footer = layout;
    }

    public void setMerchantText(@Nullable String merchantText) {
        this.merchantText = merchantText;
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

    public void setPaymentMethodItemList(@Nullable List<PaymentMethodItem> paymentMethodItemList) {
        this.paymentMethodItemList = paymentMethodItemList;
    }

    public void setPaymentMethodDefaults(@Nullable PaymentMethodInfo paymentMethodDefaults) {
        this.paymentMethodDefaults = paymentMethodDefaults;
    }

    public void setCreditCardDefaults(@Nullable CreditCardInfo creditCardDefaults) {
        this.creditCardDefaults = creditCardDefaults;
    }

    public void setBankAccountDefaults(@Nullable BankAccountInfo bankAccountDefaults) {
        this.bankAccountDefaults = bankAccountDefaults;
    }
}
