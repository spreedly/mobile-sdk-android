package com.spreedly.sdk_sample.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.Address;
import com.spreedly.client.models.enums.CardBrand;
import com.spreedly.express.ExpressBuilder;
import com.spreedly.express.PaymentMethodItem;
import com.spreedly.express.PaymentOptions;
import com.spreedly.express.PaymentType;
import com.spreedly.sdk_sample.R;

import java.util.ArrayList;
import java.util.List;

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
            builder.showDialog(getParentFragmentManager(), null, this, 1000);
        });
    }

    @NonNull
    private ExpressBuilder getExpressBuilder() {
        PaymentMethodItem card1 = new PaymentMethodItem("sample_token_1", CardBrand.visa, "Visa XXXX");
        PaymentMethodItem card2 = new PaymentMethodItem("sample_token_2", CardBrand.mastercard, "Mastercard XXXX");
        PaymentMethodItem card3 = new PaymentMethodItem("sample_token_3", CardBrand.americanExpress, "Amex XXXX");
        PaymentMethodItem card4 = new PaymentMethodItem("sample_token_4", CardBrand.dinersClub, "Diners XXXX");
        List<PaymentMethodItem> paymentMethodItems = new ArrayList<>();
        paymentMethodItems.add(card1);
        paymentMethodItems.add(card2);
        paymentMethodItems.add(card3);
        paymentMethodItems.add(card4);
        SpreedlyClient client = SpreedlyClient.newInstance("XsQXqPtrgCOnpexSwyhzN9ngr2c", "ghEGueczUT4BhJv54K24G6B4Oy9yWaM5R4dR2yt5gRsx3xnwbZE0OZ0mRg2zyI5g", true);
        PaymentOptions options = new PaymentOptions();
        options.setButtonText("Pay now");
        options.setPaymentType(PaymentType.ALL);
        options.setPaymentMethodItemList(paymentMethodItems);
        options.setHeader(R.layout.merchant_header);
        options.setMerchantText("<div style=\"text-align: center;\">\n" +
                "<h1 style=\"text-align: center;\">$20.12</h1>\n" +
                "Pass in your customized merchant text.</div>");
        options.setBillingAddress(new Address("555 Main St", "", "Anytown", "WA", "98006", "United States", null));

        return new ExpressBuilder(client, options);
    }
}
