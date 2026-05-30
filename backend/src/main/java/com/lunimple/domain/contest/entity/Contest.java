package com.lunimple.domain.contest.entity;

import com.lunimple.domain.common.entity.BaseTimeEntity;
import com.lunimple.domain.contest.enums.ContestType;
import com.lunimple.domain.problems.entity.Problem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "contest")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contest extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 대회 이름
     * ex) AtCoder Beginner contest 434
     */
    @Column(nullable = false)
    private String name;

    /**
     * 대회 시작 시간
     */
    @Column(nullable = false)
    private LocalDateTime startTime;

    /**
     * 대회 진행 시간(분 단위)
     * ex) 100, 120
     */
    @Column(nullable = false)
    private Integer durationMinutes;

    /**
     * 대회 타입
     * ex) ABC, ARC, AGC
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContestType contestType;

    /**
     * 최소 레이팅
     */
    @Column(nullable = false)
    private Integer ratedMin;

    /**
     * 최대 레이팅
     */
    @Column(nullable = false)
    private Integer ratedMax;

    /**
     * 대회 URL
     */
    @Column(nullable = false, unique = true)
    private String contestUrl;

    @OneToMany(
            mappedBy = "contest",
            fetch = FetchType.LAZY
    )
    private List<Problem> problems;

    @Builder
    private Contest(
            String name,
            LocalDateTime startTime,
            Integer durationMinutes,
            ContestType contestType,
            Integer ratedMin,
            Integer ratedMax,
            String contestUrl
    ) {
        this.name = name;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.contestType = contestType;
        this.ratedMin = ratedMin;
        this.ratedMax = ratedMax;
        this.contestUrl = contestUrl;
    }
}
