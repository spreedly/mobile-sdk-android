package com.spreedly.threedssecure;

import android.app.Activity;
import android.content.Context;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import static androidx.test.runner.lifecycle.Stage.RESUMED;
import static org.junit.Assert.assertNotNull;

public class SpreedlyThreeDSTest {

    SpreedlyThreeDS spreedlyThreeDS;

    public static Activity getCurrentActivity() {
        final Activity[] currentActivity = {null};
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance()
                        .getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()) {
                    currentActivity[0] = (Activity) resumedActivities.iterator().next();
                }
            }
        });
        return currentActivity[0];
    }

    @Before
    public void init() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        spreedlyThreeDS = new SpreedlyThreeDS(context, getCurrentActivity(), true);
    }

    @Test
    public void canCreateSpreedlyThreeDS() {
        assertNotNull(spreedlyThreeDS);
    }

    @Test
    public void canCreateTransactionRequest() {
        SpreedlyThreeDSTransactionRequest request = spreedlyThreeDS.createTransactionRequest();
        assertNotNull(request);
    }

    @Test
    public void canSerializeData() {
        SpreedlyThreeDSTransactionRequest request = spreedlyThreeDS.createTransactionRequest();
        JSONObject json = request.serialize();
        String string = json.toString();
        assertNotNull(json.toString());
    }

    @Test
    public void canDoChallenge() {
        SpreedlyThreeDSTransactionRequest request = spreedlyThreeDS.createTransactionRequest();
        request.delegate = new SpreedlyThreeDSTransactionRequestListener() {
            @Override
            public void success(@NonNull String status) {

            }

            @Override
            public void cancelled() {

            }

            @Override
            public void timeout() {

            }

            @Override
            public void error(@NonNull SpreedlyThreeDSError error) {

            }
        };
        request.doChallenge(request.serialize());
    }
}
