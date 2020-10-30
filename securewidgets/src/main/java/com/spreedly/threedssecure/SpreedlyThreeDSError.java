package com.spreedly.threedssecure;


/**
 * Class for handling errors from the SpreedlyThreeDS flow
 */
public class SpreedlyThreeDSError {

    /**
     * The error message
     */
    public String message;
    /**
     * The error type
     *
     * @see SpreedlyThreeDSErrorType
     */
    public SpreedlyThreeDSErrorType type;

    /**
     * Constructor that creates a new SpreedlyThreeDSError from a given type, and string message
     *
     * @param type    the SpreedlyThreeDSErrorType that most closely describes the type of error
     * @param message a string containing the error message
     */
    SpreedlyThreeDSError(SpreedlyThreeDSErrorType type, String message) {
        this.message = message;
        this.type = type;
    }

    /**
     * An empty constructor
     */
    SpreedlyThreeDSError() {
    }

    /**
     * Creates a new SpreedlyThreeDSError from a given type, and Error
     *
     * @param type  the SpreedlyThreeDSErrorType that most closely describes the type of error
     * @param error an Error produced by the SpreedlyThreeDS flow
     */
    SpreedlyThreeDSError(SpreedlyThreeDSErrorType type, Error error) {
        this.message = error.getMessage();
        this.type = type;
    }

    /**
     * Creates a new SpreedlyThreeDSError from a given type, and exception thrown by the SpreedlyThreeDS flow
     *
     * @param type the SpreedlyThreeDSErrorType that most closely describes the type of error
     * @param e    an Exception thrown by the SpreedlyThreeDS flow
     */
    public SpreedlyThreeDSError(SpreedlyThreeDSErrorType type, Exception e) {
        this.type = type;
        this.message = e.getMessage();
    }

    /**
     * An enum containing likely errors produced by the SpreedlyThreeDSFlow, including UNKNOWN_ERROR
     */
    public enum SpreedlyThreeDSErrorType {
        INVALID_INPUT,
        PROTOCOL_ERROR,
        RUNTIME_ERROR,
        UNKNOWN_ERROR
    }
}