package com.lunimple.domain.user.repository;

import com.lunimple.domain.problems.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findAllByUserIdAndSubmittedAtAfter(Long id, LocalDateTime localDateTime);
}
