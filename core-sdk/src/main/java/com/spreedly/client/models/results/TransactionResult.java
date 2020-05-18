package com.spreedly.client.models.results;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.annotations.Nullable;

public class TransactionResult<T> {
    public final @Nullable String token;
    public final @Nullable Date createdAt;
    public final @Nullable Date updatedAt;
    public final boolean succeeded;
    public final @Nullable String transactionType; // maybe enum
    public final boolean retained;
    public final @Nullable String state; // maybe enum
    public final @Nullable String messageKey; // localization?
    public final @Nullable String message;
    public final @Nullable List<SpreedlyError> errors;
    public final @Nullable T result;

    public TransactionResult(
            @Nullable String token,
            @Nullable Date createdAt,
            @Nullable Date updatedAt,
            boolean succeeded,
            @Nullable String transactionType,
            boolean retained,
            @Nullable String state,
            @Nullable String messageKey,
            @Nullable String message,
            @Nullable List<SpreedlyError> errors,
            @Nullable T result) {
        this.token = token;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.succeeded = succeeded;
        this.transactionType = transactionType;
        this.retained = retained;
        this.state = state;
        this.messageKey = messageKey;
        this.message = (message == null || message.isEmpty()) && errors != null && !errors.isEmpty() ? errors.get(0).message : message;
        this.result = result;
        this.errors = errors;
    }
}
