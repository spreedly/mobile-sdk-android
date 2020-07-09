package com.spreedly.securewidgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;


public class SecureExpirationDate extends LinearLayout {
    Spinner monthSpinner;
    Spinner yearSpinner;
    LinearLayout spinnerWrapper;
    TextView error;
    String month;
    String year;

    public SecureExpirationDate(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SecureExpirationDate(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onFinishInflate() {

        super.onFinishInflate();
        init();
    }

    private void init() {
        this.setOrientation(VERTICAL);
        String[] monthArr = new String[]{"01", "02", "03", "04", "05", "06", "07", "06", "09", "10", "11", "12"};
        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        String[] yearArr = new String[20];
        for (int i = 0; i < 20; i++) {
            int y = currentYear + i;
            yearArr[i] = Integer.toString(y);
        }
        spinnerWrapper = new LinearLayout(getContext());
        monthSpinner = new Spinner(getContext());
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, monthArr);
        monthSpinner.setAdapter(monthAdapter);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = monthArr[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                month = "01";
            }
        });
        spinnerWrapper.addView(monthSpinner);
        yearSpinner = new Spinner(getContext());

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, yearArr);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = yearArr[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                year = Integer.toString(currentYear);
            }
        });
        spinnerWrapper.addView(yearSpinner);
        this.addView(spinnerWrapper);
    }


    public int getMonth() {
        return Integer.parseInt(month);
    }

    public int getYear() {
        return Integer.parseInt(year);
    }

    public void setError(@Nullable String errorMessage) {
        error = new TextView(getContext());
        error.setText(errorMessage);
        error.setTextAppearance(getContext(), R.style.InputError);
        this.addView(error, 0);
    }
}
