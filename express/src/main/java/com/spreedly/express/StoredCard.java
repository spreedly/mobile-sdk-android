package com.spreedly.express;

import androidx.annotation.NonNull;

import com.spreedly.client.models.enums.CardBrand;

import java.io.Serializable;

public class StoredCard implements Serializable {
    @NonNull
    public final String token;
    @NonNull
    public final CardBrand type;
    @NonNull
    public final String description;

    public StoredCard(@NonNull String token, @NonNull CardBrand type, @NonNull String description) {
        this.token = token;
        this.type = type;
        this.description = description;
    }
}