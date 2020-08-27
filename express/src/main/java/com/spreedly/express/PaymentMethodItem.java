package com.spreedly.express;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.spreedly.client.models.enums.CardBrand;

import java.io.Serializable;

public class PaymentMethodItem implements Serializable {
    @NonNull
    public final String token;
    @NonNull
    public final PaymentMethodType type;
    @NonNull
    public final String description;
    @Nullable
    final CardBrand brandType;

    public PaymentMethodItem(@NonNull String token, @NonNull PaymentMethodType type, @NonNull String description, @Nullable CardBrand brandType) {
        this.token = token;
        this.type = type;
        this.description = description;
        this.brandType = brandType;
    }
}
