package com.lunimple.domain.notice.dto.response;

import com.lunimple.domain.notice.entity.Notice;

import java.time.LocalDateTime;

public record NoticeResponse(
        Long id,
        String title,
        String content,
        String writer,
        LocalDateTime createdAt
) {

    public static NoticeResponse from(Notice notice) {
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getWriter(),
                notice.getCreatedAt()
        );
    }
}
