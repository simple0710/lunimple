package com.lunimple.domain.recommendation.dto.response;

import java.util.List;

public record RecommendationGroupResponse(
        String title,
        List<RecommendedProblemResponse> problems
) {
}