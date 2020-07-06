package com.spreedly.express;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
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

    CardSlider cardSlider;

    public PaymentMenuView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyAttributes(context, attrs);
    }

    public PaymentMenuView(@NonNull Context context) {
        super(context);
        setPaymentType(0);
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
        submitButtonText = a.getString(R.styleable.PaymentMenuView_submitButtonTextOverride);
    }

    public void setPaymentType(int value) {
        paymentType = value;
    }

    public void addSavedCards(@Nullable List<StoredCard> cards) {
        storedCards = cards;
        cardSlider.update(storedCards);
    }

    private void createSavedCardList() {
        cardSlider = new CardSlider(getContext(), null);
        cardSlider.onFinishInflate();
        paymentSelectorLayout.addView(cardSlider);
    }

    private void createMenu() {
        this.setOrientation(VERTICAL);
        addCardButton = new Button(this.getContext());
        addCardButton.setText(R.string.add_a_card);
        addCardButton.setOnClickListener((l) -> {
            paymentView = new CreditCardPaymentView(this.getContext(), showBilling, showShipping, submitButtonText, true);
            paymentView.onFinishInflate();
            this.removeAllViews();
            this.addView(paymentView);
            paymentView.setBackButtonListener(new OnCustomEventListener() {
                @Override
                public void onEvent() {
                    removeAllViews();
                    addView(paymentSelectorLayout);
                }
            });
        });
        bankButton = new Button(this.getContext());
        bankButton.setText(R.string.pay_with_bank);
        bankButton.setOnClickListener((l) -> {
            paymentView = new BankAccountPaymentView(this.getContext(), showBilling, showShipping, submitButtonText, true);
            paymentView.onFinishInflate();
            this.removeAllViews();
            this.addView(paymentView);
            paymentView.setBackButtonListener(new OnCustomEventListener() {
                @Override
                public void onEvent() {
                    removeAllViews();
                    addView(paymentSelectorLayout);
                }
            });
        });
        cardButton = new Button(this.getContext());
        cardButton.setText(R.string.pay_with_card);
        cardButton.setOnClickListener((l) -> {
            paymentView = new CreditCardPaymentView(this.getContext(), showBilling, showShipping, submitButtonText, true);
            paymentView.onFinishInflate();
            this.removeAllViews();
            this.addView(paymentView);
            paymentView.setBackButtonListener(new OnCustomEventListener() {
                @Override
                public void onEvent() {
                    removeAllViews();
                    addView(paymentSelectorLayout);
                }
            });
        });
        paymentSelectorLayout = new LinearLayout(getContext());
        paymentSelectorLayout.setOrientation(VERTICAL);
        switch (paymentType) {
            case 0:
                createSavedCardList();
                paymentSelectorLayout.addView(addCardButton);
                break;
            case 1:
                paymentSelectorLayout.addView(cardButton);
                paymentSelectorLayout.addView(bankButton);
                break;
            case 2:
                createSavedCardList();
                paymentSelectorLayout.addView(addCardButton);
                paymentSelectorLayout.addView(bankButton);
                break;
        }
        this.addView(paymentSelectorLayout);
    }

}
