package com.lunimple.domain.user.controller;

import com.lunimple.domain.user.dto.response.UserProfileResponse;
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
}
