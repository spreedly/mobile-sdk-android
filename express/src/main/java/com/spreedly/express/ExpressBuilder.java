package com.spreedly.express;

import com.spreedly.client.SpreedlyClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ExpressBuilder {
    public static final String EXTRA_PAYMENT_METHOD_TOKEN = "com.spreedly.express.ExpressBuilder.EXTRA_PAYMENT_METHOD_TOKEN";
    public static final String EXTRA_PAYMENT_METHOD_TRANSACTION = "com.spreedly.express.ExpressBuilder.EXTRA_PAYMENT_METHOD_TRANSACTION";
    public static final String EXTRA_STORED_PAYMENT_METHOD = "com.spreedly.express.ExpressBuilder.EXTRA_STORED_PAYMENT_METHOD";

    @NonNull
    private final SpreedlyClient client;
    @Nullable
    private final PaymentOptions options;

    public ExpressBuilder(@Nullable SpreedlyClient client, @NonNull PaymentOptions options) {
        this.client = client;
        this.options = options;
    }

    public void showDialog(@NonNull FragmentManager fm, @NonNull int requestCode) {
        ExpressPaymentFragment fragment = ExpressPaymentFragment.newInstance(client, options);
        fragment.setTargetFragment(null, requestCode);
        fragment.show(fm, null);
    }

    public void showDialog(@NonNull FragmentManager fm, @NonNull Fragment target, @NonNull int requestCode) {
        ExpressPaymentFragment fragment = ExpressPaymentFragment.newInstance(client, options);
        fragment.setTargetFragment(target, requestCode);
        fragment.show(fm, null);
    }

    public void showDialog(@NonNull FragmentManager fm, @Nullable String tag, @NonNull int requestCode) {
        ExpressPaymentFragment fragment = ExpressPaymentFragment.newInstance(client, options);
        fragment.setTargetFragment(null, requestCode);
        fragment.show(fm, tag);
    }

    public void showDialog(@NonNull FragmentManager fm, @Nullable String tag, @NonNull Fragment target, @NonNull int requestCode) {
        ExpressPaymentFragment fragment = ExpressPaymentFragment.newInstance(client, options);
        fragment.setTargetFragment(target, requestCode);
        fragment.show(fm, tag);
    }
}
