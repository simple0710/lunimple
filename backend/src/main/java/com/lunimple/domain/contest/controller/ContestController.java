package com.lunimple.domain.contest.controller;

import com.lunimple.domain.contest.dto.response.ContestResponse;
import com.lunimple.domain.contest.service.ContestService;
import com.lunimple.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contests")
public class ContestController {

    private final ContestService contestService;

    @GetMapping("/upcoming")
    public ApiResponse<List<ContestResponse>> getUpcomingContests() {

        return ApiResponse.success(
                contestService.getUpcomingContests()
        );
    }
}
