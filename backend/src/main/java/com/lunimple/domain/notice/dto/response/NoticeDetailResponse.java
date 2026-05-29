package com.lunimple.domain.notice.dto.response;

import com.lunimple.domain.notice.entity.Notice;

import java.time.LocalDateTime;

public record NoticeDetailResponse(
        Long id,
        String title,
        String content,
        String writer,
        LocalDateTime createdAt
) {
    public static NoticeDetailResponse from(Notice notice) {
        return new NoticeDetailResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getWriter(),
                notice.getCreatedAt()
        );
    }
}
