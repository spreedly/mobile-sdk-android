package com.spreedly.client.models.transactions;

import com.spreedly.client.models.SpreedlySecureOpaqueString;

import io.reactivex.rxjava3.annotations.NonNull;

public class Recache {
    PaymentMethod payment_method;
    public Recache(@NonNull SpreedlySecureOpaqueString cvv) {
        this.payment_method = new PaymentMethod("432");
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
