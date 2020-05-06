package com.spreedly.client.models;

import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

abstract class PaymentMethodMeta {
    @NonNull public boolean retained;
    @NonNull abstract String encode();
}
