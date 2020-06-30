package com.spreedly.express;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import java.util.List;

class CardSlider extends HorizontalScrollView {
    List<StoredCard> cards;
    LinearLayout wrapper;

    public CardSlider(@NonNull Context context) {
        super(context);
    }

    public CardSlider(@NonNull Context context, List<StoredCard> storedCards) {
        super(context);
        cards = storedCards;
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        wrapper = new LinearLayout(this.getContext());
        this.addView(wrapper);
        this.setBackgroundColor(getResources().getColor(R.color.design_default_color_on_secondary));
    }


    public void update(List<StoredCard> storedCards) {
        // this.removeAllViews();
        try {
            cards = storedCards;
            Button[] buttons = new Button[cards.size()];
            for (int i = 0; i < cards.size(); i++) {
                StoredCard card = cards.get(i);
                buttons[i] = new Button(wrapper.getContext());
                buttons[i].setText(card.description);
                wrapper.addView(buttons[i]);
            }
        } catch (Exception e) {
            Log.e("Spreedly", e.getMessage());
        }
    }
}
