package com.lunimple.domain.user.dto.response;

public record UserProfileResponse(
        String handle,
        Integer rating,
        String rank,
        Integer totalSolved,
        Double averageSolvedRating,
        Double averageSubmissionCount,
        Double successRate,
        /** WA 비율 (%) */
        Double waRatio
) {

    public static UserProfileResponse of(
            String handle,
            int rating,
            String rank,
            int totalSolved,
            double averageSolvedRating,
            double averageSubmissionCount,
            double successRate,
            double waRatio
    ) {
        return new UserProfileResponse(
                handle,
                rating,
                rank,
                totalSolved,
                averageSolvedRating,
                averageSubmissionCount,
                successRate,
                waRatio
        );
    }
}
