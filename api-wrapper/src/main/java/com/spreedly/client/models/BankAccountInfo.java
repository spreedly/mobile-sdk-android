package com.spreedly.client.models;

import java.util.Map;

public class BankAccountInfo extends PaymentMethodMeta {
    public String email;
    public String firstName;
    public String lastName;
    public String fullName;
    public String routingNumber;
    public String accountNumber; //maybe SpreedlySecureOpaqueString?
    public String accountType; //maybe enum
    public String bankAccountHolderType; //maybe enum
    public Map<String, Object> additionalInformation;
}


