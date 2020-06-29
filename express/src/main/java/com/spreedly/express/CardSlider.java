package com.spreedly.express;

import android.content.Context;
import android.widget.Button;
import android.widget.HorizontalScrollView;

import androidx.annotation.NonNull;

import java.util.List;

class CardSlider extends HorizontalScrollView {
    List<StoredCard> cards;

    public CardSlider(@NonNull Context context) {
        super(context);
    }

    public CardSlider(@NonNull Context context, List<StoredCard> storedCards) {
        super(context);
        cards = storedCards;
    }


    public void update(List<StoredCard> storedCards) {
        this.removeAllViews();
        cards = storedCards;
        for (int i = 0; i < cards.size(); i++) {
            StoredCard card = cards.get(i);
            Button button = new Button(getContext());
            button.setText(card.description);
            this.addView(button);
        }
    }
}
