package com.spreedly.client.models;

import io.reactivex.rxjava3.annotations.NonNull;

public class SpreedlySecureOpaqueString {
    @NonNull
    private String data;
    public void clear(){
        data = "";
    }

    public void append(@NonNull String string) {
        data += string;
    }
    public void removeLastCharacter(){

    }

    public void encode(){

    }
}
