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

    @NonNull
    abstract JSONObject toJson(@Nullable String email, @Nullable JSONObject metadata);

    protected void addCommonJsonFields(@NonNull JSONObject paymentMethod, @NonNull JSONObject subType, @Nullable String email, @Nullable JSONObject metadata) {
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
