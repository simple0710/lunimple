package com.lunimple.global.init;


import com.lunimple.domain.contest.entity.Contest;
import com.lunimple.domain.contest.enums.ContestType;
import com.lunimple.domain.contest.repository.ContestRepository;
import com.lunimple.domain.problems.entity.Problem;
import com.lunimple.domain.problems.enums.ProblemCode;
import com.lunimple.domain.problems.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class ContestDataInitializer implements CommandLineRunner {

    private final ContestRepository contestRepository;
    private final ProblemRepository problemRepository;

    @Override
    public void run(String... args) {

        if (contestRepository.count() > 0) {
            return;
        }

        Contest abc = Contest.builder()
                .name("AtCoder Beginner Contest 463")
                .startTime(LocalDateTime.of(2026, 6, 27, 21, 0))
                .durationMinutes(100)
                .contestType(ContestType.ABC)
                .ratedMin(0)
                .ratedMax(1999)
                .contestUrl("https://atcoder.jp/contests/abc463")
                .build();
        Contest arc = Contest.builder()
                .name("AtCoder Regular Contest 211")
                .startTime(LocalDateTime.of(2025, 11, 30, 21, 0))
                .durationMinutes(120)
                .contestType(ContestType.ARC)
                .ratedMin(1200)
                .ratedMax(2399)
                .contestUrl("https://atcoder.jp/contests/arc211")
                .build();
        List<Problem> problems = List.of(
                Problem.builder()
                        .problemId("abc463_a")
                        .title("Hello AtCoder")
                        .code(ProblemCode.A)
                        .difficulty(100)
                        .url("https://atcoder.jp/contests/abc463/tasks/abc463_a")
                        .contest(abc)
                        .build(),

                Problem.builder()
                        .problemId("abc463_b")
                        .title("Sum of Digits")
                        .code(ProblemCode.B)
                        .difficulty(200)
                        .url("https://atcoder.jp/contests/abc463/tasks/abc463_b")
                        .contest(abc)
                        .build(),

                Problem.builder()
                        .problemId("abc463_c")
                        .title("Array Rotation")
                        .code(ProblemCode.C)
                        .difficulty(400)
                        .url("https://atcoder.jp/contests/abc463/tasks/abc463_c")
                        .contest(abc)
                        .build(),

                Problem.builder()
                        .problemId("abc463_d")
                        .title("Graph Path")
                        .code(ProblemCode.D)
                        .difficulty(800)
                        .url("https://atcoder.jp/contests/abc463/tasks/abc463_d")
                        .contest(abc)
                        .build(),

                Problem.builder()
                        .problemId("abc463_e")
                        .title("Tree Query")
                        .code(ProblemCode.E)
                        .difficulty(1200)
                        .url("https://atcoder.jp/contests/abc463/tasks/abc463_e")
                        .contest(abc)
                        .build(),

                Problem.builder()
                        .problemId("abc463_f")
                        .title("Dynamic Programming")
                        .code(ProblemCode.F)
                        .difficulty(1800)
                        .url("https://atcoder.jp/contests/abc463/tasks/abc463_f")
                        .contest(abc)
                        .build(),

                Problem.builder()
                        .problemId("abc463_g")
                        .title("Advanced Graph")
                        .code(ProblemCode.G)
                        .difficulty(2400)
                        .url("https://atcoder.jp/contests/abc463/tasks/abc463_g")
                        .contest(abc)
                        .build(),

                Problem.builder()
                        .problemId("arc211_a")
                        .title("Simple Math")
                        .code(ProblemCode.A)
                        .difficulty(300)
                        .url("https://atcoder.jp/contests/arc211/tasks/arc211_a")
                        .contest(arc)
                        .build(),

                Problem.builder()
                        .problemId("arc211_b")
                        .title("String Operations")
                        .code(ProblemCode.B)
                        .difficulty(700)
                        .url("https://atcoder.jp/contests/arc211/tasks/arc211_b")
                        .contest(arc)
                        .build(),

                Problem.builder()
                        .problemId("arc211_c")
                        .title("Interval Merge")
                        .code(ProblemCode.C)
                        .difficulty(1200)
                        .url("https://atcoder.jp/contests/arc211/tasks/arc211_c")
                        .contest(arc)
                        .build(),

                Problem.builder()
                        .problemId("arc211_d")
                        .title("Shortest Path")
                        .code(ProblemCode.D)
                        .difficulty(1800)
                        .url("https://atcoder.jp/contests/arc211/tasks/arc211_d")
                        .contest(arc)
                        .build(),

                Problem.builder()
                        .problemId("arc211_e")
                        .title("Segment Tree")
                        .code(ProblemCode.E)
                        .difficulty(2400)
                        .url("https://atcoder.jp/contests/arc211/tasks/arc211_e")
                        .contest(arc)
                        .build(),

                Problem.builder()
                        .problemId("arc211_f")
                        .title("Flow Network")
                        .code(ProblemCode.F)
                        .difficulty(3000)
                        .url("https://atcoder.jp/contests/arc211/tasks/arc211_f")
                        .contest(arc)
                        .build()
        );

        contestRepository.saveAndFlush(abc);
        contestRepository.saveAndFlush(arc);
        problemRepository.saveAll(problems);
    }
}