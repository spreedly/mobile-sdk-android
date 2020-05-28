package com.spreedly.sdk_sample.widget;

import androidx.lifecycle.ViewModel;

import com.spreedly.securewidgets.SecureFormLayout;

public class SecureFormCreditCardViewModel extends ViewModel {
    SecureFormLayout layout;
    // TODO: Implement the ViewModel
    public void submitCreditCard() {
        layout.createCreditCardPaymentMethod();
    }
}
