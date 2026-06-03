package com.lunimple.domain.user.repository;

import com.lunimple.domain.user.entity.UserProblem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserProblemRepository
        extends JpaRepository<UserProblem, Long> {

    List<UserProblem> findAllByUserId(Long userId);

    Long countByUserIdAndSolvedTrue(Long userId);

    @Query("""
        select cast(avg(p.difficulty) as integer)
        from UserProblem up
        join up.problem p
        where up.user.id = :userId
          and up.solved = true
    """)
    Integer findAverageSolvedDifficulty(
            @Param("userId") Long userId
    );

    @Query("""
        select up.problem.id
        from UserProblem up
        where up.user.id = :userId
          and up.solved = true
        order by up.solvedAt desc
    """)
    List<Long> findRecentSolvedProblemIds(
            @Param("userId") Long userId,
            Pageable pageable
    );
}