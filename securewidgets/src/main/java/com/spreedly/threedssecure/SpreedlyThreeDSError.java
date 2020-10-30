package com.spreedly.threedssecure;

public class SpreedlyThreeDSError {

    Exception exception;
    public String message;
    Error error;
    SpreedlyThreeDSErrorType type;

    SpreedlyThreeDSError(SpreedlyThreeDSErrorType type, String message) {
        this.message = message;
        this.type = type;
    }

    SpreedlyThreeDSError() {
    }

    SpreedlyThreeDSError(SpreedlyThreeDSErrorType type, Error error) {
        this.error = error;
        this.type = type;
    }

    public SpreedlyThreeDSError(SpreedlyThreeDSErrorType type, Exception e) {
        this.type = type;
        this.exception = e;
    }

    public enum SpreedlyThreeDSErrorType {
        INVALID_INPUT,
        PROTOCOL_ERROR,
        RUNTIME_ERROR,
        UNKNOWN_ERROR
    }
}