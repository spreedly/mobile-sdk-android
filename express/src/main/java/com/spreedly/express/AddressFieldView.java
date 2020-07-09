package com.spreedly.express;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * TODO: document your custom view class.
 */
public class AddressFieldView extends LinearLayout {
    AddressType addressType;
    TextInputLayout address1Wrapper;
    TextInputEditText address1Content;

    TextInputLayout address2Wrapper;
    TextInputEditText address2Content;

    TextInputLayout cityWrapper;
    TextInputEditText cityContent;

    TextInputLayout stateWrapper;
    TextInputEditText stateContent;

    TextInputLayout zipWrapper;
    TextInputEditText zipContent;

    Spinner countrySpinner;

    TextInputLayout phoneWrapper;
    TextInputEditText phoneContent;

    boolean visible = true;

    public AddressFieldView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AddressFieldView(@NonNull Context context, @NonNull AddressType addressType) {
        super(context);
        this.addressType = addressType;
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    void init() {
        this.setOrientation(VERTICAL);

        address1Wrapper = new TextInputLayout(this.getContext());
        address1Content = new TextInputEditText(this.getContext());
        address1Wrapper.setHint(getContext().getString(R.string.hint_address_line_1));
        address1Wrapper.addView(address1Content);


        address2Wrapper = new TextInputLayout(this.getContext());
        address2Content = new TextInputEditText(this.getContext());
        address2Wrapper.setHint(getContext().getString(R.string.hint_address_2));
        address2Wrapper.addView(address2Content);

        cityWrapper = new TextInputLayout(this.getContext());
        cityContent = new TextInputEditText(this.getContext());
        cityWrapper.setHint(getContext().getString(R.string.hint_city));
        cityWrapper.addView(cityContent);

        stateWrapper = new TextInputLayout(this.getContext());
        stateContent = new TextInputEditText(this.getContext());
        stateWrapper.setHint(getContext().getString(R.string.hint_state));
        stateWrapper.addView(stateContent);

        phoneWrapper = new TextInputLayout(this.getContext());
        phoneContent = new TextInputEditText(this.getContext());
        phoneWrapper.setHint(getContext().getString(R.string.hint_phone));
        phoneWrapper.addView(phoneContent);

        ArrayAdapter countryAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.country_array, android.R.layout.simple_spinner_item);
        countrySpinner = new Spinner(this.getContext());
        countrySpinner.setAdapter(countryAdapter);

        zipWrapper = new TextInputLayout(this.getContext());
        zipWrapper.setHint(getContext().getString(R.string.hint_zip));
        zipContent = new TextInputEditText(this.getContext());
        zipContent.setInputType(InputType.TYPE_CLASS_PHONE);
        zipWrapper.addView(zipContent);

        if (addressType == AddressType.BILLING) {
            address1Wrapper.setId(R.id.spreedly_address1);
            address2Wrapper.setId(R.id.spreedly_address2);
            cityWrapper.setId(R.id.spreedly_city);
            stateWrapper.setId(R.id.spreedly_state);
            zipWrapper.setId(R.id.spreedly_zip);
            countrySpinner.setId(R.id.spreedly_country);
            phoneWrapper.setId(R.id.spreedly_phone_number);
        } else {
            address1Wrapper.setId(R.id.spreedly_shipping_address1);
            address2Wrapper.setId(R.id.spreedly_shipping_address2);
            cityWrapper.setId(R.id.spreedly_shipping_city);
            stateWrapper.setId(R.id.spreedly_shipping_state);
            zipWrapper.setId(R.id.spreedly_shipping_zip);
            countrySpinner.setId(R.id.spreedly_shipping_country);
            phoneWrapper.setId(R.id.spreedly_shipping_phone_number);
        }

        this.addView(address1Wrapper);
        this.addView(address2Wrapper);
        this.addView(cityWrapper);
        this.addView(stateWrapper);
        this.addView(zipWrapper);
        this.addView(countrySpinner);
    }

    public enum AddressType {BILLING, SHIPPING}
}

