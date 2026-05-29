package com.lunimple.domain.notice.controller;

import com.lunimple.domain.notice.dto.response.NoticeDetailResponse;
import com.lunimple.domain.notice.dto.response.NoticeResponse;
import com.lunimple.domain.notice.enums.NoticeImportance;
import com.lunimple.domain.notice.service.NoticeService;
import com.lunimple.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public ApiResponse<Page<NoticeResponse>> getTotalNotices(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) NoticeImportance importance
    ) {

        Pageable pageable = PageRequest.of(page - 1, size);

        return ApiResponse.success(
                noticeService.getNotices(
                        importance,
                        pageable
                )
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<NoticeDetailResponse> getNoticeDetail(
            @PathVariable Long id
    ) {
        return ApiResponse.success(
                noticeService.getNoticeDetail(
                        id
                )
        );
    }
}
