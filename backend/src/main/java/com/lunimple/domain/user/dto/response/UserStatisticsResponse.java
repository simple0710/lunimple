package com.lunimple.domain.user.dto.response;


import java.util.List;

public record UserStatisticsResponse(
        List<ContestStatisticsResponse> statistics
) {
    public static UserStatisticsResponse of(
            List<ContestStatisticsResponse> statistics

    ) {
        return new UserStatisticsResponse(
                statistics
        );
    }

}
