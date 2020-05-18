package com.spreedly.client.models;

import io.reactivex.rxjava3.annotations.NonNull;

public final class SpreedlySecureOpaqueString {
    @NonNull
    private String data;

    public int length;

    public SpreedlySecureOpaqueString() {
        data = "";
        length = 0;
    }

    public void clear() {
        data = "";
        length = 0;
    }

    public void append(@NonNull String string) {
        data += string;
        length = data.length();
    }

    public void removeLastCharacter() {
        if (length == 0) {
            return;
        }
        data = data.substring(0, length - 1);
        length = data.length();
    }

    @NonNull String _encode() {
        return data;
    }
}
