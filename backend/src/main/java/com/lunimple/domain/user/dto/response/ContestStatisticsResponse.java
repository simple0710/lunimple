package com.lunimple.domain.user.dto.response;

import com.lunimple.domain.contest.enums.ContestType;

import java.util.List;

public record ContestStatisticsResponse(
        ContestType contestType,
        List<ProblemStatisticsResponse> problems
) {
    public static ContestStatisticsResponse of(
            ContestType contestType,
            List<ProblemStatisticsResponse> problems
    ) {
        return new ContestStatisticsResponse(
                contestType,
                problems
        );
    }
}