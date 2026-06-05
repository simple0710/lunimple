package com.lunimple.domain.user.dto.response;

import com.lunimple.domain.user.enums.RecommendationTier;

public record ProblemRecommendationResponse(
        RecommendationTier tier,
        String title,
        Integer difficulty,
        String url,
        String hint
) {
    public static ProblemRecommendationResponse of(
            RecommendationTier tier,
            String title,
            Integer difficulty,
            String url,
            String hint
    ) {
        return new ProblemRecommendationResponse(
                tier,
                title,
                difficulty,
                url,
                hint
        );
    }
}
