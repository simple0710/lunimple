package com.lunimple.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecommendationTier {
    ESSENTIAL("필수"),
    ADDITIONAL("추가"),
    CHALLENGE("도전");

    private final String label;
}
