package com.lunimple.domain.user.entity;

import com.lunimple.domain.problems.entity.Problem;
import com.lunimple.domain.user.enums.ProblemResult;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(
        name = "user_problems",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_problem",
                        columnNames = {"user_id", "problem_id"}
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 해결 여부
     */
    @Column(nullable = false)
    private Boolean solved;

    /**
     * 총 제출 횟수
     */
    @Column(nullable = false)
    private Integer attemptCount;

    /**
     * 마지막 제출 결과
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProblemResult lastResult;

    /**
     * 마지막 제출 시간
     */
    private LocalDateTime lastSubmittedAt;

    /**
     * 최초 해결 시간
     */
    private LocalDateTime solvedAt;

    /**
     * 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 문제
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Builder
    private UserProblem(
            Long id,
            Boolean solved,
            Integer attemptCount,
            ProblemResult lastResult,
            LocalDateTime lastSubmittedAt,
            LocalDateTime solvedAt,
            User user,
            Problem problem
    ) {
        this.id = id;
        this.solved = solved;
        this.attemptCount = attemptCount;
        this.lastResult = lastResult;
        this.lastSubmittedAt = lastSubmittedAt;
        this.solvedAt = solvedAt;
        this.user = user;
        this.problem = problem;
    }
}