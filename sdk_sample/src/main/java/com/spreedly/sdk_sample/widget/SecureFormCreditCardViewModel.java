package com.spreedly.sdk_sample.widget;

import androidx.lifecycle.ViewModel;

import com.spreedly.securewidgets.SecureFormLayout;

public class SecureFormCreditCardViewModel extends ViewModel {
    SecureFormLayout layout;
    // TODO: Implement the ViewModel
    public void submitCreditCard() {
        layout.setSpreedlyClient("XsQXqPtrgCOnpexSwyhzN9ngr2c", "ghEGueczUT4BhJv54K24G6B4Oy9yWaM5R4dR2yt5gRsx3xnwbZE0OZ0mRg2zyI5g", true);
        layout.createCreditCardPaymentMethod();
    }
}
