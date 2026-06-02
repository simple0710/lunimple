package com.lunimple.domain.user.dto.response;

import com.lunimple.domain.contest.enums.ContestType;
import com.lunimple.domain.problems.enums.ProblemCode;

public record WeaknessKey(
        ContestType contestType,
        ProblemCode problemCode
) {
}