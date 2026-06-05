package com.lunimple.domain.user.controller;

import com.lunimple.domain.user.dto.response.UserProfileResponse;
import com.lunimple.domain.user.dto.response.UserRecommendationResponse;
import com.lunimple.domain.user.dto.response.UserStatisticsResponse;
import com.lunimple.domain.user.dto.response.UserStreakResponse;
import com.lunimple.domain.user.dto.response.UserWeaknessResponse;
import com.lunimple.domain.user.service.UserService;
import com.lunimple.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{handle}")
    public ApiResponse<UserProfileResponse> getUserProfile(
            @PathVariable String handle
    ) {
        return ApiResponse.success(
                userService.getUserProfile(handle)
        );
    }

    @GetMapping("/{handle}/streak")
    public ApiResponse<UserStreakResponse> getUserStreak(
            @PathVariable String handle
    ) {
        return ApiResponse.success(
                userService.getUserStreak(handle)
        );
    }

    @GetMapping("/{handle}/statistics")
    public ApiResponse<UserStatisticsResponse> getUserStatistics(
            @PathVariable String handle
    ) {
        return ApiResponse.success(
                userService.getUserStatistics(handle)
        );
    }

    @GetMapping("/{handle}/weaknesses")
    public ApiResponse<UserWeaknessResponse> getWeakness(
            @PathVariable String handle
    ) {
        return ApiResponse.success(
                userService.getUserWeakness(handle)
        );
    }

    @GetMapping("/{handle}/recommendations")
    public ApiResponse<UserRecommendationResponse> getRecommendations(
            @PathVariable String handle
    ) {
        return ApiResponse.success(
                userService.getUserRecommendations(handle)
        );
    }
}
