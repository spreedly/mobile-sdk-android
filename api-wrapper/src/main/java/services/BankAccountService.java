package services;

import classes.PaymentMethodMeta;
import classes.PaymentMethodResult;
import classes.SpreedlySecureOpaqueString;
import classes.TransactionResult;

import java.io.IOException;

public class BankAccountService implements SpreedlyClient {
    @Override
    public SpreedlySecureOpaqueString createString() {
        return null;
    }

    @Override
    public TransactionResult<PaymentMethodResult> tokenize(PaymentMethodMeta data) {
        return null;
    }

    @Override
    public TransactionResult<PaymentMethodResult> recache(String token, SpreedlySecureOpaqueString cvv) {
        return null;
    }

    @Override
    public void stop() throws IOException {

    }
}
