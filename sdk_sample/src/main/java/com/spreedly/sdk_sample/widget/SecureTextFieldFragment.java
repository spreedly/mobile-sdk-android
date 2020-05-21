package com.spreedly.sdk_sample.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.spreedly.sdk_sample.R;

public class SecureTextFieldFragment extends Fragment {

    private SecureTextFieldViewModel mViewModel;

    public static SecureTextFieldFragment newInstance() {
        return new SecureTextFieldFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.secure_text_field_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SecureTextFieldViewModel.class);
        // TODO: Use the ViewModel
    }

}
