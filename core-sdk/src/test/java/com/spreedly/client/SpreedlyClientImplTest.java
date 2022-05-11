package com.spreedly.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.json.JSONObject;
import org.junit.Test;

import java.util.Base64;
import java.util.Date;

public class SpreedlyClientImplTest {
    @Test
    public void parseDateReturnsNullWithBadDate() {
        SpreedlyClientImpl spreedlyClient = new SpreedlyClientImpl(TestCredentials.getUser(), TestCredentials.getPassword(), true);
        Date date = spreedlyClient.parseDate(null);
        assertNull(date);
    }

    @Test
    public void testGetPlatformData() {
        SpreedlyClientImpl spreedlyClient = new SpreedlyClientImpl(TestCredentials.getUser(), TestCredentials.getPassword(), true);
        final String json = new String(Base64.getDecoder().decode(spreedlyClient.getPlatformLocalData()));
        System.out.println("-------");
        System.out.println(json);
        System.out.println("-------");
        JSONObject data = new JSONObject(json);
        System.out.println(data.toString(2));

        assertNotNull(data.getString("core-version"));
    }
}