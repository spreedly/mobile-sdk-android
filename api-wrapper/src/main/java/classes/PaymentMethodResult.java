package classes;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PaymentMethodResult {
    String token;
    String storageState;
    boolean test;
    String paymentMethodType; //maybe enum
    ArrayList errors;
    String lastFourDigits;
    String firstFourDigits;
    String cvv;
    String number;
    String month;
    String year;

    public PaymentMethodResult(String token, String storageState, boolean test, String paymentMethodType, ArrayList errors, String lastFourDigits, String firstFourDigits, String cvv, String number, String month, String year){
        this.token = token;
        this.storageState = storageState;
        this.test = test;
        this.paymentMethodType = paymentMethodType;
        this.errors = errors;
        this.lastFourDigits = lastFourDigits;
        this.firstFourDigits = firstFourDigits;
        this.cvv = cvv;
        this.number = number;
        this.month = month;
        this.year = year;
    }

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

    public String getLastFourDigits() {
        return lastFourDigits;
    }

    public String getFirstFourDigits() {
        return firstFourDigits;
    }

    public String getCvv() {
        return cvv;
    }

    public String getNumber() {
        return number;
    }

    public ArrayList getErrors() {
        return errors;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

}
