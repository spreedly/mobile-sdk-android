package com.spreedly.securewidgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;
import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.Address;
import com.spreedly.client.models.BankAccountInfo;
import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.PaymentMethodMeta;
import com.spreedly.client.models.enums.BankAccountType;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.SpreedlyError;
import com.spreedly.client.models.results.TransactionResult;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * TODO: document your custom view class.
 */
public class SecureFormLayout extends LinearLayout {
    @NonNull
    SpreedlyClient spreedlyClient;

    @Nullable TextInputLayout fullNameInput;
    @Nullable TextInputLayout firstNameInput;
    @Nullable TextInputLayout lastNameInput;
    @Nullable TextInputLayout company;

    @Nullable SecureCreditCardField creditCardNumberField;
    @Nullable SecureTextField ccvField;
    @Nullable SecureExpirationDate expirationField;
    @Nullable TextInputLayout monthInput;
    @Nullable TextInputLayout yearInput;

    @Nullable TextInputLayout address1Input;
    @Nullable TextInputLayout address2Input;
    @Nullable TextInputLayout cityInput;
    @Nullable TextInputLayout stateInput;
    @Nullable Spinner stateSpinner;
    @Nullable TextInputLayout countryInput;
    @Nullable Spinner countrySpinner;
    @Nullable TextInputLayout phoneInput;
    @Nullable TextInputLayout zipInput;
    @Nullable TextInputLayout shippingAddress1Input;
    @Nullable TextInputLayout shippingAddress2Input;
    @Nullable TextInputLayout shippingCityInput;
    @Nullable TextInputLayout shippingStateInput;
    @Nullable Spinner shippingStateSpinner;
    @Nullable TextInputLayout shippingCountryInput;
    @Nullable Spinner shippingCountrySpinner;
    @Nullable TextInputLayout shippingPhoneInput;
    @Nullable TextInputLayout shippingZipInput;

    @Nullable TextInputLayout emailInput;

    @Nullable SecureTextField bankAccountNumberField;
    @Nullable TextInputLayout routingNumberInput;

    @Nullable TextInputLayout bankAccountTypeInput;
    @Nullable Spinner bankAccountTypeSpinner;
    @Nullable TextInputLayout accountHolderTypeInput;
    @Nullable Spinner accountHolderTypeSpinner;

    @Nullable TextView errorView;

    CheckBox sameAddress;


    public SecureFormLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSpreedlyClient(@NonNull String envKey, @NonNull String envSecret, boolean test) {
        spreedlyClient = SpreedlyClient.newInstance(envKey, envSecret, test);
    }

    @Nullable
    public SpreedlyClient getClient() {
        return spreedlyClient;
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    @NonNull
    public Single<TransactionResult<PaymentMethodResult>> createCreditCardPaymentMethod() {
        Log.i("Spreedly", "createCreditCardPaymentMethod firing");
        resetCardErrors();
        resetGenericErrors();
        CreditCardInfo info;
        if (fullNameInput != null) {
            info = new CreditCardInfo(getString(fullNameInput), creditCardNumberField.getText(), ccvField.getText(), expirationField.getYear(), expirationField.getMonth());
        } else {
            info = new CreditCardInfo(getString(firstNameInput), getString(lastNameInput), creditCardNumberField.getText(), ccvField.getText(), expirationField.getYear(), expirationField.getMonth());
        }
        addAddress(info);
        addShippingAddress(info);
        Single<TransactionResult<PaymentMethodResult>> result = spreedlyClient.createCreditCardPaymentMethod(info, getString(emailInput), null);
        return result.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map((transaction) -> {
            if (!transaction.succeeded) {
                handleErrors(transaction.errors);
            }
            return transaction;
        });
    }

    @NonNull
    public Single<TransactionResult<PaymentMethodResult>> createBankAccountPaymentMethod() {
        Log.i("Spreedly", "createCreditCardPaymentMethod firing");
        resetBankErrors();
        resetGenericErrors();
        BankAccountInfo info;
        Object accountType = bankAccountTypeSpinner.getSelectedItem();
        if (fullNameInput != null) {
            info = new BankAccountInfo(getString(fullNameInput), getString(routingNumberInput), bankAccountNumberField.getText(), BankAccountType.valueOf(accountType.toString()));
        } else {
            info = new BankAccountInfo(getString(firstNameInput), getString(lastNameInput), getString(routingNumberInput), bankAccountNumberField.getText(), BankAccountType.valueOf(accountType.toString()));
        }
        addAddress(info);
        addShippingAddress(info);
        if (accountHolderTypeSpinner != null) {
            info.bankAccountHolderType = accountHolderTypeSpinner.getSelectedItem().toString();
        }
        Single<TransactionResult<PaymentMethodResult>> result = spreedlyClient.createBankPaymentMethod(info, getString(emailInput), null);
        return result.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map((transaction) -> {
            if (!transaction.succeeded) {
                handleErrors(transaction.errors);
            }
            return transaction;
        });
    }

    private void addAddress(PaymentMethodMeta info) {
        String address1 = getString(address1Input);
        String address2 = getString(address2Input);
        String city = getString(cityInput);
        String state = getString(stateInput);
        String zip = getString(zipInput);
        String phone = getString(phoneInput);
        String country = getString(countryInput);
        info.address = new Address(address1, address2, city, state, zip, country, phone);
    }

    private void addShippingAddress(PaymentMethodMeta info) {
        if (getBoolean(sameAddress)) {
            addAddress(info);
        } else {
            String address1 = getString(shippingAddress1Input);
            String address2 = getString(shippingAddress2Input);
            String city = getString(shippingCityInput);
            String state = getString(shippingStateInput);
            String zip = getString(shippingZipInput);
            String phone = getString(shippingPhoneInput);
            String country = getString(shippingCountryInput);
            info.shippingAddress = new Address(address1, address2, city, state, zip, country, phone);
        }
    }


    public void handleErrors(@NonNull List<SpreedlyError> errors) {
        try {
            for (int i = 0; i < errors.size(); i++) {
                SpreedlyError error = errors.get(i);
                WidgetError widgetError = WidgetError.valueOf(error.attribute.toUpperCase());
                if (widgetError != null) {
                    int resourceId = widgetError.getResourceId();
                    View view = findViewById(resourceId);
                    Class viewClass = view.getClass();
                    if (viewClass == TextInputLayout.class) {
                        TextInputLayout textInputLayout = (TextInputLayout) view;
                        textInputLayout.setError(error.message);
                    } else if (viewClass == SecureTextField.class) {
                        SecureTextField secureTextField = (SecureTextField) view;
                        secureTextField.setError(error.message);
                    } else if (viewClass == SecureCreditCardField.class) {
                        SecureCreditCardField secureCreditCardField = (SecureCreditCardField) view;
                        secureCreditCardField.setError(error.message);
                    } else if (viewClass == SecureExpirationDate.class) {
                        SecureExpirationDate secureExpirationDate = (SecureExpirationDate) view;
                        secureExpirationDate.setError(error.message);
                    } else if (errorView != null) {
                        errorView.setText(error.message);
                    }
                }

            }
        } catch (Exception e) {
            Log.e("Spreedly", e.getMessage());
            throw e;
        }
    }


    private int nameTold(@NonNull String name) {
        WidgetError widgetError = WidgetError.valueOf(name.toUpperCase());
        if (widgetError == null) {
            return 0;
        } else {
            return widgetError.getResourceId();
        }
    }

    private int getNumber(TextInputLayout textInputLayout) {
        try {
            return Integer.parseInt(textInputLayout.getEditText().getText().toString());
        } catch (NullPointerException | NumberFormatException e) {
            return 0;
        }
    }

    private String getString(TextInputLayout textInputLayout) {
        try {
            return textInputLayout.getEditText().getText().toString();
        } catch (NullPointerException e) {
            return "";
        }
    }

    private boolean getBoolean(CheckBox checkBox) {
        try {
            return checkBox.isChecked();
        } catch (NullPointerException e) {
            return false;
        }
    }

    private void resetCardErrors() {
        if (creditCardNumberField != null)
            creditCardNumberField.setError(null);
        if (ccvField != null)
            ccvField.setError(null);
        if (expirationField != null)
            expirationField.setError(null);
        if (monthInput != null)
            monthInput.setError(null);
        if (yearInput != null)
            yearInput.setError(null);
    }

    private void resetBankErrors() {
        if (bankAccountNumberField != null)
            bankAccountNumberField.setError(null);
        if (routingNumberInput != null)
            routingNumberInput.setError(null);
    }

    private void resetGenericErrors() {
        if (fullNameInput != null)
            fullNameInput.setError(null);
        if (firstNameInput != null)
            firstNameInput.setError(null);
        if (lastNameInput != null)
            lastNameInput.setError(null);

        if (address1Input != null)
            address1Input.setError(null);
        if (address2Input != null)
            address2Input.setError(null);
        if (cityInput != null)
            cityInput.setError(null);
        if (stateInput != null)
            stateInput.setError(null);
        if (zipInput != null)
            zipInput.setError(null);
        if (countryInput != null)
            countryInput.setError(null);
        if (phoneInput != null)
            phoneInput.setError(null);


        if (shippingAddress1Input != null)
            address1Input.setError(null);
        if (shippingAddress2Input != null)
            address2Input.setError(null);
        if (shippingCityInput != null)
            cityInput.setError(null);
        if (shippingStateInput != null)
            stateInput.setError(null);
        if (shippingZipInput != null)
            zipInput.setError(null);
        if (shippingCountryInput != null)
            countryInput.setError(null);
        if (shippingPhoneInput != null)
            phoneInput.setError(null);
    }


    private void init() {
        creditCardNumberField = findViewById(R.id.spreedly_credit_card_number);
        ccvField = findViewById(R.id.spreedly_ccv);
        fullNameInput = findViewById(R.id.spreedly_full_name);
        expirationField = findViewById(R.id.spreedly_cc_expiration_date);
        firstNameInput = findViewById(R.id.spreedly_first_name);
        lastNameInput = findViewById(R.id.spreedly_last_name);
        company = findViewById(R.id.spreedly_company);
        monthInput = findViewById(R.id.spreedly_cc_month);
        yearInput = findViewById(R.id.spreedly_cc_year);
        address1Input = findViewById(R.id.spreedly_address1);
        address2Input = findViewById(R.id.spreedly_address2);
        cityInput = findViewById(R.id.spreedly_city);
        stateInput = findViewById(R.id.spreedly_state);
        zipInput = findViewById(R.id.spreedly_zip);
        countryInput = findViewById(R.id.spreedly_country);
        phoneInput = findViewById(R.id.spreedly_phone_number);
        shippingAddress1Input = findViewById(R.id.spreedly_shipping_address1);
        shippingAddress2Input = findViewById(R.id.spreedly_shipping_address2);
        shippingCityInput = findViewById(R.id.spreedly_shipping_city);
        shippingStateInput = findViewById(R.id.spreedly_shipping_state);
        shippingZipInput = findViewById(R.id.spreedly_shipping_zip);
        shippingCountryInput = findViewById(R.id.spreedly_shipping_country);
        shippingPhoneInput = findViewById(R.id.spreedly_shipping_phone_number);
        emailInput = findViewById(R.id.spreedly_email);
        bankAccountNumberField = findViewById(R.id.spreedly_ba_account_number);
        routingNumberInput = findViewById(R.id.spreedly_ba_routing_number);
        View bankAccountTypeView = findViewById(R.id.spreedly_ba_account_type);
        if (bankAccountTypeView != null && bankAccountTypeView.getClass() == TextInputLayout.class)
            bankAccountTypeInput = (TextInputLayout) bankAccountTypeView;
        else bankAccountTypeSpinner = (Spinner) bankAccountTypeView;
        View accountHolderTypeView = findViewById(R.id.spreedly_ba_account_holder_type);
        if (accountHolderTypeView != null && accountHolderTypeView.getClass() == TextInputLayout.class)
            accountHolderTypeInput = (TextInputLayout) accountHolderTypeView;
        else accountHolderTypeSpinner = (Spinner) accountHolderTypeView;
        errorView = findViewById(R.id.spreedly_generic_error);

    }


}
