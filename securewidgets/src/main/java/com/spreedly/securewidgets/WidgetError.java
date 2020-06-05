package com.spreedly.securewidgets;

public enum WidgetError {
    NUMBER(R.id.spreedly_credit_card_number),
    MONTH(R.id.spreedly_cc_expiration_date),
    YEAR(R.id.spreedly_cc_expiration_date),
    CARD_TYPE(R.id.spreedly_credit_card_number),
    ACCOUNT_NUMBER(R.id.spreedly_ba_account_number),
    ROUTING_NUMBER(R.id.spreedly_ba_routing_number),
    FULL_NAME(R.id.spreedly_full_name);
    private final int resourceId;

    WidgetError(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }
}
