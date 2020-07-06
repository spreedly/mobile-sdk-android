package com.spreedly.express;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.lifecycle.ViewModel;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.spreedly.securewidgets.SecureCreditCardField;
import com.spreedly.securewidgets.SecureExpirationDate;
import com.spreedly.securewidgets.SecureFormLayout;
import com.spreedly.securewidgets.SecureTextField;

public class ExpressFragmentViewModel extends ViewModel {
    Button submitButton;
    CardSlider cardSlider;
    // TODO: Implement the ViewModel
    LinearLayout layout;
    Button addCardButton;
    Button bankButton;
    Button cardButton;

    LinearLayout paymentSelectorLayout;

    SecureFormLayout secureFormLayout;

    MaterialTextView paymentLabel;
    TextInputLayout fullNameWrapper;
    TextInputEditText fullNameContent;

    SecureCreditCardField secureCreditCardField;
    SecureTextField ccvField;
    SecureExpirationDate secureExpirationDate;

    TextInputLayout routingNumberWrapper;
    SecureTextField accountNumberField;
    TextInputEditText routingNumberContent;
    Spinner accountType;
    Spinner holderType;
}
