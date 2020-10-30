package com.spreedly.threedssecure;

import androidx.annotation.NonNull;

/**
 * A class to listen for responses from the SpreedlyThreeDSTransactionRequest
 * Contains empty methods success, cancelled, timeout and error
 */
public class SpreedlyThreeDSTransactionRequestListener {
    void success(@NonNull String status) {
    }

    void cancelled() {
    }

    void timeout() {
    }

    void error(@NonNull SpreedlyThreeDSError error) {
    }
}
