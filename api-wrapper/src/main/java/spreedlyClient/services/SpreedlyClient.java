package spreedlyClient.services;

import spreedlyCleint.classes.PaymentMethodMeta;
import spreedlyCleint.classes.PaymentMethodResult;
import spreedlyCleint.classes.SpreedlySecureOpaqueString;
import spreedlyCleint.classes.TransactionResult;
import io.reactivex.rxjava3.core.Single;

import java.io.IOException;

public interface SpreedlyClient<T extends PaymentMethodMeta> {
    SpreedlySecureOpaqueString createString();
    Single<TransactionResult<PaymentMethodResult>> tokenize(T data);
    Single<TransactionResult<PaymentMethodResult>> recache(String token, SpreedlySecureOpaqueString cvv);

    void stop() throws IOException;
}
