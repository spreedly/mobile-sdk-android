package com.spreedly.express;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.spreedly.securewidgets.CardBrandHelper;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Consumer;

class CardSlider extends HorizontalScrollView {
    Consumer<PaymentMethodItem> paymentItemCallback;
    List<PaymentMethodItem> cards;
    LinearLayout wrapper;
    final float pixelDensity;
    CardBrandHelper cardBrandHelper;

    public CardSlider(@NonNull Context context, List<PaymentMethodItem> paymentMethodItems, Consumer<PaymentMethodItem> paymentItemCallback) {
        super(context);
        this.cards = paymentMethodItems;
        this.pixelDensity = getResources().getDisplayMetrics().density;
        this.paymentItemCallback = paymentItemCallback;
    }

    public CardSlider(@NonNull Context context) {
        super(context);
        this.pixelDensity = getResources().getDisplayMetrics().density;
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        cardBrandHelper = new CardBrandHelper();
        init();
    }

    private void init() {
        wrapper = new LinearLayout(this.getContext());
        this.addView(wrapper);
        update(cards);

    }


    public void update(List<PaymentMethodItem> paymentMethodItems) {
        wrapper.removeAllViews();
        cards = paymentMethodItems;
        int hpadding = (int) (10 * pixelDensity);
        int vpadding = (int) (20 * pixelDensity);
        int width = (int) (108 * pixelDensity);
        int height = (int) (80 * pixelDensity);
        float textSize = (4 * pixelDensity);
        for (int i = 0; i < cards.size(); i++) {
            PaymentMethodItem card = cards.get(i);
            Button button = new Button(wrapper.getContext());
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (card.brandType != null) {
                button.setCompoundDrawablesWithIntrinsicBounds(0, cardBrandHelper.getIcon(card.brandType), 0, 0);
            } else {
                button.setCompoundDrawablesWithIntrinsicBounds(0, imageHelper(card.type), 0, 0);
            }
            button.setText(card.description);
            button.setMinWidth(width);
            button.setPadding(hpadding, vpadding, hpadding, vpadding);
            button.setTextSize(textSize);
            button.setOnClickListener((l) -> submit(card));
            wrapper.addView(button);
        }
    }

    private void submit(PaymentMethodItem card) {
        Single<PaymentMethodItem> result = Single.create(emitter -> {
            emitter.onSuccess(card);
        });
        result.subscribe(paymentItemCallback);

    }

    private int imageHelper(PaymentMethodType type) {
        switch (type) {
            //TODO: get icons
            case CARD:
                return 0;
            break;
            case BANK:
                return 1;
            break;
            case THIRD_PARTY:
                return 2;
            break;
        }
    }
}
