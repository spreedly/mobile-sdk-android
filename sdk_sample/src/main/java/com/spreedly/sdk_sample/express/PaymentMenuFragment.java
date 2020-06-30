package com.spreedly.sdk_sample.express;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.spreedly.client.models.enums.CardBrand;
import com.spreedly.express.StoredCard;
import com.spreedly.sdk_sample.R;

import java.util.ArrayList;
import java.util.List;

public class PaymentMenuFragment extends Fragment {

    private PaymentMenuViewModel mViewModel;

    @NonNull
    public static PaymentMenuFragment newInstance() {
        return new PaymentMenuFragment();
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.payment_menu_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PaymentMenuViewModel.class);
        mViewModel.mainView = getView().findViewById(R.id.express_payment_menu);
        StoredCard card1 = new StoredCard("sample_token_1", CardBrand.visa, "Visa XXXX");
        StoredCard card2 = new StoredCard("sample_token_2", CardBrand.mastercard, "Mastercard XXXX");
        List<StoredCard> storedCards = new ArrayList<>();
        storedCards.add(card1);
        storedCards.add(card2);
        mViewModel.mainView.addSavedCards(storedCards);
    }

}
