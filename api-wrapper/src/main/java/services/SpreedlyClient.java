package services;

import classes.PaymentMethodMeta;
import classes.PaymentMethodResult;
import classes.SpreedlySecureOpaqueString;
import classes.TransactionResult;

import java.io.IOException;
import java.util.concurrent.Future;

public interface SpreedlyClient<T extends PaymentMethodMeta> {
    SpreedlySecureOpaqueString createString();
    TransactionResult<PaymentMethodResult> tokenize(T data);
    TransactionResult<PaymentMethodResult> recache(String token, SpreedlySecureOpaqueString cvv);

    void stop() throws IOException;
}
