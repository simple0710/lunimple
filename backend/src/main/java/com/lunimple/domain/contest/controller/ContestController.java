package com.lunimple.domain.contest.controller;

import com.lunimple.domain.contest.dto.response.ContestProblemListResponse;
import com.lunimple.domain.contest.dto.response.ContestResponse;
import com.lunimple.domain.contest.enums.ContestType;
import com.lunimple.domain.contest.service.ContestService;
import com.lunimple.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contests")
public class ContestController {

    private final ContestService contestService;

    @GetMapping("/upcoming")
    public ApiResponse<Page<ContestResponse>> getUpcomingContests(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(name = "contest_type", required = false) ContestType contestType
    ) {

        Pageable pageable = PageRequest.of(page - 1, size);

        return ApiResponse.success(
                contestService.getUpcomingContests(
                        contestType,
                        pageable
                )
        );
    }

    @GetMapping("/past")
    public ApiResponse<Page<ContestResponse>> getPastContest(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(name = "contest_type", required = false) ContestType contestType
    ) {

        Pageable pageable = PageRequest.of(page - 1, size);

        return ApiResponse.success(
                contestService.getPastContests(
                        contestType,
                        pageable
                )
        );
    }

    @GetMapping("/problems")
    public ApiResponse<Page<ContestProblemListResponse>> getContestProblems(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ) {

        Pageable pageable = PageRequest.of(page - 1, size);

        return ApiResponse.success(
                contestService.getContestProblems(pageable)
        );
    }
}
