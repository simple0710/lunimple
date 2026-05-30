package com.lunimple.domain.contest.dto.response;

import com.lunimple.domain.contest.entity.Contest;
import com.lunimple.domain.contest.enums.ContestType;
import com.lunimple.domain.problems.dto.response.ProblemResponse;

import java.util.List;

public record ContestProblemListResponse(
        String contestName,
        String contestUrl,
        ContestType contestType,
        List<ProblemResponse> problems
) {

    public static ContestProblemListResponse from(Contest contest) {
        return new ContestProblemListResponse(
                contest.getName(),
                contest.getContestUrl(),
                contest.getContestType(),
                contest.getProblems().stream()
                        .map(ProblemResponse::from)
                        .toList()
        );
    }

}