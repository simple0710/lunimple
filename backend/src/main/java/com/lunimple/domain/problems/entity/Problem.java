package com.lunimple.domain.problems.entity;

import com.lunimple.domain.contest.entity.Contest;
import com.lunimple.domain.problems.enums.ProblemCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "problems")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 외부 문제 ID
     * ex) abc100_a
     */
    @Column(nullable = false)
    private String problemId;

    /**
     * 문제 제목
     */
    @Column(nullable = false)
    private String title;

    /**
     * 문제 코드
     * ex) A, B, C, D, E, F, G, H, Ex
     */
    @Enumerated(EnumType.STRING)
    private ProblemCode code;

    /**
     * 문제 난이도
     */
    @Column(nullable = false)
    private Integer difficulty;

    /**
     * 문제 url
     */
    @Column(nullable = false)
    private String url;

    /**
     * 대회
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @Builder
    private Problem(
            String problemId,
            String title,
            ProblemCode code,
            Integer difficulty,
            String url,
            Contest contest
    ) {
        this.problemId = problemId;
        this.title = title;
        this.code = code;
        this.difficulty = difficulty;
        this.url = url;
        this.contest = contest;
    }
}
