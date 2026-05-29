package com.lunimple.domain.notice.service;

import com.lunimple.domain.notice.dto.response.NoticeDetailResponse;
import com.lunimple.domain.notice.dto.response.NoticeResponse;
import com.lunimple.domain.notice.enums.NoticeImportance;
import com.lunimple.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public Page<NoticeResponse> getNotices(
            NoticeImportance importance,
            Pageable pageable
    ) {

        Page<NoticeResponse> notice;
        if (importance == null) {
            notice = noticeRepository
                    .findAllByOrderByCreatedAtDesc(pageable)
                    .map(NoticeResponse::from);
        } else {
            notice = noticeRepository
                    .findByImportanceOrderByCreatedAtDesc(
                            importance,
                            pageable
                    )
                    .map(NoticeResponse::from);
        }
        return notice;
    }

    public NoticeDetailResponse getNoticeDetail(Long id) {
        return noticeRepository.findById(id)
                .map(NoticeDetailResponse::from)
                .orElseThrow(IllegalArgumentException::new);
    }
}
