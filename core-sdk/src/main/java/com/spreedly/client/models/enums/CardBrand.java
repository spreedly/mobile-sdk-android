package com.spreedly.client.models.enums;

import com.spreedly.client.models.Range;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public enum CardBrand {
    visa(null, null),
    mastercard(new Range[]{new Range(222100, 272099), new Range(510000, 559999)}, null),
    americanExpress(null, null),
    alelo(new Range[]{new Range(402588, 402588), new Range(404347, 404347), new Range(405876, 405876), new Range(405882, 405882), new Range(405884, 405884), new Range(405886, 405886), new Range(430471, 430471), new Range(438061, 438061), new Range(438064, 438064), new Range(470063, 470066), new Range(496067, 496067), new Range(506699, 506704), new Range(506706, 506706), new Range(506713, 506714), new Range(506716, 506716), new Range(506749, 506750), new Range(506752, 506752), new Range(506754, 506756), new Range(506758, 506762), new Range(506764, 506767), new Range(506770, 506771), new Range(509015, 509019), new Range(509880, 509882), new Range(509884, 509885), new Range(509987, 509992)}, null),
    cabal(new Range[]{new Range(60420100, 60440099), new Range(58965700, 58965799), new Range(60352200, 60352299)}, null),
    carnet(new Range[]{new Range(506199, 506499)}, new String[]{"286900", "502275", "606333", "627535", "636318", "636379", "639388", "639484", "639559", "50633601", "50633606", "58877274", "62753500", "60462203", "60462204", "588772"}),
    dankort(null, null),
    dinersClub(null, null),
    discover(null, null),
    elo(new Range[]{new Range(506707, 506708), new Range(506715, 506715), new Range(506718, 506722), new Range(506724, 506724), new Range(506726, 506736), new Range(506739, 506739), new Range(506741, 506743), new Range(506745, 506747), new Range(506753, 506753), new Range(506774, 506776), new Range(506778, 506778), new Range(509000, 509001), new Range(509003, 509003), new Range(509007, 509007), new Range(509020, 509022), new Range(509035, 509035), new Range(509039, 509042), new Range(509045, 509045), new Range(509048, 509048), new Range(509051, 509071), new Range(509073, 509074), new Range(509077, 509080), new Range(509084, 509084), new Range(509091, 509094), new Range(509098, 509098), new Range(509100, 509100), new Range(509104, 509104), new Range(509106, 509109), new Range(627780, 627780), new Range(636368, 636368), new Range(650031, 650033), new Range(650035, 650045), new Range(650047, 650047), new Range(650406, 650410), new Range(650434, 650436), new Range(650439, 650439), new Range(650485, 650504), new Range(650506, 650530), new Range(650577, 650580), new Range(650582, 650591), new Range(650721, 650727), new Range(650901, 650922), new Range(650928, 650928), new Range(650938, 650939), new Range(650946, 650948), new Range(650954, 650955), new Range(650962, 650963), new Range(650967, 650967), new Range(650971, 650971), new Range(651652, 651667), new Range(651675, 651678), new Range(655000, 655010), new Range(655012, 655015), new Range(655051, 655052), new Range(655056, 655057)}, null),
    jcb(null, null),
    maestro(new Range[]{new Range(561200, 561269), new Range(561271, 561299), new Range(561320, 561356), new Range(581700, 581751), new Range(581753, 581800), new Range(589998, 591259), new Range(591261, 596770), new Range(596772, 598744), new Range(598746, 599999), new Range(600297, 600314), new Range(600316, 600335), new Range(600337, 600362), new Range(600364, 600382), new Range(601232, 601254), new Range(601256, 601276), new Range(601640, 601652), new Range(601689, 601700), new Range(602011, 602050), new Range(639000, 639099), new Range(670000, 679999)}, new String[]{"500033", "581149"}),
    naranja(null, new String[]{"589562"}),
    sodexo(null, null),
    vr(null, null),
    unknown(null, null),
    error(null, null);
    @Nullable
    public final Range[] range;
    @Nullable
    public final String[] bins;
    @NonNull
    private int icon;


    CardBrand(@Nullable Range[] range, @Nullable String[] bins) {
        this.bins = bins;
        this.range = range;
    }

}
