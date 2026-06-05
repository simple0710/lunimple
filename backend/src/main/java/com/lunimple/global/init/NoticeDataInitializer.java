package com.lunimple.global.init;

import com.lunimple.domain.notice.entity.Notice;
import com.lunimple.domain.notice.enums.NoticeImportance;
import com.lunimple.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class NoticeDataInitializer implements CommandLineRunner {

    private final NoticeRepository noticeRepository;

    @Override
    public void run(String... args) {
        noticeRepository.deleteAll();

        List<Notice> notices = List.of(
                Notice.builder()
                        .title("AGC Proctoring Rules")
                        .content("AGC Proctoring Rules announcement")
                        .url("https://atcoder.jp/posts/agc_proctoring_en")
                        .writer("AtCoder")
                        .importance(NoticeImportance.IMPORTANT)
                        .build(),
                Notice.builder()
                        .title("ARC221 (English)")
                        .content("ARC221 announcement in English")
                        .url("https://atcoder.jp/posts/arc221_en")
                        .writer("AtCoder")
                        .importance(NoticeImportance.RECENT)
                        .build(),
                Notice.builder()
                        .title("ARC221 (Japanese)")
                        .content("ARC221 announcement in Japanese")
                        .url("https://atcoder.jp/posts/arc221_ja")
                        .writer("AtCoder")
                        .importance(NoticeImportance.RECENT)
                        .build()
        );

        noticeRepository.saveAll(notices);
    }
}
