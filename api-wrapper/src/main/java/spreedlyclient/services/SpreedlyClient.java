package spreedlyclient.services;

import spreedlyclient.classes.PaymentMethodMeta;
import spreedlyclient.classes.PaymentMethodResult;
import spreedlyclient.classes.SpreedlySecureOpaqueString;
import spreedlyclient.classes.TransactionResult;
import io.reactivex.rxjava3.core.Single;

import java.io.IOException;

public interface SpreedlyClient<T extends PaymentMethodMeta> {
    SpreedlySecureOpaqueString createString();
    Single<TransactionResult<PaymentMethodResult>> tokenize(T data);
    Single<TransactionResult<PaymentMethodResult>> recache(String token, SpreedlySecureOpaqueString cvv);

    void stop() throws IOException;
}
