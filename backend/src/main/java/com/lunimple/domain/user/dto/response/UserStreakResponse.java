package com.lunimple.domain.user.dto.response;

import java.util.List;

public record UserStreakResponse(
        Integer streak,
        /** 최근 365일 중 풀이한 날짜 (yyyy-MM-dd) */
        List<String> activeDates
) {
    public static UserStreakResponse of(int streak, List<String> activeDates) {
        return new UserStreakResponse(streak, activeDates);
    }
}
