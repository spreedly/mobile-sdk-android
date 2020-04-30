package com.spreedly.client.models;

public class Recache {
    PaymentMethod payment_method;
    public Recache(SpreedlySecureOpaqueString cvv) {
        this.payment_method = new PaymentMethod(cvv.getValue());
    }
    private class PaymentMethod {
        CreditCard credit_card;

        private PaymentMethod(String value) {
            this.credit_card = new CreditCard(value);
        }

        private class CreditCard {
            String verification_value;

            private CreditCard(String value) {
                this.verification_value = value;
            }
        }
    }
}
