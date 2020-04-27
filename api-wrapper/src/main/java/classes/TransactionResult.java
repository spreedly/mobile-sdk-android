package classes;

import java.util.Date;

public class TransactionResult<T> {
    String token;
    Date createdAt;
    Date updatedAt;
    boolean succeeded;
    String transactionType; // maybe enum
    boolean retained;
    String state; // maybe enum
    String messageKey; // localization?
    String message;
    T result;

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
    }
    public String getToken() {
        return token;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public T getResult() {
        return result;
    }


    public boolean isSucceeded() {
        return succeeded;
    }


    public String getMessageKey() {
        return messageKey;
    }

    public boolean isRetained() {
        return retained;
    }

}
