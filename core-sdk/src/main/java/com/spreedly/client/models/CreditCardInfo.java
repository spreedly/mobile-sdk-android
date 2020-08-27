package com.spreedly.client.models;

import org.json.JSONObject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class CreditCardInfo extends PaymentMethodInfo {

    @Nullable
    public SpreedlySecureOpaqueString number;
    @Nullable
    public SpreedlySecureOpaqueString verificationValue;
    @Nullable
    public int month;
    @Nullable
    public int year;

    @Nullable
    public Boolean allowBlankName;
    @Nullable
    public Boolean allowExpiredDate;
    @Nullable
    public Boolean allowBlankDate;
    @Nullable
    public Boolean eligibleForCardUpdate;

    public CreditCardInfo(@NonNull PaymentMethodInfo copy) {
        super(copy);
        if (copy.getClass() == CreditCardInfo.class) {
            CreditCardInfo ccCopy = (CreditCardInfo) copy;
            this.allowBlankName = ccCopy.allowBlankName;
            this.allowBlankDate = ccCopy.allowBlankDate;
            this.allowExpiredDate = ccCopy.allowExpiredDate;
            this.eligibleForCardUpdate = ccCopy.eligibleForCardUpdate;
            this.month = ccCopy.month;
            this.year = ccCopy.year;
        }
    }

    public CreditCardInfo(@Nullable String fullName, @Nullable String firstName, @Nullable String lastName, @Nullable SpreedlySecureOpaqueString number, @Nullable SpreedlySecureOpaqueString verificationValue, @Nullable int year, @Nullable int month) {
        this.fullName = fullName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.verificationValue = verificationValue;
        this.month = month;
        this.year = year;
    }

    public CreditCardInfo() {
    }

    @NonNull
    public JSONObject toJson() {
        JSONObject wrapper = new JSONObject();
        JSONObject paymentMethod = new JSONObject();
        JSONObject creditCard = new JSONObject();

        addCommonJsonFields(paymentMethod, creditCard);

        creditCard.put("verification_value", verificationValue._encode());
        creditCard.put("number", number._encode());
        creditCard.put("month", this.month);
        creditCard.put("year", this.year);

        paymentMethod.put("credit_card", creditCard);
        paymentMethod.put("allow_blank_name", allowBlankName);
        paymentMethod.put("allow_expired_date", allowExpiredDate);
        paymentMethod.put("allow_blank_date", allowBlankDate);
        paymentMethod.put("eligible_for_card_updater", eligibleForCardUpdate);
        wrapper.put("payment_method", paymentMethod);
        return wrapper;
    }

}
