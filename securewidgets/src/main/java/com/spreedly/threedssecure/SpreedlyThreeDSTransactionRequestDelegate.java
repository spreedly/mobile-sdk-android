package com.spreedly.threedssecure;

import androidx.annotation.NonNull;

public interface SpreedlyThreeDSTransactionRequestDelegate {
    void success(@NonNull String status);

    void cancelled();

    void timeout();

    void error(@NonNull SpreedlyThreeDSError error);
}
