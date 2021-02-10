package com.spreedly.securewidgets;

import androidx.annotation.NonNull;

import com.spreedly.client.models.enums.CardBrand;

public class CardBrandHelper {
    public int getIcon(@NonNull CardBrand brand) {
        int icon = 0;
        switch (brand) {
            case visa:
                icon = R.drawable.ic_spr_visa;
                break;
            case mastercard:
                icon = R.drawable.ic_spr_mastercard;
                break;
            case americanExpress:
                icon = R.drawable.ic_spr_amex;
                break;
            case alelo:
                icon = R.drawable.ic_spr_generic;
                break;
            case cabal:
                icon = R.drawable.ic_spr_generic;
                break;
            case carnet:
                icon = R.drawable.ic_spr_generic;
                break;
            case dankort:
                icon = R.drawable.ic_spr_generic;
                break;
            case dinersClub:
                icon = R.drawable.ic_spr_diners;
                break;
            case discover:
                icon = R.drawable.ic_spr_discover;
                break;
            case elo:
                icon = R.drawable.ic_spr_elo;
                break;
            case jcb:
                icon = R.drawable.ic_spr_jcb;
                break;
            case maestro:
                icon = R.drawable.ic_spr_maestro;
                break;
            case naranja:
                icon = R.drawable.ic_spr_generic;
                break;
            case sodexo:
                icon = R.drawable.ic_spr_generic;
                break;
            case vr:
                icon = R.drawable.ic_spr_generic;
                break;
            case unknown:
                icon = R.drawable.ic_spr_generic;
                break;
            case error:
                icon = R.drawable.spr_card_error;
                break;
            default:
                icon = R.drawable.ic_spr_generic;
                break;
        }
        return icon;
    }

    public int getMonoIcon(@NonNull CardBrand brand) {
        int icon = 0;
        switch (brand) {
            case visa:
                icon = R.drawable.ic_spr_mono_visa;
                break;
            case mastercard:
                icon = R.drawable.ic_spr_mono_mastercard;
                break;
            case americanExpress:
                icon = R.drawable.ic_spr_mono_amex;
                break;
            case alelo:
                icon = R.drawable.ic_spr_mono_generic;
                break;
            case cabal:
                icon = R.drawable.ic_spr_mono_generic;
                break;
            case carnet:
                icon = R.drawable.ic_spr_mono_generic;
                break;
            case dankort:
                icon = R.drawable.ic_spr_mono_generic;
                break;
            case dinersClub:
                icon = R.drawable.ic_spr_mono_diners;
                break;
            case discover:
                icon = R.drawable.ic_spr_mono_discover;
                break;
            case elo:
                icon = R.drawable.ic_spr_mono_elo;
                break;
            case jcb:
                icon = R.drawable.ic_spr_mono_jcb;
                break;
            case maestro:
                icon = R.drawable.ic_spr_mono_maestro;
                break;
            case naranja:
                icon = R.drawable.ic_spr_mono_generic;
                break;
            case sodexo:
                icon = R.drawable.ic_spr_mono_generic;
                break;
            case vr:
                icon = R.drawable.ic_spr_generic;
                break;
            case unknown:
                icon = R.drawable.ic_spr_mono_generic;
                break;
            case error:
                icon = R.drawable.spr_card_error;
                break;
            default:
                icon = R.drawable.ic_spr_mono_generic;
                break;
        }
        return icon;
    }
}
