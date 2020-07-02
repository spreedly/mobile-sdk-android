package com.spreedly.express;

import com.spreedly.client.SpreedlyClient;

public class ExpressBuilder {
    private final SpreedlyClient client;
    private final PaymentOptions options;

    public ExpressBuilder(SpreedlyClient client, PaymentOptions options) {
        this.client = client;
        this.options = options;
    }


}
