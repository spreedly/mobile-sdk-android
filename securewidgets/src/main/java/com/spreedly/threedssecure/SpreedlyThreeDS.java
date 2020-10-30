package com.spreedly.threedssecure;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.seglan.threeds.sdk.ConfigParameters;
import com.seglan.threeds.sdk.SeglanThreeDS2Service;

/**
 * Class to manage the Seglan 3ds2 flow
 * Initializes <code>SeglandThreeDS2Service</code> and contains methods for continuing the flow
 *
 * @see SpreedlyThreeDSTransactionRequest
 * @see SpreedlyThreeDSTransactionRequestListener
 * @see SeglanThreeDS2Service
 */
public class SpreedlyThreeDS {
    /***
     * The Seglan 3ds2 service
     */
    private final SeglanThreeDS2Service threeDS2Service = new SeglanThreeDS2Service();
    /**
     * The activity that implements SpreedlyThreeDS
     */
    Activity activity;

    /**
     * SpreedlyThreeDS constructor
     * Use if this is the first time initializing SpreedlyThreeDS, or if you don't not have a threeDS2Service state to load
     * Initializes threeDS2service
     *
     * @param context  the context of the activity using SpreedlyThreeDS
     * @param activity the activity initializing SpreedlyThreeDS
     */
    public SpreedlyThreeDS(Context context, Activity activity) {
        this.activity = activity;
        threeDS2Service.initialize(context, new ConfigParameters(), "en_US", null);
    }

    /**
     * SpreedlyThreeDS constructor
     * Use if you have a specific threeDS2Service state (previously created) you wish to load
     * Initializes threeDS2service and loads a previously saved state
     *
     * @param context  the Context of the activity using SpreedlyThreeDS
     * @param activity the Activity initializing SpreedlyThreeDS
     * @param state    A Bundle containing data from a previously initialized SpreedlyThreeDS instance
     */
    public SpreedlyThreeDS(@NonNull Context context, @NonNull Activity activity, @Nullable Bundle state) {
        this(context, activity);
        loadState(state);
    }

    /**
     * Loads a previously created Bundle of data into the threeDS2Service
     *
     * @param data a Bundle holding previously stored threeDS2Service state data
     * @see #saveState()
     */
    public void loadState(Bundle data) {
        if (data != null) {
            threeDS2Service.a = data.getBoolean("a");
            threeDS2Service.c = data.getString("c");
            threeDS2Service.e = data.getString("e");
            threeDS2Service.f = data.getString("f");
        }
    }

    /**
     * Saves state from threeDS2Service into a Bundle to be loaded into another instatiation of SpreedlyThreeDS
     *
     * @return a Bundle containing the state for the threeDS2Service
     * @see #loadState(Bundle)
     */
    @NonNull
    public Bundle saveState() {
        Bundle dest = new Bundle();
        dest.putBoolean("a", threeDS2Service.a);
        dest.putString("c", threeDS2Service.c);
        dest.putString("e", threeDS2Service.e);
        dest.putString("f", threeDS2Service.f);
        return dest;
    }

    /**
     * Creates a new instance of #SpreedlyThreeDSTransactionRequest to continue the 3DS2 flow
     *
     * @return a new SpreedlyThreeDSTransactionRequest
     * @see SpreedlyThreeDSTransactionRequest
     */
    public SpreedlyThreeDSTransactionRequest createTransactionRequest() {
        return new SpreedlyThreeDSTransactionRequest(threeDS2Service, threeDS2Service.createTransaction("F000000000", "2.1.0"), activity);
    }


}