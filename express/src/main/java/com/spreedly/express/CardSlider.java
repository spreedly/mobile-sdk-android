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
    Consumer<StoredCard> storedCardCallback;
    List<StoredCard> cards;
    LinearLayout wrapper;
    final float pixelDensity;
    CardBrandHelper cardBrandHelper;

    public CardSlider(@NonNull Context context, List<StoredCard> storedCards, Consumer<StoredCard> storedCardCallback) {
        super(context);
        this.cards = storedCards;
        this.pixelDensity = getResources().getDisplayMetrics().density;
        this.storedCardCallback = storedCardCallback;
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


    public void update(List<StoredCard> storedCards) {
        wrapper.removeAllViews();
        cards = storedCards;
        int hpadding = (int) (10 * pixelDensity);
        int vpadding = (int) (20 * pixelDensity);
        int width = (int) (108 * pixelDensity);
        int height = (int) (80 * pixelDensity);
        float textSize = (4 * pixelDensity);
        for (int i = 0; i < cards.size(); i++) {
            StoredCard card = cards.get(i);
            Button button = new Button(wrapper.getContext());
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setCompoundDrawablesWithIntrinsicBounds(0, cardBrandHelper.getIcon(card.type), 0, 0);
            button.setText(card.description);
            button.setMinWidth(width);
            button.setPadding(hpadding, vpadding, hpadding, vpadding);
            button.setTextSize(textSize);
            button.setOnClickListener((l) -> submit(card));
            wrapper.addView(button);
        }
    }

    private void submit(StoredCard card) {
        Single<StoredCard> result = Single.create(emitter -> {
            emitter.onSuccess(card);
        });
        result.subscribe(storedCardCallback);

    }
}
