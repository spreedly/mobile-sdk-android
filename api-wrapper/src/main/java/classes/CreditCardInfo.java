package classes;

public class CreditCardInfo extends PaymentMethodMeta {
    String email;
    public String firstName;
    public String lastName;
    public String fullName;
    SpreedlySecureOpaqueString number;
    public String month;
    public String year;
    SpreedlySecureOpaqueString cvv;
    Address billingAddress;
    Address shippingAddress;
    String phoneNumber;
    boolean allowBlankName;
    boolean allowExpiredDate;
    boolean allowBlankDate;
    boolean eligibleForCardUpdate;
}
