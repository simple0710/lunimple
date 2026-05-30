package com.lunimple.domain.problems.dto.response;

import com.lunimple.domain.problems.entity.Problem;
import com.lunimple.domain.problems.enums.ProblemCode;

public record ProblemResponse(
        ProblemCode code,
        String title,
        Integer difficulty,
        String url

) {
    public static ProblemResponse from(Problem problem) {
        return new ProblemResponse(
                problem.getCode(),
                problem.getTitle(),
                problem.getDifficulty(),
                problem.getUrl()
        );
    }
}