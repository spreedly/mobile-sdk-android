package classes;

import java.util.Map;

public class BankAccountInfo extends PaymentMethodMeta {
    String firstName;
    String lastName;
    String fullName;
    String routingNumber;
    String accountNumber; //maybe SpreedlySecureOpaqueString?
    String accountType; //maybe enum
    String bankAccountHolderType; //maybe enum
    Map<String, Object> additionalInformation;
}


