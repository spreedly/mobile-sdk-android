package com.spreedly.sdk_sample.express;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.spreedly.sdk_sample.R;

public class ExpressBankAccountFragment extends Fragment {

    private ExpressBankAccountViewModel mViewModel;

    @NonNull
    public static ExpressBankAccountFragment newInstance() {
        return new ExpressBankAccountFragment();
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.express_bank_account_fragment, container, false);
    }

    @Override
    @Nullable
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ExpressBankAccountViewModel.class);
        mViewModel.token = getView().findViewById(R.id.token);
        mViewModel.mainView = getView().findViewById(R.id.express_bank_account_form);
        mViewModel.mainView.setClientInfo("XsQXqPtrgCOnpexSwyhzN9ngr2c", "ghEGueczUT4BhJv54K24G6B4Oy9yWaM5R4dR2yt5gRsx3xnwbZE0OZ0mRg2zyI5g", true);
        mViewModel.mainView.setCustomEventListener(mViewModel.onSubmit());
    }
}
