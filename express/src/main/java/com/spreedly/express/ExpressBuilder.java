package com.spreedly.express;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.spreedly.client.SpreedlyClient;

public class ExpressBuilder {
    @NonNull
    private final SpreedlyClient client;
    @Nullable
    private final PaymentOptions options;
    @Nullable
    public ExpressFragment fragment;
    @Nullable
    public ExpressActivity activity;

    public ExpressBuilder(@Nullable SpreedlyClient client, @NonNull PaymentOptions options) {
        this.client = client;
        this.options = options;
    }

    public void buildActivity() {

    }

    public void buildFragment() {
        fragment = ExpressFragment.newInstance(options, client);
    }



}
