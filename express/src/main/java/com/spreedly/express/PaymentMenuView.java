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

    public PaymentMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyAttributes(context, attrs);
    }

    public PaymentMenuView(Context context) {
        super(context);
        setPaymentType(0);
    }

    private void applyAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ExpressView,
                0, 0);
        int paymentType = 0;
        paymentType = a.getInteger(R.styleable.PaymentMenuView_paymentType, 0);
        setPaymentType(paymentType);
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
            //TODO: clicklistener
        });
        bankButton = new Button(this.getContext());
        bankButton.setText(R.string.pay_with_bank);
        bankButton.setOnClickListener((l) -> {
            //TODO: clicklistener
        });
        cardButton = new Button(this.getContext());
        cardButton.setText(R.string.pay_with_card);
        cardButton.setOnClickListener((l) -> {
            //TODO: click listener
        });
        paymentSelectorLayout = new LinearLayout(getContext());
        switch (paymentType) {
            case 2:
                createSavedCardList();
                paymentSelectorLayout.addView(addCardButton);
                break;
            case 3:
                paymentSelectorLayout = new LinearLayout(getContext());
                paymentSelectorLayout.addView(cardButton);
                paymentSelectorLayout.addView(bankButton);
                break;
            case 4:
                paymentSelectorLayout = new LinearLayout(getContext());
                createSavedCardList();
                paymentSelectorLayout.addView(addCardButton);
                paymentSelectorLayout.addView(bankButton);
                break;
        }
    }
}
