package com.spreedly.client.models.enums;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public enum CardBrand {
    visa,
    mastercard,
    americanExpress,
    alelo,
    cabal,
    carnet,
    dankort,
    dinersClub,
    discover,
    elo,
    jcb,
    maestro,
    naranja,
    sodexo,
    vr,
    unknown,
    error;
    private int icon;

    @Nullable
    public int getIcon() {
        return this.icon;
    }

    public void setIcon(@NonNull int icon) {
        this.icon = icon;
    }


}
