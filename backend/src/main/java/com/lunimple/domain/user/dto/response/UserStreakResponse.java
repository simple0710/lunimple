package com.lunimple.domain.user.dto.response;

public record UserStreakResponse(
        Integer streak
) {
    public static UserStreakResponse of(int streak) {
        return new UserStreakResponse(streak);
    }
}
