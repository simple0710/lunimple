package com.lunimple.global.init;

import com.lunimple.domain.notice.entity.Notice;
import com.lunimple.domain.notice.enums.NoticeImportance;
import com.lunimple.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class NoticeDataInitializer implements CommandLineRunner {

    private final NoticeRepository noticeRepository;

    @Override
    public void run(String... args) {

        if (noticeRepository.count() > 0) {
            return;
        }

        Notice important = Notice
                .builder()
                .title("AGC Proctoring Rules")
                .content("content")
                .writer("admin")
                .importance(NoticeImportance.IMPORTANT)
                .build();
        Notice normal = Notice
                .builder()
                .title("normal notice")
                .content("content")
                .writer("admin")
                .importance(NoticeImportance.NORMAL)
                .build();
        Notice urgent = Notice
                .builder()
                .title("urgent")
                .content("content")
                .writer("admin")
                .importance(NoticeImportance.URGENT)
                .build();

        noticeRepository.save(important);
        noticeRepository.save(normal);
        noticeRepository.save(urgent);
    }
}
