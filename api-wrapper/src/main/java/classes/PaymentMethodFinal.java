package classes;

import java.util.Map;

public class PaymentMethodFinal {
    PaymentMethodProcess payment_method;
    public PaymentMethodFinal(CreditCardInfo cc){
        this.payment_method = new CreditCardFinal(cc);

    }
    class PaymentMethodProcess {
        String email;
        Map<String, Object> metadata;

    }

    class CreditCardFinal extends PaymentMethodProcess {
        CreditCardDetails credit_card;

        public CreditCardFinal(CreditCardInfo cc){
            this.email = cc.email;
            this.metadata = cc.data;
            this.credit_card = new CreditCardDetails(cc);
        }
    }

    class BankAccountFinal extends PaymentMethodProcess {
        BankAccountDetails bank_account;
    }


    class PaymentDetails {
        String first_name;
        String last_name;
        String full_name;
    }

    class CreditCardDetails extends PaymentDetails {
        String number;
        String verification_value;
        String month;
        String year;
        String company;
        String address1;
        String address2;
        String city;
        String state;
        String zip;
        String country;
        String phone_number;
        String shipping_address1;
        String shipping_address2;
        String shipping_city;
        String shipping_state;
        String shipping_zip;
        String shipping_country;
        public CreditCardDetails(CreditCardInfo cc){
            this.first_name = cc.firstName;
            this.last_name = cc.lastName;
            this.full_name = cc.fullName;
            this.number = cc.number;
            this.verification_value = cc.cvv;
            this.year = cc.year;
            this.month = cc.month;
            this.phone_number = cc.phoneNumber;
            if (cc.billingAddress != null){
                this.address1 = cc.billingAddress.address1;
                this.address2 = cc.billingAddress.address2;
                this.city = cc.billingAddress.city;
                this.state = cc.billingAddress.state;
                this.country = cc.billingAddress.country;
                this.zip = cc.billingAddress.zip;

            }
            if (cc.shippingAddress != null){
                this.shipping_address1 = cc.shippingAddress.address1;
                this.shipping_address2 = cc.shippingAddress.address2;
                this.shipping_city = cc.shippingAddress.city;
                this.shipping_state = cc.shippingAddress.state;
                this.shipping_country = cc.shippingAddress.country;
                this.shipping_zip = cc.shippingAddress.zip;
            }
        }
    }

    class BankAccountDetails extends PaymentDetails{
        String bank_routing_number;
        String bank_account_number;
        String bank_account_type;
        String bank_account_holder_type;
    }
}
