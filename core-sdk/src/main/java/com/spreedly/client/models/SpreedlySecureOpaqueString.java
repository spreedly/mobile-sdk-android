package com.spreedly.client.models;

import com.spreedly.client.models.enums.CreditCardType;

import java.util.regex.Pattern;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public final class SpreedlySecureOpaqueString {
    @NonNull
    private String data;

    public int length;

    public SpreedlySecureOpaqueString() {
        data = "";
        length = 0;
    }

    public void clear() {
        data = "";
        length = 0;
    }

    public void append(@NonNull String string) {
        data += string;
        length = data.length();
    }

    public void removeLastCharacter() {
        if (length == 0) {
            return;
        }
        data = data.substring(0, length - 1);
        length = data.length();
    }

    @Nullable
    public CreditCardType detectCardType() {

        if (!checkIsValid(data)) {
            return CreditCardType.error;
        }
        if (Pattern.matches("^(5018|5081|5044|5020|5038|603845|6304|6759|676[1-3]|6799|6220|504834|504817|504645)[0-9]{8,15}$", data)) {
            return CreditCardType.maestro;
        } else if (Pattern.matches("^3(?:0[0-5]|[68][0-9])[0-9]{11}$", data)) {
            return CreditCardType.dinersClub;
        } else if (Pattern.matches("^(?:2131|1800|35[0-9]{3})[0-9]{11}$", data)) {
            return CreditCardType.jcb;
        } else if (Pattern.matches("^65[4-9][0-9]{13}|64[4-9][0-9]{13}|6011[0-9]{12}|(622(?:12[6-9]|1[3-9][0-9]|[2-8][0-9][0-9]|9[01][0-9]|92[0-5])[0-9]{10})$", data)) {
            return CreditCardType.discover;
        } else if (Pattern.matches("^4[0-9]{12}(?:[0-9]{3})?$", data)) {
            return CreditCardType.visa;
        } else if (Pattern.matches("^3[47][0-9]{13}$", data)) {
            return CreditCardType.americanExpress;
        } else if (Pattern.matches("^5[1-5][0-9]{14}$|^2(?:2(?:2[1-9]|[3-9][0-9])|[3-6][0-9][0-9]|7(?:[01][0-9]|20))[0-9]{12}$", data)) {
            return CreditCardType.mastercard;
        } else if (Pattern.matches("^(507597)[0-9]{8,15}$", data)) {
            return CreditCardType.sodexo;
        } else if (Pattern.matches("^589562[0-9]{8,15}$", data)) {
            return CreditCardType.naranja;
        } else if (Pattern.matches("^(5019|4571)[0-9]{8,15}$", data)) {
            return CreditCardType.dankort;
        }
//        alelo <-- can't find bin/iin
//        cabal <-- can't find bin/iin
//        carnet
//        dankort <-- dankort or dankort + dankort/visa?
//        elo,
//        vr,
        return CreditCardType.unknown;

    }

    @NonNull boolean checkIsValid(String numbers) {
        int nDigits = numbers.length();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {

            int d = numbers.charAt(i) - '0';

            if (isSecond == true)
                d = d * 2;

            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }

    @NonNull String _encode() {
        return data;
    }
}
