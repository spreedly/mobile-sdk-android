package com.spreedly.securewidgets;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SecureExpirationDate extends FrameLayout {
    TextInputEditText editText;
    TextInputLayout textLayout;

    public SecureExpirationDate(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        textLayout = new TextInputLayout(this.getContext());
        editText = new TextInputEditText(this.getContext());
        editText.setInputType(InputType.TYPE_CLASS_DATETIME);
        textLayout.addView(editText);
        this.addView(textLayout);
        editText.addTextChangedListener(new ExpirationDateTextWatcher());
        textLayout.setHint("MMYY");
    }

    public int getMonth() {
        String input = editText.getEditableText().toString();
        if (input.length() == 5) {
            return Integer.parseInt(input.substring(0, 2));
        }
        return 0;
    }

    public int getYear() {
        String input = editText.getEditableText().toString();
        if (input.length() == 5) {
            return 2000 + Integer.parseInt(input.substring(3, 5));
        }
        return 0;
    }

    public void setError(@Nullable String error) {
        textLayout.setError(error);
    }


    class ExpirationDateTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int month;
            if (s.length() > 2) {
                if (s.charAt(2) != '/') {
                    s = s.insert(2, "/");
                }
                if (s.length() > 5) {
                    s = s.delete(5, s.length());
                }
                if (s.length() == 5) {
                    month = Integer.parseInt(s.toString().substring(0, 2));
                    if (month > 12) {
                        textLayout.setError("Invalid Date");
                    } else {
                        textLayout.setError(null);
                    }
                }
            }
        }
    }
}
