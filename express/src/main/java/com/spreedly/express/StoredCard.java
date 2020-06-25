package com.spreedly.express;

import com.spreedly.client.models.enums.CardBrand;

public class StoredCard {
    public final String token;
    public final CardBrand type;
    public final String description;

    public StoredCard(String token, CardBrand type, String description) {
        this.token = token;
        this.type = type;
        this.description = description;
    }
}
