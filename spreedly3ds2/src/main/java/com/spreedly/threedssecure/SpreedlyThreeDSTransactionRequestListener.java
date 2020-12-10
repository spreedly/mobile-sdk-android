package com.spreedly.threedssecure;

import androidx.annotation.NonNull;
/**
 * A interface containing methods to listen for responses from the SpreedlyThreeDSTransactionRequest
 * Contains methods success, cancelled, timeout and error
 */
public interface SpreedlyThreeDSTransactionRequestListener {
    void success(@NonNull String status);

    void cancelled();

    void timeout();

    void error(@NonNull SpreedlyThreeDSError error);
}
