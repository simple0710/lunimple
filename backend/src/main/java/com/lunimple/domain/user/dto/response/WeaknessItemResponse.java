package com.lunimple.domain.user.dto.response;

import com.lunimple.domain.contest.enums.ContestType;
import com.lunimple.domain.problems.enums.ProblemCode;

public record WeaknessItemResponse(
        ContestType contestType,
        ProblemCode problemCode,
        Double successRate,
        Double averageAttempt,
        Integer recentFailCount,
        Double weaknessScore
) {

    public static WeaknessItemResponse of(
            ContestType contestType,
            ProblemCode problemCode,
            Double successRate,
            Double averageAttempt,
            Integer recentFailCount,
            Double weaknessScore
    ) {
        return new WeaknessItemResponse(
                contestType,
                problemCode,
                successRate,
                averageAttempt,
                recentFailCount,
                weaknessScore
        );
    }
}