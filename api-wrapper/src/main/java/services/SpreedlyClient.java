package services;

import classes.PaymentMethodMeta;
import classes.PaymentMethodResult;
import classes.SpreedlySecureOpaqueString;
import classes.TransactionResult;
import rx.Single;

import java.io.IOException;
import java.util.concurrent.Future;

public interface SpreedlyClient<T extends PaymentMethodMeta> {
    SpreedlySecureOpaqueString createString();
    Single<TransactionResult<PaymentMethodResult>> tokenize(T data);
    Single<TransactionResult<PaymentMethodResult>> recache(String token, SpreedlySecureOpaqueString cvv);

    void stop() throws IOException;
}
