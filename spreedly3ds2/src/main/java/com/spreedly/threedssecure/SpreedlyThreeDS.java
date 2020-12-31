package com.spreedly.threedssecure;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.seglan.threeds.sdk.ConfigParameters;
import com.seglan.threeds.sdk.SeglanThreeDS2Service;
import com.seglan.threeds.sdk.customization.UiCustomization;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SpreedlyThreeDS {
    private final SeglanThreeDS2Service threeDS2Service = new SeglanThreeDS2Service();
    Activity activity;
    final boolean test;
    /**
     * debugLogging
     * <p>
     * Normally you do not need to touch this, but this is useful, if you are working with Spreedly
     * support to diagnose an issue.
     */
    public boolean debugLogging = false;

    public SpreedlyThreeDS(@NonNull Context context, @NonNull Activity activity, @Nullable SpreedlyThreeDSTheme theme, boolean test) {
        this.activity = activity;
        this.test = test;
        ConfigParameters configParameters = new ConfigParameters();
        configParameters.addParam("CONF", "ENV", test ? "test" : "prod");
        threeDS2Service.initialize(context, configParameters, "en_US", theme != null ? theme.toUiCustomization() : new UiCustomization());
    }

    @SuppressWarnings("DuplicateBranchesInSwitch")
    static String cardTypeToDirectoryServerId(String cardType) {
        switch (cardType) {
            case "visa":
                return "A000000003";
            case "master":
                return "A000000004";
            case "maestro":
                return "A000000005";
            case "american_express":
                return "A000000025";
            default:
                return "A000000004";
        }
    }

    /**
     * createTransactionRequest
     * <p>
     * After you've tokenized the payment info, use this to start the challenge process.
     * <p>
     * Pass in the `cardType` returned from the spreedly api, then call `serialize()` to get the
     * device info that Spreedly needs to process a 3ds2 challenge. If Spreedly responds from the
     * payment api with 'sca_authentication' data, then you simply send that data back to the client,
     * and the client passes the data via `doChallenge()`.
     *
     * @param cardType the string card_type from the spreedly api.
     * @return a transaction request for a 3ds2 challenge retain this until you either need to do
     * a challenge or the payment has been processed.
     */
    public SpreedlyThreeDSTransactionRequest createTransactionRequest(String cardType) {
        return new SpreedlyThreeDSTransactionRequest(this, threeDS2Service.createTransaction(cardTypeToDirectoryServerId(cardType), "2.1.0"), activity);
    }

    void log(String context, String... lines) {
        if (debugLogging) {
            for (final String line : lines) {
                Log.i(context, line);
            }
        }
    }

    void log(String context, String lines) {
        if (debugLogging) {
            log(context, lines.split("\n"));
        }
    }

    void log(String context, JSONObject lines) {
        if (debugLogging) {
            try {
                log(context, lines.toString(2));
            } catch (JSONException e) {
                // this really shouldn't happen
                e.printStackTrace();
            }
        }
    }
}