package com.lunimple.domain.notice.dto.response;

import com.lunimple.domain.notice.entity.Notice;
import com.lunimple.domain.notice.enums.NoticeImportance;

import java.time.LocalDateTime;

public record NoticeResponse(
        Long id,
        String title,
        String url,
        NoticeImportance importance,
        LocalDateTime createdAt
) {
    public static NoticeResponse from(Notice notice) {
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getUrl(),
                notice.getImportance(),
                notice.getCreatedAt()
        );
    }
}
