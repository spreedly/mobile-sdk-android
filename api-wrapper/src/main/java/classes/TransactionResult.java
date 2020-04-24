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

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
