package com.lunimple.domain.contest.repository;

import com.lunimple.domain.contest.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {

    List<Contest> findByStartTimeAfterOrderByStartTimeAsc(
            LocalDateTime now
    );

    /**
     * 현재 시간 이전에 시작한 대회를 시작 시간 기준 최신순으로 조회한다.
     */
    List<Contest> findByStartTimeBeforeOrderByStartTimeDesc(
            LocalDateTime now
    );
}
