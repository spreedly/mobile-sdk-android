package com.spreedly.securewidgets;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

public class CreditCardTransformationMethod extends PasswordTransformationMethod {
    private static char DOT = '\u2022';

    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new PasswordCharSequence(source);
    }

    private class PasswordCharSequence implements CharSequence {

        private CharSequence mSource;

        public PasswordCharSequence(CharSequence source) {
            mSource = source;
        }

        public char charAt(int i) {
            if (i < length() - 4) {
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
