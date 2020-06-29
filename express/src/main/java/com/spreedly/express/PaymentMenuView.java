package com.spreedly.express;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.List;

public class PaymentMenuView extends LinearLayout {
    int paymentType;
    LinearLayout paymentSelectorLayout;
    List<StoredCard> storedCards;

    Button addCardButton;
    Button bankButton;
    Button cardButton;

    PaymentView paymentView;
    boolean showBilling;
    boolean showShipping;
    String submitButtonText;

    public PaymentMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyAttributes(context, attrs);
    }

    public PaymentMenuView(Context context) {
        super(context);
        setPaymentType(0);
        setAddressUse(true, true);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        createMenu();
    }

    private void applyAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PaymentMenuView,
                0, 0);
        int paymentType = 0;
        paymentType = a.getInteger(R.styleable.PaymentMenuView_paymentType, 0);
        setPaymentType(paymentType);
        boolean shipping = true;
        boolean billing = true;
        billing = a.getBoolean(R.styleable.PaymentMenuView_includeBillingAddressOverride, true);
        shipping = a.getBoolean(R.styleable.PaymentMenuView_includeShippingAddressOverride, true);
        submitButtonText = a.getString(R.styleable.PaymentMenuView_submitButtonTextOverride);
        setAddressUse(billing, shipping);
    }

    public void setPaymentType(int value) {
        paymentType = value;
    }

    public void addSavedCard(@Nullable List<StoredCard> cards) {
        storedCards = cards;
    }

    private void createSavedCardList() {
        if (storedCards == null || storedCards.size() == 0) {
            return;
        } else {

            for (StoredCard card : storedCards) {
                Button button = new Button(getContext());
                button.setText(card.description);
                paymentSelectorLayout.addView(button);

            }
        }
    }

    private void createMenu() {
        addCardButton = new Button(this.getContext());
        addCardButton.setText(R.string.add_a_card);
        addCardButton.setOnClickListener((l) -> {
            paymentView = new CreditCardPaymentView(this.getContext(), showBilling, showShipping, submitButtonText);
            paymentView.onFinishInflate();
            this.removeAllViews();
            this.addView(paymentView);
        });
        bankButton = new Button(this.getContext());
        bankButton.setText(R.string.pay_with_bank);
        bankButton.setOnClickListener((l) -> {
            paymentView = new BankAccountPaymentView(this.getContext(), showBilling, showShipping, submitButtonText);
            paymentView.setAddressUse(showBilling, showShipping);
            paymentView.onFinishInflate();
            this.removeAllViews();
            this.addView(paymentView);
        });
        cardButton = new Button(this.getContext());
        cardButton.setText(R.string.pay_with_card);
        cardButton.setOnClickListener((l) -> {
            paymentView = new CreditCardPaymentView(this.getContext(), showBilling, showShipping, submitButtonText);
            paymentView.setAddressUse(showBilling, showShipping);
            paymentView.onFinishInflate();
            this.removeAllViews();
            this.addView(paymentView);
        });
        paymentSelectorLayout = new LinearLayout(getContext());
        switch (paymentType) {
            case 0:
                createSavedCardList();
                paymentSelectorLayout.addView(addCardButton);
                break;
            case 1:
                paymentSelectorLayout = new LinearLayout(getContext());
                paymentSelectorLayout.addView(cardButton);
                paymentSelectorLayout.addView(bankButton);
                break;
            case 2:
                paymentSelectorLayout = new LinearLayout(getContext());
                createSavedCardList();
                paymentSelectorLayout.addView(addCardButton);
                paymentSelectorLayout.addView(bankButton);
                break;
        }
        this.addView(paymentSelectorLayout);
    }

    public void setAddressUse(boolean billing, boolean shipping) {
        showBilling = billing;
        showShipping = shipping;
    }
}
