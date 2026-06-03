package com.lunimple.domain.recommendation.dto.response;

import java.util.List;

public record RecommendationResponse(
        Integer totalCount,
        Integer completedCount,
        List<RecommendedProblemResponse> required,
        List<RecommendedProblemResponse> additional,
        List<RecommendedProblemResponse> challenge
) {
}