package com.spreedly.client.models;

import io.reactivex.rxjava3.annotations.NonNull;

public class IntermediateSigningKey {
    @NonNull
    public final String signedKey;
    @NonNull
    public final String[] signatures;

    public IntermediateSigningKey(@NonNull String signedKey, @NonNull String[] signatures) {
        this.signatures = signatures;
        this.signedKey = signedKey;
    }
}
