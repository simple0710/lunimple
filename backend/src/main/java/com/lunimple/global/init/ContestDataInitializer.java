package com.lunimple.global.init;


import com.lunimple.domain.contest.entity.Contest;
import com.lunimple.domain.contest.enums.ContestType;
import com.lunimple.domain.contest.repository.ContestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Transactional
public class ContestDataInitializer implements CommandLineRunner {

    private final ContestRepository contestRepository;

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

        contestRepository.saveAndFlush(abc);
        contestRepository.saveAndFlush(arc);
    }
}