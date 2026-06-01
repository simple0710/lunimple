package com.lunimple.domain.user.dto.response;

import com.lunimple.domain.problems.enums.ProblemCode;

public record ProblemStatisticsResponse(
        ProblemCode problem,
        Integer total,
        Integer solved
) {

    public static ProblemStatisticsResponse of(
            ProblemCode problem,
            Integer total,
            Integer solved
    ) {
        return new ProblemStatisticsResponse(
                problem,
                total,
                solved
        );
    }
}