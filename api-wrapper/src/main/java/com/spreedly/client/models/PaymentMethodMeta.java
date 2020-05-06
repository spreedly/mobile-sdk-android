package com.spreedly.client.models;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

abstract class PaymentMethodMeta {
    @NonNull public boolean retained;
    @NonNull abstract String encode(@Nullable String email, @Nullable JSONObject metadata);
}
