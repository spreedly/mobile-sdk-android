package com.spreedly.client.models;

public class SpreedlyError {
    String key;
    String message;
    String attribute;

    public SpreedlyError(String attribute, String key, String message){
        this.attribute =attribute;
        this.message = message;
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getKey() {
        return key;
    }
}
