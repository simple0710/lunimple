package com.lunimple.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "country")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 지역 약어
     * ex) KR, JP, US
     */
    @Column(nullable = false, unique = true)
    private String code;

    /**
     * 지역 이름
     */
    @Column(nullable = false)
    private String name;

    @Builder
    private Country(
            String code,
            String name
    ) {
        this.code = code;
        this.name = name;
    }
}