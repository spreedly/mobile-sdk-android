package com.spreedly.securewidgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.textfield.TextInputLayout;
import com.spreedly.client.SpreedlyClient;
import com.spreedly.client.models.SpreedlySecureOpaqueString;

public class SecureTextField extends FrameLayout {
    TextInputLayout textLayout;
    EditText editText;

    public SecureTextField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        int id = this.getId();
        View view = findTextInputLayout(this);
        if (!(view instanceof TextInputLayout)) {
            textLayout = new TextInputLayout(this.getContext());
            this.addView(textLayout);
        } else {
            textLayout = (TextInputLayout) view;
        }
        view = findEditText(textLayout);
        if (view instanceof AppCompatEditText) {
            editText = (AppCompatEditText) view;
        } else {
            editText = new AppCompatEditText(textLayout.getContext());
            setHint();
            textLayout.addView(editText);
        }
    }

    public SpreedlySecureOpaqueString getText() {
        SpreedlyClient client;
        ViewParent parent = this.getParent();
        parent.getClass();
        while (parent.getClass() != SecureFormLayout.class) {
            parent = parent.getParent();
        }
        SecureFormLayout parentClass = (SecureFormLayout) parent;
        client = parentClass.spreedlyClient;
        return client.createString(editText.getText().toString());
    }

    protected View findTextInputLayout(View v) {
        if (v instanceof TextInputLayout) {
            return v;
        }

        View result = null;
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {

                View child = vg.getChildAt(i);
                result = findTextInputLayout(child);
                if (result instanceof TextInputLayout) {
                    return result;
                }
            }
        }
        return result;
    }

    protected View findEditText(View v) {
        if (v instanceof EditText) {
            return v;
        }

        View result = null;
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {

                View child = vg.getChildAt(i);
                result = findEditText(child);
                if (result instanceof EditText) {
                    return result;
                }
            }
        }
        return result;
    }

    private void setHint() {
        int id = getId();
        if (id == R.id.credit_card_number) {
            textLayout.setHint("Credit Card Number");
        } else if (id == R.id.ccv) {
            textLayout.setHint("CCV");
        } else if (id == R.id.ba_account_number) {
            textLayout.setHint("Account Number");
        }
    }

    public void setError(String error) {

    }
}
