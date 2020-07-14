package com.spreedly.express;

import android.content.Intent;
import android.os.Bundle;

import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import io.reactivex.rxjava3.functions.Consumer;

public class ExpressPaymentActivity extends AppCompatActivity {
    private SpreedlyClient client;
    private PaymentOptions options;


    private Consumer<TransactionResult<PaymentMethodResult>> submitCallback = result -> {
        Intent data = new Intent();
        data.putExtra(ExpressBuilder.EXTRA_PAYMENT_METHOD_TOKEN, result.result.token);
        data.putExtra(ExpressBuilder.EXTRA_PAYMENT_METHOD_TRANSACTION, result);
        setResult(RESULT_OK, data);
        finish();
    };
    private Consumer<StoredCard> savedCardCallback = storedCard -> {
        Intent data = new Intent();
        data.putExtra(ExpressBuilder.EXTRA_PAYMENT_METHOD_TOKEN, storedCard.token);
        data.putExtra(ExpressBuilder.EXTRA_STORED_PAYMENT_METHOD, storedCard);
        setResult(RESULT_OK, data);
        finish();
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = (SpreedlyClient) getIntent().getSerializableExtra("client");
        options = (PaymentOptions) getIntent().getSerializableExtra("options");

        setContentView(new CoordinatorLayout(this));
        new ExpressBuilder(client, options).showDialog(getSupportFragmentManager(), null, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}