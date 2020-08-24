package com.spreedly.securewidgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.material.textfield.TextInputLayout;
import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.Address;
import com.spreedly.client.models.BankAccountInfo;
import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.PaymentMethodInfo;
import com.spreedly.client.models.enums.AccountHolderType;
import com.spreedly.client.models.enums.AccountType;
import com.spreedly.client.models.enums.CardBrand;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.SpreedlyError;
import com.spreedly.client.models.results.TransactionResult;

import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * TODO: document your custom view class.
 */
public class SecureFormLayout extends LinearLayout {
    @NonNull
    SpreedlyClient spreedlyClient;
    @Nullable
    public AccountTypeHelper accountTypeHelper;

    @Nullable TextInputLayout fullNameInput;
    @Nullable TextInputLayout firstNameInput;
    @Nullable TextInputLayout lastNameInput;
    @Nullable TextInputLayout company;

    @Nullable SecureCreditCardField creditCardNumberField;
    @Nullable SecureTextField cvvField;
    @Nullable SecureExpirationDate expirationField;
    @Nullable TextInputLayout monthInput;
    @Nullable TextInputLayout yearInput;

    @Nullable TextInputLayout address1Input;
    @Nullable TextInputLayout address2Input;
    @Nullable TextInputLayout cityInput;
    @Nullable TextInputLayout stateInput;
    @Nullable TextInputLayout phoneInput;
    @Nullable TextInputLayout zipInput;
    @Nullable TextInputLayout shippingAddress1Input;
    @Nullable TextInputLayout shippingAddress2Input;
    @Nullable TextInputLayout shippingCityInput;
    @Nullable TextInputLayout shippingStateInput;
    @Nullable Spinner shippingCountrySpinner;
    @Nullable TextInputLayout shippingPhoneInput;
    @Nullable TextInputLayout shippingZipInput;

    @Nullable TextInputLayout emailInput;

    @Nullable SecureTextField bankAccountNumberField;
    @Nullable TextInputLayout routingNumberInput;

    @Nullable TextInputLayout accountTypeInput;
    @Nullable Spinner accountTypeSpinner;
    @Nullable RadioGroup accountTypeRadio;
    @Nullable TextInputLayout accountHolderTypeInput;
    @Nullable Spinner accountHolderTypeSpinner;
    @Nullable RadioGroup accountHolderTypeRadio;

    @Nullable TextView errorView;
    @Nullable PaymentMethodInfo defaultPaymentMethodInfo;
    @Nullable CreditCardInfo defaultCreditCardInfo;
    @Nullable BankAccountInfo defaultBankAccountInfo;

    CheckBox sameAddress;


    public SecureFormLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        accountTypeHelper = new AccountTypeHelper(context);
    }

    public SecureFormLayout(@NonNull Context context) {
        super(context);
        accountTypeHelper = new AccountTypeHelper(context);
    }

    public void setSpreedlyClient(@NonNull String envKey, @NonNull String envSecret, boolean test) {
        spreedlyClient = SpreedlyClient.newInstance(envKey, envSecret, test);
    }

    public void setSpreedlyClient(@NonNull SpreedlyClient client) {
        spreedlyClient = client;
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
        boolean hasError = validateNames();
        hasError = validateAddress(hasError);
        if (creditCardNumberField == null || creditCardNumberField.getText().detectCardType() == CardBrand.error) {
            creditCardNumberField.setError(getContext().getString(R.string.error_bad_card_number));
            hasError = true;
        }
        if (expirationField != null && (expirationField.getYear() == 0 || expirationField.getMonth() == 0)) {
            expirationField.setError(getContext().getString(R.string.error_bad_date));
            hasError = true;
        }
        if (hasError) {
            return new Single<TransactionResult<PaymentMethodResult>>() {
                @Override
                protected void subscribeActual(@io.reactivex.rxjava3.annotations.NonNull SingleObserver<? super TransactionResult<PaymentMethodResult>> observer) {

                }
            };
        }
        CreditCardInfo info = createCreditCardInfo();
        Single<TransactionResult<PaymentMethodResult>> result = spreedlyClient.createCreditCardPaymentMethod(info);
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
        boolean hasError = validateNames();
        hasError = validateAddress(hasError);
        if (bankAccountNumberField != null) {
            if (bankAccountNumberField.getText().length == 0) {
                hasError = true;
                bankAccountNumberField.setError(getContext().getString(R.string.error_blank_account_number));
            } else if (!bankAccountNumberField.getText().isNumber()) {
                hasError = true;
                bankAccountNumberField.setError(getContext().getString(R.string.error_bad_account_number));
            }
        }
        if (routingNumberInput != null) {
            if (getString(routingNumberInput).length() == 0) {
                hasError = true;
                routingNumberInput.setError(getContext().getString(R.string.error_blank_routing_number));
            } else {
                try {
                    Double.parseDouble(getString(routingNumberInput));
                } catch (Exception e) {
                    hasError = true;
                    routingNumberInput.setError(getContext().getString(R.string.error_bad_routing_number));
                }
            }
        }
        if (hasError) {
            return new Single<TransactionResult<PaymentMethodResult>>() {
                @Override
                protected void subscribeActual(@io.reactivex.rxjava3.annotations.NonNull SingleObserver<? super TransactionResult<PaymentMethodResult>> observer) {

                }
            };
        }
        BankAccountInfo info = createBankAccountInfo();
        Single<TransactionResult<PaymentMethodResult>> result = spreedlyClient.createBankPaymentMethod(info);
        return result.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map((transaction) -> {
            if (!transaction.succeeded) {
                handleErrors(transaction.errors);
            }
            return transaction;
        });
    }


    public void handleErrors(@NonNull List<SpreedlyError> errors) {
        try {
            for (int i = 0; i < errors.size(); i++) {
                SpreedlyError error = errors.get(i);
                WidgetError widgetError = WidgetError.valueOf(error.attribute.toUpperCase(Locale.US));
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
        WidgetError widgetError = WidgetError.valueOf(name.toUpperCase(Locale.US));
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
        if (cvvField != null)
            cvvField.setError(null);
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
        if (phoneInput != null)
            phoneInput.setError(null);


        if (shippingAddress1Input != null)
            shippingAddress1Input.setError(null);
        if (shippingAddress2Input != null)
            shippingAddress2Input.setError(null);
        if (shippingCityInput != null)
            shippingCityInput.setError(null);
        if (shippingStateInput != null)
            shippingStateInput.setError(null);
        if (shippingZipInput != null)
            shippingZipInput.setError(null);
        if (shippingPhoneInput != null)
            shippingPhoneInput.setError(null);
    }

    private boolean validateNames() {
        boolean hasError = false;
        if (fullNameInput != null && getString(fullNameInput).length() == 0) {
            fullNameInput.setError(getContext().getString(R.string.error_blank_first_name));
            hasError = true;
        }
        if (firstNameInput != null && getString(firstNameInput).length() == 0) {
            firstNameInput.setError(getContext().getString(R.string.error_blank_first_name));
            hasError = true;
        }
        if (lastNameInput != null && getString(lastNameInput).length() == 0) {
            lastNameInput.setError(getContext().getString(R.string.error_blank_last_name));
            hasError = true;
        }
        return hasError;
    }

    private boolean validateAddress(boolean hasError) {
        if (address1Input != null && getString(address1Input).length() == 0) {
            hasError = true;
            address1Input.setError(getContext().getString(R.string.error_blank_address));
        }
        if (cityInput != null && getString(cityInput).length() == 0) {
            hasError = true;
            cityInput.setError(getContext().getString(R.string.error_blank_city));
        }
        if (stateInput != null && getString(stateInput).length() == 0) {
            hasError = true;
            stateInput.setError(getContext().getString(R.string.error_blank_state));
        }
        if (zipInput != null && getString(zipInput).length() == 0) {
            hasError = true;
            zipInput.setError(getContext().getString(R.string.error_blank_zipcode));
        }
        if (shippingAddress1Input != null && getString(shippingAddress1Input).length() == 0) {
            hasError = true;
            shippingAddress1Input.setError(getContext().getString(R.string.error_blank_address));
        }
        if (shippingCityInput != null && getString(shippingCityInput).length() == 0) {
            hasError = true;
            shippingCityInput.setError(getContext().getString(R.string.error_blank_city));
        }
        if (shippingStateInput != null && getString(shippingStateInput).length() == 0) {
            hasError = true;
            shippingStateInput.setError(getContext().getString(R.string.error_blank_state));
        }
        if (shippingZipInput != null && getString(shippingZipInput).length() == 0) {
            hasError = true;
            shippingZipInput.setError(getContext().getString(R.string.error_blank_zipcode));
        }
        return hasError;
    }

    private void init() {
        creditCardNumberField = findViewById(R.id.spreedly_credit_card_number);
        cvvField = findViewById(R.id.spreedly_cvv);
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
        phoneInput = findViewById(R.id.spreedly_phone_number);
        shippingAddress1Input = findViewById(R.id.spreedly_shipping_address1);
        shippingAddress2Input = findViewById(R.id.spreedly_shipping_address2);
        shippingCityInput = findViewById(R.id.spreedly_shipping_city);
        shippingStateInput = findViewById(R.id.spreedly_shipping_state);
        shippingZipInput = findViewById(R.id.spreedly_shipping_zip);
        shippingCountrySpinner = findViewById(R.id.spreedly_shipping_country);
        shippingPhoneInput = findViewById(R.id.spreedly_shipping_phone_number);
        emailInput = findViewById(R.id.spreedly_email);
        bankAccountNumberField = findViewById(R.id.spreedly_ba_account_number);
        routingNumberInput = findViewById(R.id.spreedly_ba_routing_number);


        View accountTypeView = findViewById(R.id.spreedly_ba_account_type);
        if (accountTypeView != null) {
            if (accountTypeView.getClass() == TextInputLayout.class) {
                accountTypeInput = (TextInputLayout) accountTypeView;
            } else if (accountTypeView.getClass() == Spinner.class || accountTypeView.getClass() == AppCompatSpinner.class) {
                accountTypeSpinner = (Spinner) accountTypeView;
            } else {
                accountTypeRadio = (RadioGroup) accountTypeView;
            }
        }
        View accountHolderTypeView = findViewById(R.id.spreedly_ba_account_holder_type);
        if (accountHolderTypeView != null) {
            if (accountHolderTypeView.getClass() == TextInputLayout.class) {
                accountHolderTypeInput = (TextInputLayout) accountHolderTypeView;
            } else if (accountHolderTypeView.getClass() == Spinner.class) {
                accountHolderTypeSpinner = (Spinner) accountHolderTypeView;
            } else {
                accountHolderTypeRadio = (RadioGroup) accountHolderTypeView;
            }
        }
        errorView = findViewById(R.id.spreedly_generic_error);

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

    private CreditCardInfo createCreditCardInfo() {
        CreditCardInfo info = new CreditCardInfo();
        if (defaultCreditCardInfo != null) {
            info = defaultCreditCardInfo;
        } else if (defaultPaymentMethodInfo != null) {
            info = (CreditCardInfo) defaultPaymentMethodInfo;
        }
        addAddress(info);
        addShippingAddress(info);
        addName(info);
        if (creditCardNumberField != null) {
            info.number = creditCardNumberField.getText();
        }
        if (cvvField != null) {
            info.verificationValue = cvvField.getText();
        }
        if (expirationField != null) {
            info.year = expirationField.getYear();
            info.month = expirationField.getMonth();
        }
        return info;
    }

    private BankAccountInfo createBankAccountInfo() {
        BankAccountInfo info = new BankAccountInfo();
        if (defaultBankAccountInfo != null) {
            info = defaultBankAccountInfo;
        } else if (defaultPaymentMethodInfo != null) {
            info = (BankAccountInfo) defaultPaymentMethodInfo;
        }
        addAddress(info);
        addShippingAddress(info);
        addName(info);
        if (bankAccountNumberField != null) {
            info.accountNumber = bankAccountNumberField.getText();
        }
        if (routingNumberInput != null) {
            info.routingNumber = getString(routingNumberInput);
        }
        if (accountTypeSpinner != null) {
            info.accountType = AccountType.valueOf(accountTypeSpinner.getSelectedItem().toString());
        } else if (accountTypeRadio != null) {
            info.accountType = accountTypeHelper.getAccountType(((RadioButton) findViewById(accountTypeRadio.getCheckedRadioButtonId())).getText().toString());
        } else if (accountTypeInput != null) {
            info.accountType = AccountType.valueOf(getString(accountTypeInput));
        }
        if (accountHolderTypeSpinner != null) {
            info.accountHolderType = AccountHolderType.valueOf(accountHolderTypeSpinner.getSelectedItem().toString());
        } else if (accountHolderTypeRadio != null) {
            info.accountHolderType = accountTypeHelper.getAccountHolderType(((RadioButton) findViewById(accountHolderTypeRadio.getCheckedRadioButtonId())).getText().toString());
        } else if (accountHolderTypeInput != null) {
            info.accountHolderType = AccountHolderType.valueOf(getString(accountHolderTypeInput));
        }
        return info;
    }

    private void addAddress(PaymentMethodInfo info) {
        Address address = new Address();
        if (address1Input != null) {
            address.address1 = getString(address1Input);
        }
        if (address2Input != null) {
            address.address2 = getString(address2Input);
        }
        if (cityInput != null) {
            address.city = getString(cityInput);
        }
        if (stateInput != null) {
            address.state = getString(stateInput);
        }
        if (zipInput != null) {
            address.zip = getString(zipInput);
        }
        info.address = address;
    }


    private void addShippingAddress(PaymentMethodInfo info) {
        if (getBoolean(sameAddress)) {
            info.shippingAddress = info.address;
        } else {
            Address address = new Address();
            if (shippingAddress1Input != null) {
                address.address1 = getString(shippingAddress1Input);
            }
            if (shippingAddress2Input != null) {
                address.address2 = getString(shippingAddress2Input);
            }
            if (shippingCityInput != null) {
                address.city = getString(shippingCityInput);
            }
            if (shippingStateInput != null) {
                address.state = getString(shippingStateInput);
            }
            if (shippingZipInput != null) {
                address.zip = getString(shippingZipInput);
            }
            info.shippingAddress = address;
        }
    }

    private void addName(PaymentMethodInfo info) {
        if (firstNameInput != null) {
            info.firstName = getString(firstNameInput);
        }
        if (lastNameInput != null) {
            info.lastName = getString(lastNameInput);
        }
        if (fullNameInput != null) {
            info.fullName = getString(fullNameInput);
        }
    }
}
