package com.lunimple.domain.user.dto.response;

import com.lunimple.domain.user.entity.User;

public record RankingResponse(
        Long id,
        String handle,
        Integer rating,
        Integer highest,
        Integer win,
        Integer match,
        String countryCode
) {

    public static RankingResponse from(User user) {
        return new RankingResponse(
                user.getId(),
                user.getHandle(),
                user.getRating(),
                user.getHighest(),
                user.getWin(),
                user.getMatchCount(),
                user.getCountry().getCode()
        );
    }
}
