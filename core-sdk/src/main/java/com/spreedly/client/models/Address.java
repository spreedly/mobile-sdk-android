package com.spreedly.client.models;

import org.json.JSONObject;

import java.io.Serializable;

import io.reactivex.rxjava3.annotations.Nullable;

public class Address implements Serializable {
    public @Nullable String address1;
    public @Nullable String address2;
    public @Nullable String city;
    public @Nullable String state;
    public @Nullable String zip;
    public @Nullable String country;
    public @Nullable String phoneNumber;

    public Address() {
    }
    public Address(
            @Nullable String address1,
            @Nullable String address2,
            @Nullable String city,
            @Nullable String state,
            @Nullable String zip,
            @Nullable String country,
            @Nullable String phoneNumber) {
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.phoneNumber = phoneNumber;
    }

    static Address fromJson(JSONObject json, String prefix) {
        if (json.has(prefix + "address1")) {
            return new Address(
                    json.optString(prefix + "address1"),
                    json.optString(prefix + "address2"),
                    json.optString(prefix + "city"),
                    json.optString(prefix + "state"),
                    json.optString(prefix + "zip"),
                    json.optString(prefix + "country"),
                    json.optString(prefix + "phone_number")
            );
        } else {
            return null;
        }
    }

    JSONObject toJson(JSONObject json, String prefix) {
        if (json == null) json = new JSONObject();
        json.put(prefix + "address1", address1);
        json.put(prefix + "address2", address2);
        json.put(prefix + "city", city);
        json.put(prefix + "state", state);
        json.put(prefix + "zip", zip);
        json.put(prefix + "country", country);
        json.put(prefix + "phone_number", phoneNumber);
        return json;
    }
}
