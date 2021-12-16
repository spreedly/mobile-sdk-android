package com.spreedly.express;

import static org.junit.Assert.assertNotNull;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.spreedly.client.SpreedlyClient;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Base64;
import java.util.Properties;

@RunWith(AndroidJUnit4.class)
public class PlatformTest {

    @Test
    public void testGetPlatformData() throws Exception {
        Properties x = System.getProperties();
        for (final String key : x.stringPropertyNames())
            Log.i("ENV", key + ": " + x.getProperty(key));

        SpreedlyClient spreedlyClient = SpreedlyClient.newInstance("", "", true);
        final String json = new String(Base64.getDecoder().decode(spreedlyClient.getPlatformData()));
        JSONObject data = new JSONObject(json);
        Log.i("TEST", data.toString(2));

        assertNotNull(data.getString("core-version"));
    }
}
