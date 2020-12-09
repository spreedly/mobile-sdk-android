package com.spreedly.threedssecure;

import android.app.Activity;
import android.content.Context;

import com.seglan.threeds.sdk.ConfigParameters;
import com.seglan.threeds.sdk.SeglanThreeDS2Service;
import com.seglan.threeds.sdk.customization.UiCustomization;
import com.spreedly.client.models.enums.CardBrand;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SpreedlyThreeDS {
    private final SeglanThreeDS2Service threeDS2Service = new SeglanThreeDS2Service();
    Activity activity;
    final boolean test;

    public SpreedlyThreeDS(@NonNull Context context, @NonNull Activity activity, @Nullable SpreedlyThreeDSTheme theme, boolean test) {
        this.activity = activity;
        this.test = test;
        ConfigParameters configParameters = new ConfigParameters();
        configParameters.addParam("CONF", "ENV", test ? "test" : "prod");
        threeDS2Service.initialize(context, configParameters, "en_US", theme != null ? theme.toUiCustomization() : new UiCustomization());
    }

    @SuppressWarnings("DuplicateBranchesInSwitch")
    static String cardTypeToDirectoryServerId(CardBrand cardType) {
        switch (cardType) {
            case visa:
                return "A000000003";
            case mastercard:
                return "A000000004";
            case maestro:
                return "A000000005";
            case americanExpress:
                return "A000000025";
            default:
                return "A000000004";
        }
    }

    public SpreedlyThreeDSTransactionRequest createTransactionRequest(CardBrand cardType) {
        return new SpreedlyThreeDSTransactionRequest(threeDS2Service, threeDS2Service.createTransaction(cardTypeToDirectoryServerId(cardType), "2.1.0"), activity);
    }
}