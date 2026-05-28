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
}
