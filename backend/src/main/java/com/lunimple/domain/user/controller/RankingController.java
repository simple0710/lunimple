package com.lunimple.domain.user.controller;

import com.lunimple.domain.user.dto.response.RankingResponse;
import com.lunimple.domain.user.enums.RankingSortType;
import com.lunimple.domain.user.service.UserService;
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
@RequestMapping("/ranking")
public class RankingController {

    private final UserService userService;

    @GetMapping
    public ApiResponse<Page<RankingResponse>> getRanking(
            @RequestParam(required = false, name = "sort") RankingSortType rankingSortType,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page - 1, size);

        return ApiResponse.success(
                userService.getRanking(
                        rankingSortType,
                        pageable
                )
        );
    }
}
