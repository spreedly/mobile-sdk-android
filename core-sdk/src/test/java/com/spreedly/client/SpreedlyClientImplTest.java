package com.spreedly.client;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertNull;

public class SpreedlyClientImplTest {
    @Test
    public void parseDateReturnsNullWithBadDate() {
        SpreedlyClientImpl spreedlyClient = new SpreedlyClientImpl(TestCredentials.getUser(), TestCredentials.getPassword(), true);
        Date date = spreedlyClient.parseDate(null);
        assertNull(date);
    }
}