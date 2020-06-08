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

    private final Range[] mastercardRanges = new Range[]{new Range(222100, 272099), new Range(510000, 559999)};
    private final Range[] maestroRanges = new Range[]{
            new Range(561200, 561269),
            new Range(561271, 561299),
            new Range(561320, 561356),
            new Range(581700, 581751),
            new Range(581753, 581800),
            new Range(589998, 591259),
            new Range(591261, 596770),
            new Range(596772, 598744),
            new Range(598746, 599999),
            new Range(600297, 600314),
            new Range(600316, 600335),
            new Range(600337, 600362),
            new Range(600364, 600382),
            new Range(601232, 601254),
            new Range(601256, 601276),
            new Range(601640, 601652),
            new Range(601689, 601700),
            new Range(602011, 602050),
            new Range(639000, 639099),
            new Range(670000, 679999)
    };
    private final Range[] eloRanges = new Range[]{
            new Range(506707, 506708), new Range(506715, 506715), new Range(506718, 506722), new Range(506724, 506724), new Range(506726, 506736), new Range(506739, 506739), new Range(506741, 506743),
            new Range(506745, 506747), new Range(506753, 506753), new Range(506774, 506776), new Range(506778, 506778), new Range(509000, 509001), new Range(509003, 509003), new Range(509007, 509007),
            new Range(509020, 509022), new Range(509035, 509035), new Range(509039, 509042), new Range(509045, 509045), new Range(509048, 509048), new Range(509051, 509071), new Range(509073, 509074),
            new Range(509077, 509080), new Range(509084, 509084), new Range(509091, 509094), new Range(509098, 509098), new Range(509100, 509100), new Range(509104, 509104), new Range(509106, 509109),
            new Range(627780, 627780), new Range(636368, 636368), new Range(650031, 650033), new Range(650035, 650045), new Range(650047, 650047), new Range(650406, 650410), new Range(650434, 650436),
            new Range(650439, 650439), new Range(650485, 650504), new Range(650506, 650530), new Range(650577, 650580), new Range(650582, 650591), new Range(650721, 650727), new Range(650901, 650922),
            new Range(650928, 650928), new Range(650938, 650939), new Range(650946, 650948), new Range(650954, 650955), new Range(650962, 650963), new Range(650967, 650967), new Range(650971, 650971),
            new Range(651652, 651667), new Range(651675, 651678), new Range(655000, 655010), new Range(655012, 655015), new Range(655051, 655052), new Range(655056, 655057)};
    private final Range[] aleloRanges = new Range[]{
            new Range(402588, 402588), new Range(404347, 404347), new Range(405876, 405876), new Range(405882, 405882), new Range(405884, 405884),
            new Range(405886, 405886), new Range(430471, 430471), new Range(438061, 438061), new Range(438064, 438064), new Range(470063, 470066),
            new Range(496067, 496067), new Range(506699, 506704), new Range(506706, 506706), new Range(506713, 506714), new Range(506716, 506716),
            new Range(506749, 506750), new Range(506752, 506752), new Range(506754, 506756), new Range(506758, 506762), new Range(506764, 506767),
            new Range(506770, 506771), new Range(509015, 509019), new Range(509880, 509882), new Range(509884, 509885), new Range(509987, 509992)
    };
    private final Range[] cabalRanges = new Range[]{
            new Range(60420100, 60440099),
            new Range(58965700, 58965799),
            new Range(60352200, 60352299)
    };
    private final Range[] naranjaRanges = new Range[]{
            new Range(589562, 589562)
    };
    private final Range[] carnetRanges = new Range[]{
            new Range(506199, 506499)
    };
    private final String[] carnetBins = new String[]{
            "286900", "502275", "606333", "627535", "636318", "636379", "639388",
            "639484", "639559", "50633601", "50633606", "58877274", "62753500",
            "60462203", "60462204", "588772"
    };

    private final String[] maestroBins = new String[]{
            "500033", "581149"
    };

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
        } else if (data.length() == 16 && inRanges(mastercardRanges, data, 6)) {
            return CardBrand.mastercard;
        } else if (data.length() == 16 && inRanges(eloRanges, data, 6)) {
            return CardBrand.elo;
        } else if (data.length() == 16 && inRanges(aleloRanges, data, 6)) {
            return CardBrand.alelo;
        } else if (Pattern.matches("^(6011|65[0-9]{2}|64[4-9][0-9])[0-9]{12,15}|(62[0-9]{14,17})$", data)) {
            return CardBrand.discover;
        } else if (Pattern.matches("^3[47][0-9]{13}$", data)) {
            return CardBrand.americanExpress;
        } else if (data.length() == 16 && inRanges(naranjaRanges, data, 6)) {
            return CardBrand.naranja;
        } else if (Pattern.matches("^3(0[0-5]|[68][0-9])[0-9]{11}$", data)) {
            return CardBrand.dinersClub;
        } else if (Pattern.matches("^35(28|29|[3-8][0-9])[0-9]{12}$", data)) {
            return CardBrand.jcb;
        } else if (Pattern.matches("^5019[0-9]{12}$", data)) {
            return CardBrand.dankort;
        } else if (data.length() >= 12 && (inRanges(maestroRanges, data, 6) || binMatch(carnetBins, data))) {
            return CardBrand.maestro;
        } else if (Pattern.matches("^(606071|603389|606070|606069|606068|600818)[0-9]{10}$", data)) {
            return CardBrand.sodexo;
        } else if (Pattern.matches("^(627416|637036)[0-9]{10}$", data)) {
            return CardBrand.vr;
        } else if (data.length() == 16 && inRanges(cabalRanges, data, 8)) {
            return CardBrand.cabal;
        } else if ((data.length() == 16 && inRanges(carnetRanges, data, 6) || binMatch(carnetBins, data))) {
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
        } else if (length >= 6 && inRanges(mastercardRanges, data, 6)) {
            return CardBrand.mastercard;
        } else if (length >= 6 && inRanges(eloRanges, data, 6)) {
            return CardBrand.elo;
        } else if (length >= 6 && inRanges(aleloRanges, data, 6)) {
            return CardBrand.alelo;
        } else if (Pattern.matches("^(6011|65[0-9]{2}|64[4-9][0-9])", data)) {
            return CardBrand.discover;
        } else if (Pattern.matches("^3[47][0-9]*", data)) {
            return CardBrand.americanExpress;
        } else if (length >= 6 && inRanges(naranjaRanges, data, 6)) {
            return CardBrand.naranja;
        } else if (Pattern.matches("^3(0[0-5]|[68][0-9])", data)) {
            return CardBrand.dinersClub;
        } else if (Pattern.matches("^35(28|29|[3-8][0-9])", data)) {
            return CardBrand.jcb;
        } else if (Pattern.matches("^5019[0-9]", data)) {
            return CardBrand.dankort;
        } else if (length >= 6 && (inRanges(maestroRanges, data, 6) || binMatch(carnetBins, data))) {
            return CardBrand.maestro;
        } else if (Pattern.matches("^(606071|603389|606070|606069|606068|600818)", data)) {
            return CardBrand.sodexo;
        } else if (Pattern.matches("^(627416|637036)[0-9]", data)) {
            return CardBrand.vr;
        } else if (length >= 8 && inRanges(cabalRanges, data, 8)) {
            return CardBrand.cabal;
        } else if (length >= 6 && (inRanges(carnetRanges, data, 6) || binMatch(carnetBins, data))) {
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

    class Range {
        public final int last;
        public final int first;

        public Range(int first, int last) {
            this.first = first;
            this.last = last;
        }

        boolean inRange(int number) {
            return first <= number && last >= number;
        }
    }
}