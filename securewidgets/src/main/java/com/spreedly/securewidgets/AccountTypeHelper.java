package com.spreedly.securewidgets;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.spreedly.client.models.enums.AccountHolderType;
import com.spreedly.client.models.enums.AccountType;

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
    public String getString(@NonNull AccountHolderType type) {
        String string = context.getString(localizedMap.get(type.toString()));
        enumdMap.put(string, type);
        return string;
    }

    @Nullable
    public String getString(@NonNull AccountType type) {
        String string = context.getString(localizedMap.get(type.toString()));
        enumdMap.put(string, type);
        return string;
    }

    @Nullable
    public AccountType getAccountType(@NonNull String string) {
        return (AccountType) enumdMap.get(string);
    }

    @Nullable
    public AccountHolderType getAccountHolderType(@NonNull String string) {
        return (AccountHolderType) enumdMap.get(string);
    }


}

