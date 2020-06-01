package com.spreedly.securewidgets;

import android.content.Context;
import android.content.res.TypedArray;
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
    TextInputLayout textInputLayout;
    TypedArray styledAttributes;
    int format;

    public SecureExpirationDate(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.SecureExpirationDate);
        init();
    }

    private void init() {
        textInputLayout = new TextInputLayout(this.getContext());
        editText = new TextInputEditText(this.getContext());
        editText.setInputType(InputType.TYPE_CLASS_DATETIME);
        textInputLayout.addView(editText);
        this.addView(textInputLayout);
        editText.addTextChangedListener(new ExpirationDateTextWatcher());

        format = styledAttributes.getInteger(R.styleable.SecureExpirationDate_dateFormat, 0);
        switch (format) {
            case 0:
                textInputLayout.setHint("MMYY");
                break;
            case 1:
                textInputLayout.setHint("YYMM");
                break;
            case 2:
                textInputLayout.setHint("MMYYYY");
                break;
            default:
                textInputLayout.setHint("MMYY");
                break;
        }
    }

    public int getMonth() {
        String input = editText.getEditableText().toString();
        switch (format) {
            case 0:
                return Integer.parseInt(input.substring(0, 2));
            case 1:
                return Integer.parseInt(input.substring(3, 5));
            case 2:
                return Integer.parseInt(input.substring(0, 2));
            default:
                return 0;
        }
    }

    public int getYear() {
        String input = editText.getEditableText().toString();
        switch (format) {
            case 0:
                return 2000 + Integer.parseInt(input.substring(3, 5));
            case 1:
                return 2000 + Integer.parseInt(input.substring(0, 2));
            case 2:
                return Integer.parseInt(input.substring(3, 7));
            default:
                return 0;
        }
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
            int length = s.length();
            int month;
            int year;
            if (s.length() > 2) {
                if (s.charAt(2) != '/') {
                    s = s.insert(2, "/");
                    length = s.length();
                }
                switch (format) {
                    case 0:
                        if (s.length() > 5) {
                            s = s.delete(5, s.length());
                        }
                        if (s.length() == 5) {
                            month = Integer.parseInt(s.toString().substring(0, 2));
                            if (month > 12) {
                                textInputLayout.setError("Invalid Date");
                            } else {
                                textInputLayout.setError(null);
                            }
                        }
                        break;
                    case 1:
                        if (s.length() > 5) {
                            s = s.delete(5, s.length());
                        }
                        if (s.length() == 5) {
                            month = Integer.parseInt(s.toString().substring(3, 5));
                            if (month > 12) {
                                textInputLayout.setError("Invalid Date");
                            } else {
                                textInputLayout.setError(null);
                            }
                        }
                        break;
                    case 2:
                        if (s.length() > 7) {
                            s = s.delete(7, s.length());
                            if (s.length() == 5) {
                                month = Integer.parseInt(s.toString().substring(0, 2));
                                if (month > 12) {
                                    textInputLayout.setError("Invalid Date");
                                } else {
                                    textInputLayout.setError(null);
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
