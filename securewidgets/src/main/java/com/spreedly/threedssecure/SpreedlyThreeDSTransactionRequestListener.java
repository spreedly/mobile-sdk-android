package com.spreedly.threedssecure;

import androidx.annotation.NonNull;

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
