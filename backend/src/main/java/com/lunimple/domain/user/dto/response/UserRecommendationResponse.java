package com.lunimple.domain.user.dto.response;

import java.util.List;

public record UserRecommendationResponse(
        List<ProblemRecommendationResponse> recommendations,
        int solvedCount,
        int totalRecommendations
) {
    public static UserRecommendationResponse of(
            List<ProblemRecommendationResponse> recommendations,
            int solvedCount
    ) {
        return new UserRecommendationResponse(
                recommendations,
                solvedCount,
                recommendations.size()
        );
    }
}
