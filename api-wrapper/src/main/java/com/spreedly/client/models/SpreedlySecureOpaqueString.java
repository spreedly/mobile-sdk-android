package com.spreedly.client.models;

import org.json.JSONObject;

import io.reactivex.rxjava3.annotations.NonNull;

public class SpreedlySecureOpaqueString {
    @NonNull
    private String data;
    public int length;
    public void clear(){
        data = "";
        length = 0;
    }
    public SpreedlySecureOpaqueString(){
        data = "";
        length = 0;
    }
    public void append(@NonNull String string) {
        data += string;
        length = data.length();
    }
    public void removeLastCharacter(){
        if (length == 0){
            return;
        }
        data = data.substring(0, length - 1);
        length = data.length();
    }

    @NonNull public JSONObject encode(@NonNull JSONObject json, @NonNull String key){
        json.put(key, this.data);
        return json;
    }
}
