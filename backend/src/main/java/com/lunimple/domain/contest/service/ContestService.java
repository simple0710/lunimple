package com.lunimple.domain.contest.service;

import com.lunimple.domain.contest.dto.response.ContestResponse;
import com.lunimple.domain.contest.repository.ContestRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContestService {

    private final ContestRepository contestRepository;

    public List<ContestResponse> getUpcomingContests() {

        return contestRepository
                .findByStartTimeAfterOrderByStartTimeAsc(
                        LocalDateTime.now()
                )
                .stream()
                .map(ContestResponse::from)
                .toList();
    }

    public List<ContestResponse> getPastContests() {

        return contestRepository
                .findByStartTimeBeforeOrderByStartTimeDesc(
                        LocalDateTime.now()
                )
                .stream()
                .map(ContestResponse::from)
                .toList();
    }
}
