package com.spreedly.client.models;

import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class PaymentMethodMeta {
    @NonNull boolean retained;
    @Nullable Map<String, Object> data;
}
