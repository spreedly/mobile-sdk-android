package com.spreedly.express;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.spreedly.client.SpreedlyClient;

import java.io.Serializable;

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

    public Intent buildIntent(Context context) {
        Intent intent = new Intent(context, ExpressPaymentActivity.class);
        intent.putExtra("client", (Serializable) client);
        intent.putExtra("options", options);
        return intent;
    }

    public void show(Activity context, int resultCode) {
        context.startActivityForResult(buildIntent(context), resultCode);
    }

    public void showDialog(@NonNull FragmentManager fm, @Nullable String tag, int requestCode) {
        ExpressPaymentFragment fragment = ExpressPaymentFragment.newInstance(client, options);
        fragment.setTargetFragment(null, requestCode);
        fragment.show(fm, tag);
    }

    public void showDialog(@NonNull FragmentManager fm, @Nullable String tag, Fragment target, int requestCode) {
        ExpressPaymentFragment fragment = ExpressPaymentFragment.newInstance(client, options);
        fragment.setTargetFragment(target, requestCode);
        fragment.show(fm, tag);
    }
}
