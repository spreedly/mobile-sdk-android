package com.spreedly.sdk_sample.simple;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

abstract class BillingFragmentBase extends Fragment {

    void bindTextView(TextView text, MutableLiveData<String> data) {
        text.setText(data.getValue());
        data.observe(getViewLifecycleOwner(), s -> text.setText(s));
    }

    void bindEditText(EditText edit, MutableLiveData<String> data) {
        edit.setText(data.getValue());
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data.postValue(s.toString());
            }
        });
    }

    void bindEditNumber(EditText edit, MutableLiveData<Integer> data) {
        edit.setText(data.getValue() == null ? "" : data.getValue().toString());
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data.postValue(s.length() == 0 ? null : Integer.parseInt(String.valueOf(s)));
            }
        });
    }

    <T> void bindSpinner(Spinner edit, MutableLiveData<T> data, T[] values) {
        edit.setAdapter(new ArrayAdapter<T>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, values));
        edit.setSelection(Arrays.binarySearch(values, data.getValue()));
        edit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                data.postValue(values[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
