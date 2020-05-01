package com.spreedly.client.models.results;

import java.util.ArrayList;
import java.util.Date;

public class TransactionResult<T> {
    public final String token;
    public final Date createdAt;
    public final Date updatedAt;
    public final boolean succeeded;
    public final String transactionType; // maybe enum
    public final boolean retained;
    public final String state; // maybe enum
    public final String messageKey; // localization?
    public final String message;
    public final ArrayList<SpreedlyError> errors;
    public final T result;

    public TransactionResult(String token, Date createdAt, Date updatedAt, boolean succeeded, String transactionType, boolean retained, String state, String messageKey, String message, T result) {
        this.token = token;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.succeeded = succeeded;
        this.transactionType = transactionType;
        this.retained = retained;
        this.state = state;
        this.messageKey = messageKey;
        this.message = message;
        this.result = result;
        this.errors = null;
    }

    public TransactionResult(ArrayList<SpreedlyError> errors) {
        this.errors = errors;
        this.token = null;
        this.createdAt = null;
        this.updatedAt = null;
        this.succeeded = false;
        this.transactionType = null;
        this.retained = false;
        this.state = null;
        this.messageKey = null;
        this.message = null;
        this.result = null;
    }
}
