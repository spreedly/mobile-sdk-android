package com.spreedly.securewidgets;

import com.spreedly.client.models.enums.BankAccountHolderType;
import com.spreedly.client.models.enums.BankAccountType;

import java.util.HashMap;
import java.util.ListResourceBundle;

public class AccountTypeBundle extends ListResourceBundle {
    private HashMap<String, Object> localizedMap;

    public AccountTypeBundle() {
        super();
        localizedMap = new HashMap<>();
    }

    @Override
    protected Object[][] getContents() {
        {
            return new Object[][]{
                    // LOCALIZE THE SECOND STRING OF EACH ARRAY (e.g., "OK")
                    {"checkingKey", "Checking"},
                    {"savingsKey", "Savings"},
                    {"personalKey", "Personal"},
                    {"businessKey", "Business"}
                    // END OF MATERIAL TO LOCALIZE
            };
        }
    }

    public String getContent(BankAccountHolderType key) {
        String value = this.getString(key.toString() + "Key");
        localizedMap.put(value, key);
        return value;
    }

    public String getContent(BankAccountType key) {
        String value = this.getString(key.toString() + "Key");
        localizedMap.put(value, key);
        return value;
    }

    public BankAccountType getAccountType(String key) {
        return (BankAccountType) localizedMap.get(key);
    }

    public BankAccountHolderType getAccountHolderType(String key) {
        return (BankAccountHolderType) localizedMap.get(key);
    }


}
