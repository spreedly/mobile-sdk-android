package classes;

public class CreditCardInfo extends PaymentMethodMeta {
    public String company;
    String email;
    public String firstName;
    public String lastName;
    public String fullName;
    // public SpreedlySecureOpaqueString number;
    public String number;
    public String month;
    public String year;
    // public SpreedlySecureOpaqueString cvv;
    public String cvv;
    Address billingAddress;
    Address shippingAddress;
    String phoneNumber;
    boolean allowBlankName;
    boolean allowExpiredDate;
    boolean allowBlankDate;
    boolean eligibleForCardUpdate;
}
