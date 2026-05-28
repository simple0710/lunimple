package com.lunimple.domain.contest.dto.response;

import com.lunimple.domain.contest.entity.Contest;
import com.lunimple.domain.contest.enums.ContestType;

import java.time.LocalDateTime;

public record ContestResponse(

        String name,
        ContestType contestType,
        LocalDateTime startTime,
        Integer durationMinutes,
        Integer ratedMin,
        Integer ratedMax
) {

    public static ContestResponse from(Contest contest) {
        return new ContestResponse(
                contest.getName(),
                contest.getContestType(),
                contest.getStartTime(),
                contest.getDurationMinutes(),
                contest.getRatedMin(),
                contest.getRatedMax()
        );
    }

}
