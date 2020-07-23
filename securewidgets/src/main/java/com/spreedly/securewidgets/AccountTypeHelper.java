package com.spreedly.securewidgets;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.spreedly.client.models.enums.BankAccountHolderType;
import com.spreedly.client.models.enums.BankAccountType;

import java.util.HashMap;

public class AccountTypeHelper {
    @NonNull
    private HashMap<String, Object> enumdMap;
    @NonNull
    private HashMap<String, Integer> localizedMap;
    @NonNull
    private Context context;


    public AccountTypeHelper(@NonNull Context context) {
        this.context = context;
        enumdMap = new HashMap<>();
        localizedMap = new HashMap<>();
        localizedMap.put("checking", R.string.checking);
        localizedMap.put("savings", R.string.savings);
        localizedMap.put("personal", R.string.personal);
        localizedMap.put("business", R.string.business);
    }

    @Nullable
    public String getString(@NonNull BankAccountHolderType type) {
        String string = context.getString(localizedMap.get(type.toString()));
        enumdMap.put(string, type);
        return string;
    }

    @Nullable
    public String getString(@NonNull BankAccountType type) {
        String string = context.getString(localizedMap.get(type.toString()));
        enumdMap.put(string, type);
        return string;
    }

    @Nullable
    public BankAccountType getBankAccountType(@NonNull String string) {
        return (BankAccountType) enumdMap.get(string);
    }

    @Nullable
    public BankAccountHolderType getBankAccountHolderType(@NonNull String string) {
        return (BankAccountHolderType) enumdMap.get(string);
    }


}

