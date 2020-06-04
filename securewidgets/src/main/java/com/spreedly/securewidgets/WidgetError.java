package com.spreedly.securewidgets;

import android.view.View;

public enum WidgetError {
    NUMBER(R.id.spreedly_credit_card_number, SecureCreditCardField.class),
    MONTH(R.id.spreedly_cc_expiration_date, SecureExpirationDate.class),
    YEAR(R.id.spreedly_cc_expiration_date, SecureExpirationDate.class),
    CARD_TYPE(R.id.spreedly_credit_card_number, SecureCreditCardField.class);

    private final int resourceId;
    private final Class<? extends View> resourceClass;

    WidgetError(int resourceId, Class<? extends View> resourceClass) {
        this.resourceId = resourceId;
        this.resourceClass = resourceClass;
    }

    public int getResourceId() {
        return resourceId;
    }

}
