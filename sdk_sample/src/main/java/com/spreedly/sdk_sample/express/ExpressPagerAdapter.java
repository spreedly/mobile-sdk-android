package com.spreedly.sdk_sample.express;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.Address;
import com.spreedly.client.models.enums.CardBrand;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.TransactionResult;
import com.spreedly.express.ExpressBuilder;
import com.spreedly.express.PaymentOptions;
import com.spreedly.express.PaymentType;
import com.spreedly.express.StoredCard;
import com.spreedly.sdk_sample.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class ExpressPagerAdapter extends FragmentPagerAdapter {
    public ExpressPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Menu";
            case 1:
                return "Bank Account";
            case 3:
                return "Credit Card";
        }
        throw new IndexOutOfBoundsException();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                StoredCard card1 = new StoredCard("sample_token_1", CardBrand.visa, "Visa XXXX");
                StoredCard card2 = new StoredCard("sample_token_2", CardBrand.mastercard, "Mastercard XXXX");
                List<StoredCard> storedCards = new ArrayList<>();
                storedCards.add(card1);
                storedCards.add(card2);
                SpreedlyClient client = SpreedlyClient.newInstance("XsQXqPtrgCOnpexSwyhzN9ngr2c", "ghEGueczUT4BhJv54K24G6B4Oy9yWaM5R4dR2yt5gRsx3xnwbZE0OZ0mRg2zyI5g", true);
                PaymentOptions options = new PaymentOptions();
                options.setButtonText("Pay now");
                options.setPaymentType(PaymentType.ALL);
                options.setStoredCardList(storedCards);
                options.setMerchantTitle("Lucy's Shop");
                options.setMerchantIcon(R.drawable.ic_rowing);
                options.setMerchantText("<div><h1>My First Heading</h1>\n" +
                        "<p>My first paragraph.</p></div>");
                options.setBillingAddress(new Address("555 Main St", "", "Anytown", "WA", "98006", "United States", null));
                options.setSubmitCallback(new SingleObserver<TransactionResult<PaymentMethodResult>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        Log.i("Spreedly", "Subscribed");
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull TransactionResult<PaymentMethodResult> paymentMethodResultTransactionResult) {
                        Log.i("Spreedly", "Token: " + paymentMethodResultTransactionResult.result.token);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e("Spreedly", e.getMessage());
                    }
                });
                ExpressBuilder builder = new ExpressBuilder(client, options);
                builder.buildFragment();
                return builder.fragment;
            case 1:
                return ExpressBankAccountFragment.newInstance();
            case 2:
                return ExpressCreditCardFragment.newInstance();
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
