package com.lunimple.domain.notice.entity;

import com.lunimple.domain.common.entity.BaseTimeEntity;
import com.lunimple.domain.notice.enums.NoticeImportance;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "notice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 공지 제목
     */
    @Column(nullable = false)
    private String title;

    /**
     * 공지 내용
     */
    @Column(nullable = false)
    private String content;

    /**
     * 공지 작성자
     */
    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private NoticeImportance importance;

    @Builder
    private Notice(
            String title,
            String content,
            String writer,
            NoticeImportance importance
    ) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.importance = importance;
    }
}
