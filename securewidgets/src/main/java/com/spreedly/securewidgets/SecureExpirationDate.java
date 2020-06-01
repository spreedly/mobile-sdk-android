package com.spreedly.securewidgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SecureExpirationDate extends TextInputLayout {
    TextInputEditText editText;
    TypedArray styledAttributes;
    int format;

    public SecureExpirationDate(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.SecureExpirationDate);
        init();
    }

    private void init() {
        editText = new TextInputEditText(this.getContext());
        editText.setInputType(InputType.TYPE_CLASS_DATETIME);
        this.addView(editText);
        editText.addTextChangedListener(new ExpirationDateTextWatcher());

        format = styledAttributes.getInteger(R.styleable.SecureExpirationDate_dateFormat, 0);
        switch (format) {
            case 0:
                this.setHint("MMYY");
                break;
            case 1:
                this.setHint("YYMM");
                break;
            case 2:
                this.setHint("MMYYYY");
            default:
                this.setHint("MMYY");
        }
    }


    private class ExpirationDateTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int length = s.length();
            if (s.length() > 2) {
                if (s.charAt(2) != '/') {
                    s = s.insert(2, "/");
                    length = s.length();
                }
                if (format == 2 && s.length() > 7) {
                    s = s.delete(7, s.length());
                } else if ((format == 0 || format == 1) && s.length() > 5) {
                    s = s.delete(5, s.length());
                }
            }
        }
    }
}
