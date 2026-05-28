package com.lunimple.domain.contest.service;

import com.lunimple.domain.contest.dto.response.ContestResponse;
import com.lunimple.domain.contest.enums.ContestType;
import com.lunimple.domain.contest.repository.ContestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContestService {

    private final ContestRepository contestRepository;

    public Page<ContestResponse> getUpcomingContests(
            ContestType contestType,
            Pageable pageable
    ) {

        Page<ContestResponse> contest;

        if (contestType == null) {
            contest = contestRepository
                    .findByStartTimeAfterOrderByStartTimeAsc(
                            LocalDateTime.now(),
                            pageable
                    )
                    .map(ContestResponse::from);
        } else {
            contest = contestRepository
                    .findByContestTypeAndStartTimeAfterOrderByStartTimeAsc(
                            contestType,
                            LocalDateTime.now(),
                            pageable
                    )
                    .map(ContestResponse::from);
        }

        return contest;
    }

    public Page<ContestResponse> getPastContests(
            ContestType contestType,
            Pageable pageable
    ) {

        Page<ContestResponse> contest;

        if (contestType == null) {
            contest = contestRepository
                    .findByStartTimeBeforeOrderByStartTimeDesc(
                            LocalDateTime.now(),
                            pageable
                    )
                    .map(ContestResponse::from);
        } else {
            contest = contestRepository
                    .findByContestTypeAndStartTimeBeforeOrderByStartTimeDesc(
                            contestType,
                            LocalDateTime.now(),
                            pageable
                    )
                    .map(ContestResponse::from);
        }

        return contest;
    }
}
