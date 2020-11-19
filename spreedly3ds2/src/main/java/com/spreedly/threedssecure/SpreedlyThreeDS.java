package com.spreedly.threedssecure;

import android.app.Activity;
import android.content.Context;

import com.seglan.threeds.sdk.ConfigParameters;
import com.seglan.threeds.sdk.SeglanThreeDS2Service;

public class SpreedlyThreeDS {
    private final SeglanThreeDS2Service threeDS2Service = new SeglanThreeDS2Service();
    Activity activity;

    public SpreedlyThreeDS(Context context, Activity activity, boolean test) {
        this.activity = activity;
        ConfigParameters configParameters = new ConfigParameters();
        configParameters.addParam("CONF", "ENV", test ? "test" : "prod");
        threeDS2Service.initialize(context, configParameters, "en_US", null);
    }

    public SpreedlyThreeDSTransactionRequest createTransactionRequest() {
        return new SpreedlyThreeDSTransactionRequest(threeDS2Service, threeDS2Service.createTransaction("F000000000", "2.1.0"), activity);
    }
}