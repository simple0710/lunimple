package com.lunimple.domain.problems.entity;

import com.lunimple.domain.problems.enums.ProgrammingLanguageType;
import com.lunimple.domain.user.entity.User;
import com.lunimple.domain.user.enums.ProblemResult;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "submissions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 제출 결과
     * ex) AC, WA, TLE, RE, CE
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProblemResult result;

    /**
     * 제출 언어
     * ex) JAVA, CPP, PYTHON
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProgrammingLanguageType programmingLanguage;

    /**
     * 제출 시간
     */
    @Column(nullable = false)
    private LocalDateTime submittedAt;

    /**
     * 제출 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 제출 문제
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Builder
    private Submission(
            Long id,
            ProblemResult result,
            ProgrammingLanguageType programmingLanguage,
            LocalDateTime submittedAt,
            User user,
            Problem problem
    ) {
        this.id = id;
        this.result = result;
        this.programmingLanguage = programmingLanguage;
        this.submittedAt = submittedAt;
        this.user = user;
        this.problem = problem;
    }
}
