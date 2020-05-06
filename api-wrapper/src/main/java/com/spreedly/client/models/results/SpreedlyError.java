package com.spreedly.client.models.results;

import io.reactivex.rxjava3.annotations.NonNull;

public class SpreedlyError {
    public final @NonNull String key;
    public final @NonNull String message;
    public final @NonNull String attribute;

    public SpreedlyError(
            @NonNull String attribute,
            @NonNull String key,
            @NonNull String message) {
        this.attribute = attribute;
        this.message = message;
        this.key = key;
    }
}
