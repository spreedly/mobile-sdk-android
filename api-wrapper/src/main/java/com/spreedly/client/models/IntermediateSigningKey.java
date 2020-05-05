package com.spreedly.client.models;

import io.reactivex.rxjava3.annotations.NonNull;

class IntermediateSigningKey {
    @NonNull public final String signedKey;
    @NonNull public final String[] signatures;

    public IntermediateSigningKey(String signedKey, String[] signatures){
        this.signatures = signatures;
        this.signedKey = signedKey;
    }

}
