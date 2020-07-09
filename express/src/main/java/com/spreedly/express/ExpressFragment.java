package com.spreedly.express;

import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.enums.BankAccountHolderType;
import com.spreedly.client.models.enums.BankAccountType;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;
import com.spreedly.securewidgets.SecureCreditCardField;
import com.spreedly.securewidgets.SecureExpirationDate;
import com.spreedly.securewidgets.SecureFormLayout;
import com.spreedly.securewidgets.SecureTextField;

import io.reactivex.rxjava3.core.Single;

public class ExpressFragment extends Fragment {

    @NonNull
    private final SpreedlyClient client;
    private ExpressFragmentViewModel mViewModel;
    private PaymentOptions options;

    public ExpressFragment(@Nullable PaymentOptions options, @NonNull SpreedlyClient client) {
        super();
        this.options = options;
        this.client = client;
    }

    @NonNull
    public static ExpressFragment newInstance(@Nullable PaymentOptions options, @NonNull SpreedlyClient client) {
        return new ExpressFragment(options, client);
    }


    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.express_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ExpressFragmentViewModel.class);
        mViewModel.layout = getView().findViewById(R.id.express_fragment);
        createMenu();
        // TODO: Use the ViewModel
    }

    private void createMenu() {
        setMerchantLogo();
        mViewModel.addCardButton = new Button(this.getContext());
        mViewModel.addCardButton.setText(R.string.add_a_card);
        mViewModel.addCardButton.setOnClickListener((l) -> {
            mViewModel.layout.removeAllViews();
            createCardPayment(true);
        });
        mViewModel.bankButton = new Button(this.getContext());
        mViewModel.bankButton.setText(R.string.pay_with_bank);
        mViewModel.bankButton.setOnClickListener((l) -> {
            mViewModel.layout.removeAllViews();
            createBankPayment(true);
        });
        mViewModel.cardButton = new Button(this.getContext());
        mViewModel.cardButton.setText(R.string.pay_with_card);
        mViewModel.cardButton.setOnClickListener((l) -> {
            mViewModel.layout.removeAllViews();
            createCardPayment(true);
        });
        mViewModel.paymentSelectorLayout = new LinearLayout(getContext());
        mViewModel.paymentSelectorLayout.setOrientation(LinearLayout.VERTICAL);
        switch (options.paymentType) {
            case CARDS_ONLY:
                createSavedCardList();
                mViewModel.paymentSelectorLayout.addView(mViewModel.addCardButton);
                break;
            case BANK_ONLY:
                createBankPayment(false);
                break;
            case NEW_CARD_ONLY:
                createCardPayment(false);
                break;
            case NEW_CARD_AND_BANK:
                mViewModel.paymentSelectorLayout.addView(mViewModel.cardButton);
                mViewModel.paymentSelectorLayout.addView(mViewModel.bankButton);
                break;
            case ALL:
                createSavedCardList();
                mViewModel.paymentSelectorLayout.addView(mViewModel.addCardButton);
                mViewModel.paymentSelectorLayout.addView(mViewModel.bankButton);
                break;
        }
        mViewModel.layout.addView(mViewModel.paymentSelectorLayout);
    }

    private void createSavedCardList() {
        mViewModel.cardSlider = new CardSlider(getContext(), options.storedCardList, options.savedCardCallback);
        mViewModel.cardSlider.onFinishInflate();
        mViewModel.paymentSelectorLayout.addView(mViewModel.cardSlider);
    }

    private void createCardPayment(boolean includeBackButton) {
        setMerchantLogo();
        mViewModel.secureFormLayout = new SecureFormLayout(this.getContext());
        mViewModel.secureFormLayout.setOrientation(LinearLayout.VERTICAL);
        mViewModel.secureFormLayout.setSpreedlyClient(client);
        setLabel(getContext().getString(R.string.label_payment_method));
        setFullName();
        mViewModel.secureCreditCardField = new SecureCreditCardField(mViewModel.secureFormLayout.getContext());
        mViewModel.secureCreditCardField.setId(R.id.spreedly_credit_card_number);
        mViewModel.secureCreditCardField.onFinishInflate();
        mViewModel.ccvField = new SecureTextField(mViewModel.secureFormLayout.getContext());
        mViewModel.ccvField.setId(R.id.spreedly_ccv);
        mViewModel.ccvField.onFinishInflate();
        mViewModel.secureExpirationDate = new SecureExpirationDate(mViewModel.secureFormLayout.getContext());
        mViewModel.secureExpirationDate.onFinishInflate();
        mViewModel.secureExpirationDate.setId(R.id.spreedly_cc_expiration_date);
        addZipcode();
        mViewModel.secureFormLayout.addView(mViewModel.secureCreditCardField);
        mViewModel.secureFormLayout.addView(mViewModel.ccvField);
        mViewModel.secureFormLayout.addView(mViewModel.secureExpirationDate);
        if (includeBackButton) {
            Button backButton = new Button(getContext());
            backButton.setText(R.string.back);
            backButton.setOnClickListener((l) -> {
                mViewModel.layout.removeAllViews();
                createMenu();
            });
            mViewModel.secureFormLayout.addView(backButton);
        }
        mViewModel.submitButton = new Button(getContext());
        mViewModel.submitButton.setText(options.buttonText);
        mViewModel.submitButton.setOnClickListener(b -> submitNewCard());
        mViewModel.secureFormLayout.addView(mViewModel.submitButton);
        mViewModel.secureFormLayout.onFinishInflate();
        mViewModel.layout.addView(mViewModel.secureFormLayout);
        setMerchantText();
    }

    private void createBankPayment(boolean includeBackButton) {
        setMerchantLogo();
        mViewModel.secureFormLayout = new SecureFormLayout(this.getContext());
        mViewModel.secureFormLayout.setOrientation(LinearLayout.VERTICAL);
        mViewModel.secureFormLayout.setSpreedlyClient(client);
        setLabel(getString(R.string.label_payment_method));
        setFullName();
        mViewModel.accountNumberField = new SecureTextField(getContext());
        mViewModel.accountNumberField.setId(R.id.spreedly_ba_account_number);
        mViewModel.accountNumberField.onFinishInflate();
        mViewModel.routingNumberWrapper = new TextInputLayout(mViewModel.secureFormLayout.getContext());
        mViewModel.routingNumberWrapper.setId(R.id.spreedly_ba_routing_number);
        mViewModel.routingNumberWrapper.setHint(getString(R.string.hint_routing_number));
        mViewModel.routingNumberContent = new TextInputEditText(mViewModel.secureFormLayout.getContext());
        mViewModel.routingNumberWrapper.addView(mViewModel.routingNumberContent);
        mViewModel.accountType = new Spinner(mViewModel.secureFormLayout.getContext());
        mViewModel.accountType.setAdapter(new ArrayAdapter<>(mViewModel.secureFormLayout.getContext(), android.R.layout.simple_spinner_dropdown_item, BankAccountType.values()));
        mViewModel.accountType.setId(R.id.spreedly_ba_account_type);
        mViewModel.holderType = new Spinner(mViewModel.secureFormLayout.getContext());
        mViewModel.holderType.setAdapter(new ArrayAdapter<>(mViewModel.secureFormLayout.getContext(), android.R.layout.simple_spinner_dropdown_item, BankAccountHolderType.values()));
        mViewModel.holderType.setId(R.id.spreedly_ba_account_holder_type);
        addZipcode();
        mViewModel.secureFormLayout.addView(mViewModel.accountNumberField);
        mViewModel.secureFormLayout.addView(mViewModel.routingNumberWrapper);
        mViewModel.secureFormLayout.addView(mViewModel.accountType);
        mViewModel.secureFormLayout.addView(mViewModel.holderType);
        if (includeBackButton) {
            Button backButton = new Button(getContext());
            backButton.setText(R.string.back);
            backButton.setOnClickListener((l) -> {
                mViewModel.layout.removeAllViews();
                createMenu();
            });
            mViewModel.secureFormLayout.addView(backButton);
        }
        mViewModel.submitButton = new Button(mViewModel.secureFormLayout.getContext());
        mViewModel.submitButton.setText(options.buttonText);
        mViewModel.submitButton.setOnClickListener(b -> submitBank());
        mViewModel.secureFormLayout.addView(mViewModel.submitButton);
        mViewModel.secureFormLayout.onFinishInflate();
        mViewModel.layout.addView(mViewModel.secureFormLayout);
        setMerchantText();
    }


    private void submitNewCard() {
        Single<TransactionResult<PaymentMethodResult>> result = mViewModel.secureFormLayout.createCreditCardPaymentMethod(options.billingAddress, options.shippingAddress);
        result.subscribe(options.submitCallback);
    }

    private void submitBank() {
        Single<TransactionResult<PaymentMethodResult>> result = mViewModel.secureFormLayout.createBankAccountPaymentMethod(options.billingAddress, options.shippingAddress);
        result.subscribe(options.submitCallback);
    }

    void setFullName() {
        mViewModel.fullNameWrapper = new TextInputLayout(mViewModel.secureFormLayout.getContext());
        mViewModel.fullNameContent = new TextInputEditText(mViewModel.secureFormLayout.getContext());
        mViewModel.fullNameWrapper.setHint(getString(R.string.hint_full_name));
        mViewModel.fullNameWrapper.addView(mViewModel.fullNameContent);
        mViewModel.fullNameWrapper.setId(R.id.spreedly_full_name);
        mViewModel.secureFormLayout.addView(mViewModel.fullNameWrapper);
    }

    void setLabel(String labelContent) {
        final float pixelDensity = getResources().getDisplayMetrics().density;
        int padding = (int) (16 * pixelDensity);
        int labelPadding = (int) (12 * pixelDensity);
        MaterialTextView label = new MaterialTextView(this.getContext());
        label.setText(labelContent);
        label.setTextAppearance(this.getContext(), android.R.style.TextAppearance_DeviceDefault_Large);
        label.setPadding(0, labelPadding, 0, 0);
        mViewModel.secureFormLayout.addView(label);
    }

    void setMerchantLogo() {
        LinearLayout wrapper = new LinearLayout(getContext());
        wrapper.setGravity(Gravity.CENTER);
        MaterialTextView textView = new MaterialTextView(getContext());
        if (options.merchantIcon != 0) {
            ImageView img = new ImageView(getContext());
            img.setImageResource(options.merchantIcon);
            wrapper.addView(img);
        }
        if (options.merchantTitle != null) {
            textView.setText(options.merchantTitle);
            textView.setTextAppearance(getContext(), R.style.TextAppearance_AppCompat_Large);
            textView.setGravity(Gravity.CENTER);
            wrapper.addView(textView);
        }
        mViewModel.layout.addView(wrapper, 0);
    }

    void setMerchantText() {
        MaterialTextView textView = new MaterialTextView(getContext());
        textView.setText(Html.fromHtml(options.merchantText));
        mViewModel.layout.addView(textView);
    }

    void addZipcode() {
        if (options.showZipcode && options.billingAddress == null) {
            TextInputLayout zipWrapper = new TextInputLayout(getContext());
            TextInputEditText zipContent = new TextInputEditText(getContext());
            zipWrapper.addView(zipContent);
            zipWrapper.setId(R.id.spreedly_zip);
            zipWrapper.setHint(getResources().getString(R.string.zipcode_hint));
            mViewModel.secureFormLayout.addView(zipWrapper);
        }
    }


}
