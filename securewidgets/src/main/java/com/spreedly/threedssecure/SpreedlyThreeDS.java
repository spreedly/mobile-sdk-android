package com.spreedly.threedssecure;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.seglan.threeds.sdk.ConfigParameters;
import com.seglan.threeds.sdk.SeglanThreeDS2Service;

public class SpreedlyThreeDS {
    private final SeglanThreeDS2Service threeDS2Service = new SeglanThreeDS2Service();
    Activity activity;

    public SpreedlyThreeDS(Context context, Activity activity) {
        this.activity = activity;
        threeDS2Service.initialize(context, new ConfigParameters(), "en_US", null);
    }

    public SpreedlyThreeDS(@NonNull Context context, @NonNull Activity activity, @Nullable Bundle state) {
        this(context, activity);
        loadState(state);
    }

    public void loadState(Bundle data) {
        if (data != null) {
            threeDS2Service.a = data.getBoolean("a");
            threeDS2Service.c = data.getString("c");
            threeDS2Service.e = data.getString("e");
            threeDS2Service.f = data.getString("f");
        }
    }

    @NonNull
    public Bundle saveState() {
        Bundle dest = new Bundle();
        dest.putBoolean("a", threeDS2Service.a);
        dest.putString("c", threeDS2Service.c);
        dest.putString("e", threeDS2Service.e);
        dest.putString("f", threeDS2Service.f);
        return dest;
    }

    public SpreedlyThreeDSTransactionRequest createTransactionRequest() {
        return new SpreedlyThreeDSTransactionRequest(threeDS2Service, threeDS2Service.createTransaction("F000000000", "2.1.0"), activity);
    }


}