package com.spreedly.client.models;

import io.reactivex.rxjava3.annotations.Nullable;

public class Address {
    public final @Nullable String address1;
    public final @Nullable String address2;
    public final @Nullable String city;
    public final @Nullable String state;
    public final @Nullable String zip;
    public final @Nullable String country;

    public Address(
            @Nullable String address1,
            @Nullable String address2,
            @Nullable String city,
            @Nullable String state,
            @Nullable String zip,
            @Nullable String country) {
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }
}
