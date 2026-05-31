package com.lunimple.domain.user.dto.response;

import com.lunimple.domain.user.entity.User;

public record UserProfileResponse(
        /**
         * 전체 해결 문제 수
         */
        Integer totalSolved,

        /**
         * 해결한 문제들의 평균 레이팅
         * (해결한 문제 레이팅 총합 / 해결한 문제 수)
         */
        Double averageSolvedRating,

        /**
         * 문제당 평균 제출 횟수
         * (전체 제출 횟수 / 해결한 문제 수)
         */
        Double averageSubmissionCount,

        /**
         * 성공률
         * (해결한 문제 수 / 시도한 문제 수) × 100
         */
        Double successRate
) {

    public static UserProfileResponse of(
            int totalSolved,
            double averageSolvedRating,
            double averageSubmissionCount,
            double successRate
    ) {
        return new UserProfileResponse (
                totalSolved,
                averageSolvedRating,
                averageSubmissionCount,
                successRate
        );
    }
}
