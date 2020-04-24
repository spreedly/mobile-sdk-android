package classes;

import java.util.Date;

public class TransactionResult<T> {
    String token;
    Date created_at;
    Date updated_at;
    boolean succeeded;
    String transaction_type; // maybe enum
    boolean retained;
    String state; // maybe enum
    String message_key; // localization?
    String message;
    T result;
}
