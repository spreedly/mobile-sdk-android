package spreedlyclient.classes;

import java.util.ArrayList;
import java.util.Date;

public class PaymentMethodResult {
    String token;
    String storageState;
    boolean test;
    String paymentMethodType; //maybe enum
    ArrayList errors;
    Date createdAt;
    Date updatedAt;
    String email;


    public String getToken() {
        return token;
    }

    public String getStorageState() {
        return storageState;
    }

    public boolean isTest() {
        return test;
    }

    public String getPaymentMethodType() {
        return paymentMethodType;
    }
    public ArrayList getErrors() {
        return errors;
    }

}
