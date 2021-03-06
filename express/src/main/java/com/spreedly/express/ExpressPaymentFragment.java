package com.spreedly.express;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.enums.AccountHolderType;
import com.spreedly.client.models.enums.AccountType;
import com.spreedly.client.models.results.BankAccountResult;
import com.spreedly.client.models.results.CreditCardResult;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;
import com.spreedly.securewidgets.SecureCreditCardField;
import com.spreedly.securewidgets.SecureExpirationDate;
import com.spreedly.securewidgets.SecureFormLayout;
import com.spreedly.securewidgets.SecureTextField;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
    SecureTextField cvvField;
    SecureExpirationDate secureExpirationDate;

    TextInputLayout routingNumberWrapper;
    SecureTextField accountNumberField;
    TextInputEditText routingNumberContent;
    RadioGroup accountType;
    RadioGroup holderType;

    boolean canGoBack = false;
    private int dp4;
    private int dp8;
    private int dp16;

    private Consumer<TransactionResult<? extends PaymentMethodResult>> submitCallback = result -> {
        Intent data = new Intent();
        try {
            data.putExtra(ExpressBuilder.EXTRA_PAYMENT_METHOD_TOKEN, result.result.token);
        } catch (NullPointerException e) {
            data.putExtra(ExpressBuilder.EXTRA_PAYMENT_METHOD_TOKEN, result.message);
        }
        data.putExtra(ExpressBuilder.EXTRA_PAYMENT_METHOD_TRANSACTION, result);
        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
        }
        //        else
        //            getActivity().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
        dismiss();
    };
    private Consumer<PaymentMethodItem> savedCardCallback = paymentMethodItem -> {
        Intent data = new Intent();
        data.putExtra(ExpressBuilder.EXTRA_PAYMENT_METHOD_TOKEN, paymentMethodItem.token);
        data.putExtra(ExpressBuilder.EXTRA_STORED_PAYMENT_METHOD, paymentMethodItem);
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
        int x;
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        BottomSheetBehavior<LinearLayout> behavior = new BottomSheetBehavior<>(getContext(), null);
        behavior.setHideable(false);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        params.setBehavior(behavior);
        root.setLayoutParams(params);
        root.setPadding(dp16, dp16, dp16, 0);
        layout = root;
        Configuration configuration = getResources().getConfiguration();
        int currentNightMode = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        int color = R.color.design_default_color_on_primary;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            TypedValue a = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.windowBackground, a, true);
            if (a.type >= TypedValue.TYPE_FIRST_COLOR_INT && a.type <= TypedValue.TYPE_LAST_COLOR_INT) {
                // windowBackground is a color
                color = a.data;
            }
            layout.setBackgroundColor(color);
        }
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
        cardSlider = new CardSlider(getContext(), options.paymentMethodItemList, savedCardCallback);
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
        cvvField = new SecureTextField(secureFormLayout.getContext());
        cvvField.setId(R.id.spreedly_cvv);
        cvvField.onFinishInflate();
        cvvField.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        secureExpirationDate = new SecureExpirationDate(secureFormLayout.getContext());
        secureExpirationDate.onFinishInflate();
        secureExpirationDate.setId(R.id.spreedly_cc_expiration_date);
        LinearLayout formatter = new LinearLayout(getContext());
        formatter.addView(cvvField);
        formatter.addView(secureExpirationDate);
        addZipcode();
        secureFormLayout.addView(secureCreditCardField);
        secureFormLayout.addView(formatter);
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
        addZipcode();
        secureFormLayout.addView(accountNumberField);
        secureFormLayout.addView(routingNumberWrapper);
        accountType = buildRadioGroup(secureFormLayout, R.id.spreedly_ba_account_type, R.string.hint_account_type, false);
        holderType = buildRadioGroup(secureFormLayout, R.id.spreedly_ba_account_holder_type, R.string.hint_holder_type, true);
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
        Single<TransactionResult<CreditCardResult>> result = secureFormLayout.createCreditCardPaymentMethod();
        result.subscribe(submitCallback);
    }

    private void submitBank() {
        Single<TransactionResult<BankAccountResult>> result = secureFormLayout.createBankAccountPaymentMethod();
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
        if (options.header != null) {
            layout.addView(LayoutInflater.from(getContext()).inflate(options.header, layout, false));
        }
    }

    void setMerchantText() {
        if (options.merchantText != null) {
            MaterialTextView textView = new MaterialTextView(getContext());
            textView.setText(Html.fromHtml(options.merchantText));
            layout.addView(textView);
        }
        if (options.footer != null) {
            layout.addView(LayoutInflater.from(getContext()).inflate(options.footer, layout, false));
        }
    }

    void addZipcode() {
        if (options.showZipcode) {
            TextInputLayout zipWrapper = new TextInputLayout(getContext());
            TextInputEditText zipContent = new TextInputEditText(getContext());
            zipWrapper.addView(zipContent);
            zipWrapper.setId(R.id.spreedly_zip);
            zipWrapper.setHint(getResources().getString(R.string.zipcode_hint));
            secureFormLayout.addView(zipWrapper);
        }
    }

    RadioGroup buildRadioGroup(ViewGroup parent, int id, int rlabel, boolean holderType) {
        View v = getLayoutInflater().inflate(R.layout.labeled_radio, parent, false);
        parent.addView(v);
        TextView title = v.findViewById(android.R.id.title);
        title.setText(rlabel);

        RadioButton option1 = v.findViewById(android.R.id.text1);
        RadioButton option2 = v.findViewById(android.R.id.text2);
        RadioGroup radioGroup = v.findViewById(android.R.id.content);
        radioGroup.setId(id);
        if (holderType) {
            option1.setText(secureFormLayout.accountTypeHelper.getString(AccountHolderType.personal));
            option2.setText(secureFormLayout.accountTypeHelper.getString(AccountHolderType.business));
            option1.setId(R.id.spreedly_holder_button_personal);
            option2.setId(R.id.spreedly_holder_button_business);
            radioGroup.check(R.id.spreedly_holder_button_personal);
        } else {
            option1.setText(secureFormLayout.accountTypeHelper.getString(AccountType.checking));
            option2.setText(secureFormLayout.accountTypeHelper.getString(AccountType.savings));
            option1.setId(R.id.spreedly_account_button_checking);
            option2.setId(R.id.spreedly_account_button_saving);
            radioGroup.check(R.id.spreedly_account_button_checking);
        }
        return radioGroup;
    }
}
