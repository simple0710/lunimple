package com.lunimple.domain.contest.repository;

import com.lunimple.domain.contest.entity.Contest;
import com.lunimple.domain.contest.enums.ContestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {

    Page<Contest> findByStartTimeAfterOrderByStartTimeAsc(
            LocalDateTime now,
            Pageable pageable
    );

    Page<Contest> findByContestTypeAndStartTimeAfterOrderByStartTimeAsc(
            ContestType contestType,
            LocalDateTime now,
            Pageable pageable
    );

    /**
     * 현재 시간 이전에 시작한 대회를 시작 시간 기준 최신순으로 조회한다.
     */
    Page<Contest> findByStartTimeBeforeOrderByStartTimeDesc(
            LocalDateTime now,
            Pageable pageable
    );

    Page<Contest> findByContestTypeAndStartTimeBeforeOrderByStartTimeDesc(
            ContestType contestType,
            LocalDateTime now,
            Pageable pageable
    );
}
