package com.spreedly.client.models.results;

public class SpreedlyError {
    public final String key;
    public final String message;
    public final String attribute;

    public SpreedlyError(String attribute, String key, String message){
        this.attribute =attribute;
        this.message = message;
        this.key = key;
    }
}
