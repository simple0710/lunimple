package com.lunimple.domain.problems.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProblemCode {

    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F"),
    G("G"),
    H("H"),
    EX("Ex");

    private final String value;
}