package com.lunimple.domain.notice.repository;

import com.lunimple.domain.notice.entity.Notice;
import com.lunimple.domain.notice.enums.NoticeImportance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Page<Notice> findAllByOrderByCreatedAtDesc(
            Pageable pageable
    );

    Page<Notice> findByImportanceOrderByCreatedAtDesc(
            NoticeImportance importance,
            Pageable pageable
    );

    Optional<Notice> findById(Long id);
}
