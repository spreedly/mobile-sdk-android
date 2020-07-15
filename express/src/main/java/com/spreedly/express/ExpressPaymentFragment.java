package com.spreedly.express;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
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

import java.io.Serializable;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Consumer;

public class ExpressPaymentFragment extends BottomSheetDialogFragment {

    private SpreedlyClient client;
    private PaymentOptions options;

    Button submitButton;
    CardSlider cardSlider;
    LinearLayoutCompat layout;
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

    boolean canGoBack = false;
    private int dp4;
    private int dp8;
    private int dp16;

    private Consumer<TransactionResult<PaymentMethodResult>> submitCallback = result -> {
        Intent data = new Intent();
        data.putExtra(ExpressBuilder.EXTRA_PAYMENT_METHOD_TOKEN, result.result.token);
        data.putExtra(ExpressBuilder.EXTRA_PAYMENT_METHOD_TRANSACTION, result);
        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
        }
        //        else
        //            getActivity().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
        dismiss();
    };
    private Consumer<StoredCard> savedCardCallback = storedCard -> {
        Intent data = new Intent();
        data.putExtra(ExpressBuilder.EXTRA_PAYMENT_METHOD_TOKEN, storedCard.token);
        data.putExtra(ExpressBuilder.EXTRA_STORED_PAYMENT_METHOD, storedCard);
        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
        }
        dismiss();
    };

    @NonNull
    public static ExpressPaymentFragment newInstance(@NonNull SpreedlyClient client, @NonNull PaymentOptions options) {
        final ExpressPaymentFragment fragment = new ExpressPaymentFragment();
        final Bundle args = new Bundle();
        args.putSerializable("client", (Serializable) client);
        args.putSerializable("options", options);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        client = (SpreedlyClient) getArguments().getSerializable("client");
        options = (PaymentOptions) getArguments().getSerializable("options");

        dp4 = (int) (getResources().getDisplayMetrics().density * 4);
        dp8 = (int) (getResources().getDisplayMetrics().density * 8);
        dp16 = (int) (getResources().getDisplayMetrics().density * 16);

        LinearLayoutCompat root = new LinearLayoutCompat(getContext());
        root.setOrientation(LinearLayoutCompat.VERTICAL);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        BottomSheetBehavior<LinearLayout> behavior = new BottomSheetBehavior<>(getContext(), null);
        behavior.setHideable(false);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        params.setBehavior(behavior);
        root.setLayoutParams(params);
        root.setPadding(dp16, dp16, dp16, 0);
        layout = root;
        createMenu();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {
            if (canGoBack && keyCode == KeyEvent.KEYCODE_BACK) {
                canGoBack = false;
                layout.removeAllViews();
                createMenu();
                return true;
            }
            return false;
        });
    }

    private void createMenu() {
        setMerchantLogo();
        addCardButton = new Button(getContext());
        addCardButton.setText(R.string.add_a_card);
        addCardButton.setOnClickListener((l) -> {
            layout.removeAllViews();
            createCardPayment(true);
        });
        bankButton = new Button(getContext());
        bankButton.setText(R.string.pay_with_bank);
        bankButton.setOnClickListener((l) -> {
            layout.removeAllViews();
            createBankPayment(true);
        });
        cardButton = new Button(getContext());
        cardButton.setText(R.string.pay_with_card);
        cardButton.setOnClickListener((l) -> {
            layout.removeAllViews();
            createCardPayment(true);
        });
        paymentSelectorLayout = new LinearLayout(getContext());
        paymentSelectorLayout.setOrientation(LinearLayout.VERTICAL);
        switch (options.paymentType) {
            case CARDS_ONLY:
                setLabel("Select Payment Method", paymentSelectorLayout);
                createSavedCardList();
                setLabel("Add Payment Method", paymentSelectorLayout);
                paymentSelectorLayout.addView(addCardButton);
                break;
            case BANK_ONLY:
                createBankPayment(false);
                break;
            case NEW_CARD_ONLY:
                createCardPayment(false);
                break;
            case NEW_CARD_AND_BANK:
                setLabel("Add Payment Method", paymentSelectorLayout);
                paymentSelectorLayout.addView(cardButton);
                paymentSelectorLayout.addView(bankButton);
                break;
            case ALL:
                setLabel("Select Payment Method", paymentSelectorLayout);
                createSavedCardList();
                setLabel("Add Payment Method", paymentSelectorLayout);
                paymentSelectorLayout.addView(addCardButton);
                paymentSelectorLayout.addView(bankButton);
                break;
        }
        layout.addView(paymentSelectorLayout);
        setMerchantText();
    }

    private void createSavedCardList() {
        cardSlider = new CardSlider(getContext(), options.storedCardList, savedCardCallback);
        cardSlider.onFinishInflate();
        paymentSelectorLayout.addView(cardSlider);
    }

    private void createCardPayment(boolean includeBackButton) {
        setMerchantLogo();
        secureFormLayout = new SecureFormLayout(getContext());
        secureFormLayout.setOrientation(LinearLayout.VERTICAL);
        secureFormLayout.setSpreedlyClient(client);
        setFullName();
        secureCreditCardField = new SecureCreditCardField(secureFormLayout.getContext());
        secureCreditCardField.setId(R.id.spreedly_credit_card_number);
        secureCreditCardField.onFinishInflate();
        ccvField = new SecureTextField(secureFormLayout.getContext());
        ccvField.setId(R.id.spreedly_ccv);
        ccvField.onFinishInflate();
        secureExpirationDate = new SecureExpirationDate(secureFormLayout.getContext());
        secureExpirationDate.onFinishInflate();
        secureExpirationDate.setId(R.id.spreedly_cc_expiration_date);
        addZipcode();
        secureFormLayout.addView(secureCreditCardField);
        secureFormLayout.addView(ccvField);
        secureFormLayout.addView(secureExpirationDate);
        if (includeBackButton) {
            canGoBack = true;
        }
        submitButton = new Button(getContext());
        submitButton.setText(options.buttonText);
        submitButton.setOnClickListener(b -> submitNewCard());
        secureFormLayout.addView(submitButton);
        secureFormLayout.onFinishInflate();
        layout.addView(secureFormLayout);
        setMerchantText();
    }

    private void createBankPayment(boolean includeBackButton) {
        setMerchantLogo();
        secureFormLayout = new SecureFormLayout(getContext());
        secureFormLayout.setOrientation(LinearLayout.VERTICAL);
        secureFormLayout.setSpreedlyClient(client);
        setFullName();
        accountNumberField = new SecureTextField(getContext());
        accountNumberField.setId(R.id.spreedly_ba_account_number);
        accountNumberField.onFinishInflate();
        routingNumberWrapper = new TextInputLayout(secureFormLayout.getContext());
        routingNumberWrapper.setId(R.id.spreedly_ba_routing_number);
        routingNumberWrapper.setHint(getString(R.string.hint_routing_number));
        routingNumberContent = new TextInputEditText(secureFormLayout.getContext());
        routingNumberWrapper.addView(routingNumberContent);
        accountType = new Spinner(secureFormLayout.getContext());
        accountType.setAdapter(new ArrayAdapter<>(secureFormLayout.getContext(), android.R.layout.simple_spinner_dropdown_item, BankAccountType.values()));
        accountType.setId(R.id.spreedly_ba_account_type);

        holderType = new Spinner(secureFormLayout.getContext());
        holderType.setAdapter(new ArrayAdapter<>(secureFormLayout.getContext(), android.R.layout.simple_spinner_dropdown_item, BankAccountHolderType.values()));
        holderType.setId(R.id.spreedly_ba_account_holder_type);
        addZipcode();
        secureFormLayout.addView(accountNumberField);
        secureFormLayout.addView(routingNumberWrapper);
        secureFormLayout.addView(accountType);
        holderType = buildSpinner(
                secureFormLayout,
                R.id.spreedly_ba_account_holder_type,
                R.string.hint_account_type,
                new ArrayAdapter<>(secureFormLayout.getContext(), android.R.layout.simple_spinner_dropdown_item, BankAccountHolderType.values())
        );
        if (includeBackButton) {
            canGoBack = true;
        }
        submitButton = new Button(secureFormLayout.getContext());
        submitButton.setText(options.buttonText);
        submitButton.setOnClickListener(b -> submitBank());
        secureFormLayout.addView(submitButton);
        secureFormLayout.onFinishInflate();
        layout.addView(secureFormLayout);
        setMerchantText();
    }


    private void submitNewCard() {
        Single<TransactionResult<PaymentMethodResult>> result = secureFormLayout.createCreditCardPaymentMethod(options.billingAddress, options.shippingAddress);
        result.subscribe(submitCallback);
    }

    private void submitBank() {
        Single<TransactionResult<PaymentMethodResult>> result = secureFormLayout.createBankAccountPaymentMethod(options.billingAddress, options.shippingAddress);
        result.subscribe(submitCallback);
    }

    void setFullName() {
        fullNameWrapper = new TextInputLayout(secureFormLayout.getContext());
        fullNameContent = new TextInputEditText(secureFormLayout.getContext());
        fullNameWrapper.setHint(getString(R.string.hint_full_name));
        fullNameWrapper.addView(fullNameContent);
        fullNameWrapper.setId(R.id.spreedly_full_name);
        secureFormLayout.addView(fullNameWrapper);
    }

    void setLabel(String labelContent, LinearLayout layout) {
        final float pixelDensity = getResources().getDisplayMetrics().density;
        int padding = (int) (16 * pixelDensity);
        int labelPadding = (int) (12 * pixelDensity);
        MaterialTextView label = new MaterialTextView(getContext());
        label.setText(labelContent);
        label.setTextAppearance(getContext(), android.R.style.TextAppearance_DeviceDefault_Medium);
        label.setPadding(0, labelPadding, 0, 0);
        layout.addView(label);
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
        layout.addView(wrapper, 0);
    }

    void setMerchantText() {
        MaterialTextView textView = new MaterialTextView(getContext());
        textView.setText(Html.fromHtml(options.merchantText));
        layout.addView(textView);
    }

    void addZipcode() {
        if (options.showZipcode && options.billingAddress == null) {
            TextInputLayout zipWrapper = new TextInputLayout(getContext());
            TextInputEditText zipContent = new TextInputEditText(getContext());
            zipWrapper.addView(zipContent);
            zipWrapper.setId(R.id.spreedly_zip);
            zipWrapper.setHint(getResources().getString(R.string.zipcode_hint));
            secureFormLayout.addView(zipWrapper);
        }
    }

    Spinner buildSpinner(ViewGroup parent, int id, int rlabel, SpinnerAdapter adapter) {
        View v = getLayoutInflater().inflate(R.layout.labeled_spinner,parent, false);
        parent.addView(v);

        TextView title = v.findViewById(android.R.id.title);
        title.setText(rlabel);

        Spinner spinnner = v.findViewById(android.R.id.text1);
        spinnner.setId(id);
        spinnner.setAdapter(adapter);
        return spinnner;
    }
}
