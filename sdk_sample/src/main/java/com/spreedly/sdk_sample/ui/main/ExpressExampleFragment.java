package com.spreedly.sdk_sample.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.enums.CardBrand;
import com.spreedly.express.ExpressBuilder;
import com.spreedly.express.PaymentMethodItem;
import com.spreedly.express.PaymentMethodType;
import com.spreedly.express.PaymentOptions;
import com.spreedly.express.PaymentType;
import com.spreedly.sdk_sample.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ExpressExampleFragment extends Fragment {
    @NonNull
    public static ExpressExampleFragment newInstance() {
        return new ExpressExampleFragment();
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.express_example_fragment, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Log.i("Spreedly", "Token: " + data.getStringExtra(ExpressBuilder.EXTRA_PAYMENT_METHOD_TOKEN));
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().findViewById(R.id.pay_now_fragment).setOnClickListener(v -> {
            ExpressBuilder builder = getExpressBuilder();
            builder.showDialog(getParentFragmentManager(), this, 1000);
        });
    }

    @NonNull
    private ExpressBuilder getExpressBuilder() {
        PaymentOptions options = new PaymentOptions();
        options.setButtonText("Pay now");
        options.setPaymentType(PaymentType.ALL);
        options.setHeader(R.layout.merchant_header);

        // simple html to display payment info see Html.fromHtml()
        options.setMerchantText("<div style=\"text-align: center;\">\n" +
                "<h1 style=\"text-align: center;\">$20.12</h1>\n" +
                "Pass in your customized merchant text.</div>");

        // if the user has pre-saved card numbers add them
        List<PaymentMethodItem> paymentMethodItems = List.of(new PaymentMethodItem[]{
                new PaymentMethodItem("sample_token_1", PaymentMethodType.CARD, "Visa XXXX", CardBrand.visa),
                new PaymentMethodItem("sample_token_2", PaymentMethodType.CARD, "Mastercard XXXX", CardBrand.mastercard),
                new PaymentMethodItem("sample_token_3", PaymentMethodType.BANK, "Account XXXX", null),
                new PaymentMethodItem("sample_token_4", PaymentMethodType.THIRD_PARTY, "Third Party", null)
        });
        options.setPaymentMethodItemList(paymentMethodItems);

        SpreedlyClient client = SpreedlyClient.newInstance("XsQXqPtrgCOnpexSwyhzN9ngr2c", true);
        return new ExpressBuilder(client, options);
    }
}
