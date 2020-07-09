package com.spreedly.securewidgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.textfield.TextInputLayout;
import com.spreedly.client.models.SpreedlySecureOpaqueString;

public class SecureTextField extends FrameLayout {
    @NonNull
    TextInputLayout textLayout;
    @NonNull
    EditText editText;

    public SecureTextField(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SecureTextField(@NonNull Context context) {
        super(context);
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

    @Nullable
    public SpreedlySecureOpaqueString getText() {
        return new SpreedlySecureOpaqueString(editText.getText().toString());
    }

    @Nullable
    protected View findTextInputLayout(@Nullable View v) {
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

    @Nullable
    protected View findEditText(@Nullable View v) {
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
        if (id == R.id.spreedly_credit_card_number) {
            textLayout.setHint(getContext().getString(R.string.hint_credit_card_number));
        } else if (id == R.id.spreedly_ccv) {
            textLayout.setHint(getContext().getString(R.string.hint_ccv));
        } else if (id == R.id.spreedly_ba_account_number) {
            textLayout.setHint(getContext().getString(R.string.hint_account_number));
        }
    }

    public void setError(@Nullable String error) {
        textLayout.setError(error);
    }
}
