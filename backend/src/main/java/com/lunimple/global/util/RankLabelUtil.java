package com.lunimple.global.util;

public final class RankLabelUtil {

    private RankLabelUtil() {
    }

    /** AtCoder 레이팅 구간을 kyu 라벨로 변환 */
    public static String toRankLabel(int rating) {
        if (rating < 400) {
            return "unrated";
        }
        int kyu = 10 - (rating / 100);
        if (kyu < 1) {
            kyu = 1;
        }
        if (kyu > 9) {
            kyu = 9;
        }
        return kyu + "kyu";
    }
}
