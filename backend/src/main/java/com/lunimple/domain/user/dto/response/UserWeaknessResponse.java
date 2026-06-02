package com.lunimple.domain.user.dto.response;

import java.util.List;

public record UserWeaknessResponse(
        List<WeaknessItemResponse> weaknesses
) {

    public static UserWeaknessResponse of(
            List<WeaknessItemResponse> weaknesses
    ) {
        return new UserWeaknessResponse(weaknesses);
    }
}