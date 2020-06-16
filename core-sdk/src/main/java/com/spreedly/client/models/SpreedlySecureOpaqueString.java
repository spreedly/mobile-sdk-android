package com.spreedly.client.models;

import com.spreedly.client.models.enums.CardBrand;

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

    public SpreedlySecureOpaqueString(String text) {
        data = text;
        length = text.length();
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


    @NonNull String _encode() {
        data = data.replaceAll(" ", "");
        return data;
    }

    @Nullable
    public CardBrand detectCardType() {
        data = data.replaceAll(" ", "");
        if (!checkIsValid(data)) {
            return CardBrand.error;
        }
        if (Pattern.matches("^4[0-9]{12}([0-9]{3})?([0-9]{3})?$", data)) {
            return CardBrand.visa;
        } else if (data.length() == 16 && inRanges(CardBrand.mastercard.range, data, 6)) {
            return CardBrand.mastercard;
        } else if (data.length() == 16 && inRanges(CardBrand.elo.range, data, 6)) {
            return CardBrand.elo;
        } else if (data.length() == 16 && inRanges(CardBrand.alelo.range, data, 6)) {
            return CardBrand.alelo;
        } else if (Pattern.matches("^(6011|65[0-9]{2}|64[4-9][0-9])[0-9]{12,15}|(62[0-9]{14,17})$", data) && !Pattern.matches("^627416[0-9]{10}$", data)) {
            return CardBrand.discover;
        } else if (Pattern.matches("^3[47][0-9]{13}$", data)) {
            return CardBrand.americanExpress;
        } else if (data.length() == 16 && binMatch(CardBrand.naranja.bins, data)) {
            return CardBrand.naranja;
        } else if (Pattern.matches("^3(0[0-5]|[68][0-9])[0-9]{11}$", data)) {
            return CardBrand.dinersClub;
        } else if (Pattern.matches("^35(28|29|[3-8][0-9])[0-9]{12}$", data)) {
            return CardBrand.jcb;
        } else if (Pattern.matches("^5019[0-9]{12}$", data)) {
            return CardBrand.dankort;
        } else if (data.length() >= 12 && (inRanges(CardBrand.maestro.range, data, 6) || binMatch(CardBrand.maestro.bins, data))) {
            return CardBrand.maestro;
        } else if (Pattern.matches("^(606071|603389|606070|606069|606068|600818)[0-9]{10}$", data)) {
            return CardBrand.sodexo;
        } else if (Pattern.matches("^(627416|637036)[0-9]{10}$", data)) {
            return CardBrand.vr;
        } else if (data.length() == 16 && inRanges(CardBrand.cabal.range, data, 8)) {
            return CardBrand.cabal;
        } else if ((data.length() == 16 && inRanges(CardBrand.carnet.range, data, 6) || binMatch(CardBrand.carnet.bins, data))) {
            return CardBrand.carnet;
        }
        return CardBrand.unknown;

    }

    @Nullable
    public CardBrand softDetect() {
        data = data.replaceAll(" ", "");
        if (length > 19) {
            return CardBrand.unknown;
        }
        if (data.startsWith("4")) {
            return CardBrand.visa;
        } else if (inRanges(CardBrand.mastercard.range, data, 6)) {
            return CardBrand.mastercard;
        } else if (inRanges(CardBrand.elo.range, data, 6)) {
            return CardBrand.elo;
        } else if (inRanges(CardBrand.alelo.range, data, 6)) {
            return CardBrand.alelo;
        } else if (Pattern.matches("^(6011|65[0-9]{2}|64[4-9][0-9])[0-9]{12,15}|(62[0-9]{14,17})$", data) && !Pattern.matches("^627416[0-9]{10}$", data)) {
            return CardBrand.discover;
        } else if (Pattern.matches("^3[47][0-9]*", data)) {
            return CardBrand.americanExpress;
        } else if (binMatch(CardBrand.naranja.bins, data)) {
            return CardBrand.naranja;
        } else if (Pattern.matches("^3(0[0-5]|[68][0-9])[0-9]*", data)) {
            return CardBrand.dinersClub;
        } else if (Pattern.matches("^35(28|29|[3-8][0-9]*)", data)) {
            return CardBrand.jcb;
        } else if (Pattern.matches("^5019[0-9]*", data)) {
            return CardBrand.dankort;
        } else if (inRanges(CardBrand.maestro.range, data, 6) || binMatch(CardBrand.maestro.bins, data)) {
            return CardBrand.maestro;
        } else if (Pattern.matches("^(606071|603389|606070|606069|606068|600818)[0-9]*", data)) {
            return CardBrand.sodexo;
        } else if (Pattern.matches("^(627416|637036)[0-9]{10}$", data)) {
            return CardBrand.vr;
        } else if (inRanges(CardBrand.cabal.range, data, 8)) {
            return CardBrand.cabal;
        } else if (inRanges(CardBrand.carnet.range, data, 6) || binMatch(CardBrand.carnet.bins, data)) {
            return CardBrand.carnet;
        }
        return CardBrand.unknown;

    }



    @NonNull boolean checkIsValid(String numbers) {
        try {
            Double.parseDouble(numbers);
        } catch (Exception e) {
            return false;
        }
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

    @NonNull
    public boolean isNumber() {
        data = data.replaceAll(" ", "");
        try {
            Double.parseDouble(data);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    boolean inRanges(Range[] ranges, String input, int length) {
        if (length > input.length()) {
            return false;
        }
        int number = Integer.parseInt(input.substring(0, length));
        for (int i = 0; i < ranges.length; i++) {
            if (ranges[i].inRange(number)) {
                return true;
            }
        }
        return false;
    }

    boolean binMatch(String[] bins, String number) {
        for (int i = 0; i < bins.length; i++) {
            if (number.startsWith(bins[i])) {
                return true;
            }
        }
        return false;
    }
}