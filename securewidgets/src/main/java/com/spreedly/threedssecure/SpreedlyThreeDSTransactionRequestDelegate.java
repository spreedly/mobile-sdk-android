package com.spreedly.threedssecure;

public interface SpreedlyThreeDSTransactionRequestDelegate {
    void success(String status);

    void cancelled();

    void timeout();

    void error(SpreedlyThreeDSError error);
}
