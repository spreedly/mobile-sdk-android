package com.spreedly.express;

import android.content.Context;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import java.util.List;

class CardSlider extends HorizontalScrollView {
    List<StoredCard> cards;
    LinearLayout wrapper;
    final float pixelDensity;

    public CardSlider(@NonNull Context context, List<StoredCard> storedCards) {
        super(context);
        this.cards = storedCards;
        this.pixelDensity = getResources().getDisplayMetrics().density;
    }

    public CardSlider(@NonNull Context context) {
        super(context);
        this.pixelDensity = getResources().getDisplayMetrics().density;
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        wrapper = new LinearLayout(this.getContext());
        this.addView(wrapper);

    }


    public void update(List<StoredCard> storedCards) {
        wrapper.removeAllViews();
        cards = storedCards;
        int padding = (int) (12 * pixelDensity);
        int width = (int) (112 * pixelDensity);
        int height = (int) (80 * pixelDensity);
        float textSize = (4 * pixelDensity);
        for (int i = 0; i < cards.size(); i++) {
            StoredCard card = cards.get(i);
            Button button = new Button(wrapper.getContext());
            button.setCompoundDrawablesWithIntrinsicBounds(0, card.type.getIcon(), 0, 0);
            button.setText(card.description);
            button.setMinWidth(width);
            button.setMaxWidth(width);
            button.setMaxHeight(height);
            button.setMinHeight(height);
            button.setPadding(0, padding, 0, padding);
            button.setTextSize(textSize);
            wrapper.addView(button);
        }
    }
}
