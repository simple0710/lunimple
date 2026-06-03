package com.lunimple.domain.recommendation.controller;

import com.lunimple.domain.recommendation.dto.response.RecommendationResponse;
import com.lunimple.domain.recommendation.service.RecommendationService;
import com.lunimple.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/{handle}/recommendations")
    public ApiResponse<RecommendationResponse> getRecommendations(
            @PathVariable String handle
    ) {
        return ApiResponse.success(
                recommendationService.getRecommendations(handle)
        );
    }
}