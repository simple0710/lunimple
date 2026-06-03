package com.lunimple.domain.problems.repository;

import com.lunimple.domain.problems.entity.Problem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    Problem findByProblemId(String problemId);

    @Query("""
        select p
        from Problem p
        where p.difficulty between :minDifficulty and :maxDifficulty
          and p.id not in :excludedProblemIds
        order by p.difficulty asc
    """)
    List<Problem> findRecommendations(
            Integer minDifficulty,
            Integer maxDifficulty,
            List<Long> excludedProblemIds,
            Pageable pageable
    );
}