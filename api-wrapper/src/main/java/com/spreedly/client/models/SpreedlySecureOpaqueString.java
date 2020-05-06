package com.spreedly.client.models;

import org.json.JSONObject;

import io.reactivex.rxjava3.annotations.NonNull;

public class SpreedlySecureOpaqueString {
    @NonNull
    private String data;
    public void clear(){
        data = "";
    }
    public SpreedlySecureOpaqueString(){
        data = "";
    }
    public void append(@NonNull String string) {
        data += string;
    }
    public void removeLastCharacter(){

    }

    @NonNull public JSONObject encode(@NonNull JSONObject json, @NonNull String key){
        json.put(key, this.data);
        return json;
    }
}
