package com.spreedly.threedssecure;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static androidx.test.runner.lifecycle.Stage.RESUMED;
import static org.junit.Assert.assertEquals;
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
        spreedlyThreeDS = new SpreedlyThreeDS(context, getCurrentActivity());
    }

    @Test
    public void canCreateSpreedlyThreeDS() {
        assertNotNull(spreedlyThreeDS);
    }

    @Test
    public void canSaveAndLoadState() {
        Bundle state = spreedlyThreeDS.saveState();
        SpreedlyThreeDS newInstance = new SpreedlyThreeDS(InstrumentationRegistry.getInstrumentation().getContext(), getCurrentActivity(), state);
        Bundle newState = newInstance.saveState();
        assertEquals(state.getString("f"), newState.getString("f"));
    }

    @Test
    public void canCreateTransactionRequest() {
        SpreedlyThreeDSTransactionRequest request = spreedlyThreeDS.createTransactionRequest();
        assertNotNull(request);
    }

    @Test
    public void canSerializeData() {
        SpreedlyThreeDSTransactionRequest request = spreedlyThreeDS.createTransactionRequest();
        JSONArray json = request.serialize();
        String string = json.toString();
        assertNotNull(json.toString());
    }
}
