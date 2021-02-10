package com.spreedly.securewidgets;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CreditCardTransformationMethod extends PasswordTransformationMethod {
    @NonNull
    static char DOT = '\u2022';

    @Override
    @NonNull
    public CharSequence getTransformation(@Nullable CharSequence source, @Nullable View view) {
        return new CardCharSequence(source);
    }

    private class CardCharSequence implements CharSequence {

        private CharSequence mSource;

        public CardCharSequence(CharSequence source) {
            mSource = source;
        }

        public char charAt(int i) {
            if (i < length() - 4 && mSource.charAt(i) != ' ') {
                return DOT;
            } else return mSource.charAt(i);
        }

        public int length() {
            return mSource.length();
        }

        public CharSequence subSequence(int start, int end) {
            return mSource.subSequence(start, end); // Return default
        }
    }
}
