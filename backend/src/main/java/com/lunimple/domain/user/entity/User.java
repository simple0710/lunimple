package com.lunimple.domain.user.entity;

import com.lunimple.domain.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사용자 handle (계정 아이디)
     */
    @Column(nullable = false)
    private String handle;

    /**
     * 사용자 레이팅
     */
    @Column(nullable = false)
    private Integer rating;

    /**
     * 최고 레이팅
     */
    @Column(nullable = false)
    private Integer highest;

    /**
     * 1등 횟수
     */
    @Column(nullable = false)
    private Integer win;

    /**
     * 대회 참여 횟수
     */
    @Column(nullable = false)
    private Integer match;

    /**
     * 지역
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @Builder
    private User(
            String handle,
            Integer rating,
            Integer highest,
            Integer win,
            Integer match,
            Country country
    ) {
        this.handle = handle;
        this.rating = rating;
        this.highest = highest;
        this.win = win;
        this.match = match;
        this.country = country;
    }
}
