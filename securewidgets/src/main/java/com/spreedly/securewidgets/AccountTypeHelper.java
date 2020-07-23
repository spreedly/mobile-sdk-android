package com.spreedly.securewidgets;

import android.content.Context;

import com.spreedly.client.models.enums.BankAccountHolderType;
import com.spreedly.client.models.enums.BankAccountType;

import java.util.HashMap;

public class AccountTypeHelper {
    private HashMap<String, Object> enumdMap;
    private HashMap<String, Integer> localizedMap;
    private Context context;


    public AccountTypeHelper(Context context) {
        this.context = context;
        enumdMap = new HashMap<>();
        localizedMap = new HashMap<>();
        localizedMap.put("checking", R.string.checking);
        localizedMap.put("savings", R.string.savings);
        localizedMap.put("personal", R.string.personal);
        localizedMap.put("business", R.string.business);
    }

    public String getString(BankAccountHolderType type) {
        String string = context.getString(localizedMap.get(type.toString()).intValue());
        enumdMap.put(string, type);
        return string;
    }

    public String getString(BankAccountType type) {
        String string = context.getString(localizedMap.get(type.toString()).intValue());
        enumdMap.put(string, type);
        return string;
    }

    public BankAccountType getBankAccountType(String string) {
        return (BankAccountType) enumdMap.get(string);
    }

    public BankAccountHolderType getBankAccountHolderType(String string) {
        return (BankAccountHolderType) enumdMap.get(string);
    }


}

