package com.spreedly.threedssecure;

public class SpreedlyThreeDSError {

    public String message;
    public SpreedlyThreeDSErrorType type;

    SpreedlyThreeDSError(SpreedlyThreeDSErrorType type, String message) {
        this.message = message;
        this.type = type;
    }

    SpreedlyThreeDSError() {
    }

    SpreedlyThreeDSError(SpreedlyThreeDSErrorType type, Error error) {
        this.message = error.getMessage();
        this.type = type;
    }

    public SpreedlyThreeDSError(SpreedlyThreeDSErrorType type, Exception e) {
        this.type = type;
        this.message = e.getMessage();
    }

    public enum SpreedlyThreeDSErrorType {
        INVALID_INPUT,
        PROTOCOL_ERROR,
        RUNTIME_ERROR,
        UNKNOWN_ERROR
    }
}