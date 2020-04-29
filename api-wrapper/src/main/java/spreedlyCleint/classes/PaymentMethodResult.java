package spreedlyCleint.classes;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PaymentMethodResult {
    String token;
    String storageState;
    boolean test;
    String paymentMethodType; //maybe enum
    ArrayList errors;


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
