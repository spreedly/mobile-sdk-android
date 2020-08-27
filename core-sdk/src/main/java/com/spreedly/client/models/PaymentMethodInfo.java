package com.spreedly.client.models;

import org.json.JSONObject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public abstract class PaymentMethodInfo {
    @Nullable
    public String company;
    @Nullable
    public String firstName;
    @Nullable
    public String lastName;
    @Nullable
    public String fullName;
    @Nullable
    public Address address;
    @Nullable
    public Address shippingAddress;

    @Nullable
    public Boolean retained;

    @Nullable
    public JSONObject metadata;

    @Nullable
    public String email;

    @NonNull
    abstract JSONObject toJson();

    protected PaymentMethodInfo(@NonNull PaymentMethodInfo copy) {
        this.company = copy.company;
        this.firstName = copy.firstName;
        this.lastName = copy.lastName;
        this.fullName = copy.fullName;
        this.address = copy.address;
        this.shippingAddress = copy.shippingAddress;
        this.retained = copy.retained;
        this.metadata = copy.metadata;
        this.email = copy.email;
    }

    protected PaymentMethodInfo() {
    }

    protected void addCommonJsonFields(@NonNull JSONObject paymentMethod, @NonNull JSONObject subType) {
        if (this.fullName != null) {
            subType.put("full_name", this.fullName);
        } else {
            subType.put("first_name", this.firstName);
            subType.put("last_name", this.lastName);
        }
        subType.put("company", this.company);
        if (this.address != null) {
            address.toJson(subType, "");
        }
        if (shippingAddress != null) {
            shippingAddress.toJson(subType, "shipping_");
        }
        paymentMethod.put("retained", this.retained);
        if (metadata != null) {
            paymentMethod.put("metadata", metadata);
        }
        if (email != null) {
            paymentMethod.put("email", email);
        }
    }
}
