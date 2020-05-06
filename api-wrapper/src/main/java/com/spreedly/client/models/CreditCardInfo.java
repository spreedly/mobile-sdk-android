package com.spreedly.client.models;

import org.json.JSONObject;

import javax.swing.JComponent;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class CreditCardInfo extends PaymentMethodMeta {
    @Nullable public String company;
    @Nullable public String email;
    @Nullable public String firstName;
    @Nullable public String lastName;
    @Nullable public String fullName;
    @Nullable public SpreedlySecureOpaqueString number;
    @NonNull public String month;
    @NonNull public String year;
    @NonNull public SpreedlySecureOpaqueString cvv;
    @Nullable public Address billingAddress;
    @Nullable public Address shippingAddress;
    @Nullable public String phoneNumber;
    public boolean allowBlankName;
    public boolean allowExpiredDate;
    public boolean allowBlankDate;
    public boolean eligibleForCardUpdate;

    public CreditCardInfo(){
    };
    public CreditCardInfo(@NonNull String firstName, @NonNull String lastName, @NonNull SpreedlySecureOpaqueString number, @NonNull SpreedlySecureOpaqueString cvv, @NonNull String month, @NonNull String year, boolean retained){
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.cvv = cvv;
        this.month = month;
        this.year = year;
        this.retained = retained;
    }

    public CreditCardInfo(@NonNull String fullName, @NonNull SpreedlySecureOpaqueString number, @NonNull SpreedlySecureOpaqueString cvv, @NonNull String month, @NonNull String year, boolean retained){
        this.fullName = fullName;
        this.number = number;
        this.cvv = cvv;
        this.month = month;
        this.year = year;
        this.retained = retained;
    }

    public CreditCardInfo(@NonNull String firstName, @NonNull String lastName, @NonNull SpreedlySecureOpaqueString number, @NonNull SpreedlySecureOpaqueString cvv, @NonNull String month, @NonNull String year, @Nullable Address billingAddress, @Nullable Address shippingAddress, @Nullable String company, @Nullable String email, boolean retained, boolean allowBlankDate, boolean allowBlankName, boolean allowExpiredDate, boolean eligibleForCardUpdate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.cvv = cvv;
        this.month = month;
        this.year = year;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
        this.allowBlankDate = allowBlankDate;
        this.allowBlankName = allowBlankName;
        this.allowExpiredDate = allowExpiredDate;
        this.eligibleForCardUpdate = eligibleForCardUpdate;
        this.retained = retained;
    }

    public CreditCardInfo(@NonNull String fullName, @NonNull SpreedlySecureOpaqueString number, @NonNull SpreedlySecureOpaqueString cvv, @NonNull String month, @NonNull String year, @Nullable Address billingAddress, @Nullable Address shippingAddress, @Nullable String company, @Nullable String email, boolean retained, boolean allowBlankDate, boolean allowBlankName, boolean allowExpiredDate, boolean eligibleForCardUpdate){
        this.fullName = fullName;
        this.number = number;
        this.cvv = cvv;
        this.month = month;
        this.year = year;
        this.email = email;
        this.company = company;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
        this.allowBlankDate = allowBlankDate;
        this.allowBlankName = allowBlankName;
        this.allowExpiredDate = allowExpiredDate;
        this.eligibleForCardUpdate = eligibleForCardUpdate;
        this.retained = retained;
    }


    @Override
    @NonNull public String encode() {
        JSONObject wrapper = new JSONObject();
        JSONObject paymentMethod = new JSONObject();
        JSONObject creditCard = new JSONObject();
        if (this.fullName != null && this.fullName != ""){
            creditCard.put("full_name", this.fullName);
        } else {
            creditCard.put("first_name", this.firstName);
            creditCard.put("last_name", this.lastName);
        }
        creditCard = this.cvv.encode(creditCard, "verification_value");
        creditCard = this.number.encode(creditCard, "number");
        creditCard.put("month", this.month);
        creditCard.put("year", this.year);
        if (this.billingAddress != null){
            creditCard.put("address1", this.billingAddress.address1);
            creditCard.put("address2", this.billingAddress.address2);
            creditCard.put("city", this.billingAddress.city);
            creditCard.put("state", this.billingAddress.state);
            creditCard.put("zip", this.billingAddress.zip);
            creditCard.put("country", this.billingAddress.country);
        }
        if (shippingAddress != null){
            creditCard.put("shipping_address1", this.shippingAddress.address1);
            creditCard.put("shipping_address2", this.shippingAddress.address2);
            creditCard.put("shipping_city", this.shippingAddress.city);
            creditCard.put("shipping_state", this.shippingAddress.state);
            creditCard.put("shipping_zip", this.shippingAddress.zip);
            creditCard.put("shipping_country", this.shippingAddress.country);
        }
        creditCard.put("phone_number", this.phoneNumber);
        creditCard.put("company", this.company);
        paymentMethod.put("credit_card", creditCard);
        paymentMethod.put("email", this.email);
        paymentMethod.put("metadata", this.data);
        paymentMethod.put("retained", this.retained);
        paymentMethod.put("allow_blank_name", allowBlankName);
        paymentMethod.put("allow_expired_date", allowExpiredDate);
        paymentMethod.put("allow_blank_date", allowBlankDate);
        paymentMethod.put("eligible_for_card_updater", eligibleForCardUpdate);
        wrapper.put("payment_method", paymentMethod);
        return wrapper.toString();
    }
}
