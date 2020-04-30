package com.spreedly.client.models;

public class CreditCardInfo extends PaymentMethodMeta {
    public String company;
    public String email;
    public String firstName;
    public String lastName;
    public String fullName;
    // public SpreedlySecureOpaqueString number;
    public String number;
    public String month;
    public String year;
    // public SpreedlySecureOpaqueString cvv;
    public String cvv;
    public Address billingAddress;
    public Address shippingAddress;
    public String phoneNumber;
    boolean allowBlankName;
    boolean allowExpiredDate;
    boolean allowBlankDate;
    boolean eligibleForCardUpdate;
}
