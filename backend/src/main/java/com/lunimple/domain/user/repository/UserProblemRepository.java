package com.lunimple.domain.user.repository;

import com.lunimple.domain.user.entity.UserProblem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProblemRepository extends JpaRepository<UserProblem, Long> {
    List<UserProblem> findAllByUserId(Long id);
}
