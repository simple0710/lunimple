package com.lunimple.domain.recommendation.dto.response;

public record RecommendedProblemResponse(
        Long problemId,
        String externalProblemId,
        String title,
        Integer difficulty,
        String reason
) {
}